/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.internal.ChannelUtils;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.internal.StringUtil;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

import static io.netty.channel.internal.ChannelUtils.WRITE_STATUS_SNDBUF_FULL;

/**
 * {@link AbstractNioChannel} base class for {@link Channel}s that operate on bytes.
 */
public abstract class AbstractNioByteChannel extends AbstractNioChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private static final String EXPECTED_TYPES =
            " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " +
            StringUtil.simpleClassName(FileRegion.class) + ')';

    private final Runnable flushTask = new Runnable() {
        @Override
        public void run() {
            // Calling flush0 directly to ensure we not try to flush messages that were added via write(...) in the
            // meantime.
            ((AbstractNioUnsafe) unsafe()).flush0();
        }
    };
    private boolean inputClosedSeenErrorOnRead;

    /**
     * Create a new instance
     *
     * @param parent            the parent {@link Channel} by which this instance was created. May be {@code null}
     * @param ch                the underlying {@link SelectableChannel} on which it operates
     */
    protected AbstractNioByteChannel(Channel parent, SelectableChannel ch) {
        super(parent, ch, SelectionKey.OP_READ);
    }

    /**
     * Shutdown the input side of the channel.
     */
    protected abstract ChannelFuture shutdownInput();

    protected boolean isInputShutdown0() {
        return false;
    }

    @Override
    protected AbstractNioUnsafe newUnsafe() {
        return new NioByteUnsafe();
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    final boolean shouldBreakReadReady(ChannelConfig config) {
        return isInputShutdown0() && (inputClosedSeenErrorOnRead || !isAllowHalfClosure(config));
    }

    /**
     * TCP的连接是一个双向通道，所以关闭连接就是一个关闭双向通道的过程，正常情况下，channel.close()，对端收到一个EOF（-1），
     * 然后server返回一个FIN的ACK，server调用channel.close()发送一个FIN，这样就完成双向通道的关闭。
     * 有的时候，只想关闭其中一个通道，这个时候就要调用channe.shutdownOutout()，这样就会给对端发送一个FIN包，对端读到EOF（-1），
     * 以前不支持半关时，对端会直接调用channel.close()关闭掉连接，现在支持了半关，就会调用channel.shutdonwInput()，server仍然可以发送数据给client。
     * client端channel.shutdownOutput()之后，client就不能再发送数据了，如果再发送，就会抛出一个Exception说这个channel已经关闭掉了。
     * server端调用channel.shutdownInput()之后就不能再读取数据了，读取到的依旧是EOF(-1)。
     */
    private static boolean isAllowHalfClosure(ChannelConfig config) {
        return config instanceof SocketChannelConfig &&
                ((SocketChannelConfig) config).isAllowHalfClosure();
    }

    protected class NioByteUnsafe extends AbstractNioUnsafe {

        private void closeOnRead(ChannelPipeline pipeline) {
            if (!isInputShutdown0()) {
                if (isAllowHalfClosure(config())) {
                    shutdownInput();
                    pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                } else {
                    close(voidPromise());
                }
            } else {
                inputClosedSeenErrorOnRead = true;
                pipeline.fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
            }
        }

        private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close,
                RecvByteBufAllocator.Handle allocHandle) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    readPending = false;
                    pipeline.fireChannelRead(byteBuf);
                } else {
                    byteBuf.release();
                }
            }
            allocHandle.readComplete();
            pipeline.fireChannelReadComplete();
            pipeline.fireExceptionCaught(cause);

            // If oom will close the read event, release connection.
            // See https://github.com/netty/netty/issues/10434
            if (close || cause instanceof OutOfMemoryError || cause instanceof IOException) {
                closeOnRead(pipeline);
            }
        }

        @Override
        public final void read() {
            final ChannelConfig config = config();
            if (shouldBreakReadReady(config)) {
                clearReadPending();
                return;
            }
            final ChannelPipeline pipeline = pipeline();
            // ByteBuf分配器
            final ByteBufAllocator allocator = config.getAllocator();

            // 接受缓冲区的内存分配策略，分为分配固定大小(不够时扩容)、动态变化(根据历史分配的大小，动态条件合适的内存大小)，
            // 这里主要的设计哲学是合理利用内存，并减少扩容，提高内存的分配效率与使用效率。
            final RecvByteBufAllocator.Handle allocHandle = recvBufAllocHandle();

            // allocHandle允许被复用，这里进行初始化
            allocHandle.reset(config);

            ByteBuf byteBuf = null;
            boolean close = false;
            try {
                // 当对端发送一个超大的数据包时，TCP会拆包。
                // OP_READ事件只会触发一次，Netty需要循环读，默认最多读16次, 因此ChannelRead()可能会触发多次，拿到的是半包数据。
                // 如果16次没把数据读完，没有关系，下次select()还会继续处理。
                // 对于Selector的可读事件，如果你没有读完数据，它会一直返回。
                do {
                    // 分配一个ByteBuf，容量大小由allocHandle决定
                    // 第一次推荐的大小是2048，对应AdaptiveRecvByteBufAllocator构造函数的initial属性
                    byteBuf = allocHandle.allocate(allocator);

                    // doReadBytes(byteBuf): 将Channel中的数据写入到byteBuf中，返回值为实际写入到ByteBuf中的字节数
                    // allocHandle.lastBytesRead(): 记录本次实际读取到的字节数，记录的同时会判断，如果本次读取将缓冲区填满，会触发一次扩容
                    allocHandle.lastBytesRead(doReadBytes(byteBuf));

                    if (allocHandle.lastBytesRead() <= 0) {
                        // nothing was read. release the buffer.
                        // 若没有数据可读，则释放内存
                        byteBuf.release();
                        byteBuf = null;
                        close = allocHandle.lastBytesRead() < 0;
                        if (close) {
                            // There is nothing left to read as we received an EOF.
                            // 当读到-1时,表示Channel 通道已经关闭,没有必要再继续
                            readPending = false;
                        }
                        break;
                    }

                    // 更新读取消息计数器, 递增已经读取的消息数量, 因为一次最多读取16次
                    allocHandle.incMessagesRead(1);
                    readPending = false;
                    pipeline.fireChannelRead(byteBuf);
                    byteBuf = null;

                // 当同时满足config.isAutoRead() && 本地读取大小 = 缓冲区大小 && 读取总次数 < 16，continueReading就会返回true
                // 每次最多读取16次，哲学是避免一个通道需要读取太多的数据，从而影响其他通道的数据读，
                // 因为一个NioEventLoop可以绑定多个channel，在一个NioEventLoop中多个通道的读事件是串行执行的
                } while (allocHandle.continueReading());

                // while完成，根据此次while循环读取到的总大小，决定是否扩容还是缩容，其结果作为下一次读取的分配大小
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();

                if (close) {
                    closeOnRead(pipeline);
                }
            } catch (Throwable t) {
                handleReadException(pipeline, byteBuf, t, close, allocHandle);
            } finally {
                // Check if there is a readPending which was not processed yet.
                // This could be for two reasons:
                // * The user called Channel.read() or ChannelHandlerContext.read() in channelRead(...) method
                // * The user called Channel.read() or ChannelHandlerContext.read() in channelReadComplete(...) method
                //
                // See https://github.com/netty/netty/issues/2254
                // 如果没有开启自动注册读事件，在每一次读时间处理过后会取消读事件
                if (!readPending && !config.isAutoRead()) {
                    removeReadOp();
                }
            }
        }
    }

    /**
     * Write objects to the OS.
     * @param in the collection which contains objects to write.
     * @return The value that should be decremented from the write quantum which starts at
     * {@link ChannelConfig#getWriteSpinCount()}. The typical use cases are as follows:
     * <ul>
     *     <li>0 - if no write was attempted. This is appropriate if an empty {@link ByteBuf} (or other empty content)
     *     is encountered</li>
     *     <li>1 - if a single call to write data was made to the OS</li>
     *     <li>{@link ChannelUtils#WRITE_STATUS_SNDBUF_FULL} - if an attempt to write data was made to the OS, but no
     *     data was accepted</li>
     * </ul>
     * @throws Exception if an I/O exception occurs during write.
     */
    protected final int doWrite0(ChannelOutboundBuffer in) throws Exception {
        Object msg = in.current();
        if (msg == null) {
            // Directly return here so incompleteWrite(...) is not called.
            return 0;
        }
        return doWriteInternal(in, in.current());
    }

    private int doWriteInternal(ChannelOutboundBuffer in, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            if (!buf.isReadable()) {
                in.remove();
                return 0;
            }

            // 写到jdk的socket
            final int localFlushedAmount = doWriteBytes(buf);
            if (localFlushedAmount > 0) {
                // 更新刷新进度
                in.progress(localFlushedAmount);
                if (!buf.isReadable()) {
                    // 移除当前已刷写的节点
                    in.remove();
                }
                return 1;
            }
        } else if (msg instanceof FileRegion) {
            FileRegion region = (FileRegion) msg;
            if (region.transferred() >= region.count()) {
                in.remove();
                return 0;
            }

            long localFlushedAmount = doWriteFileRegion(region);
            if (localFlushedAmount > 0) {
                in.progress(localFlushedAmount);
                if (region.transferred() >= region.count()) {
                    in.remove();
                }
                return 1;
            }
        } else {
            // Should not reach here.
            throw new Error();
        }
        return WRITE_STATUS_SNDBUF_FULL;
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        // 默认16
        int writeSpinCount = config().getWriteSpinCount();
        do {
            Object msg = in.current();
            if (msg == null) {
                // Wrote all messages.
                // 如果已经写完了，并且发现NioSocketChannel还注册有SelectionKey.OP_WRITE事件，则将SelectionKey.OP_WRITE从感兴趣的事件中移除，
                // 即，Selector不再监听该NioSocketChannel的可写事件了
                clearOpWrite();
                // Directly return here so incompleteWrite(...) is not called.
                return;
            }
            writeSpinCount -= doWriteInternal(in, msg);
        } while (writeSpinCount > 0);

        incompleteWrite(writeSpinCount < 0);
    }

    @Override
    protected final Object filterOutboundMessage(Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            if (buf.isDirect()) {
                return msg;
            }
            // 如果不是堆外内存，则转换成堆外内存，但不一定成功
            return newDirectBuffer(buf);
        }

        if (msg instanceof FileRegion) {
            return msg;
        }

        throw new UnsupportedOperationException(
                "unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
    }

    /**
     * 参数setOpWrite = true的情况：
     * 1.循环写了16次仍没有写完
     * 2.内核写缓冲区满了
     */
    protected final void incompleteWrite(boolean setOpWrite) {
        // Did not write completely.
        if (setOpWrite) {
            setOpWrite();
        } else {
            // It is possible that we have set the write OP, woken up by NIO because the socket is writable, and then
            // use our write quantum. In this case we no longer want to set the write OP because the socket is still
            // writable (as far as we know). We will find out next time we attempt to write if the socket is writable
            // and set the write OP if necessary.
            clearOpWrite();

            // Schedule flush again later so other tasks can be picked up in the meantime
            eventLoop().execute(flushTask);
        }
    }

    /**
     * Write a {@link FileRegion}
     *
     * @param region        the {@link FileRegion} from which the bytes should be written
     * @return amount       the amount of written bytes
     */
    protected abstract long doWriteFileRegion(FileRegion region) throws Exception;

    /**
     * Read bytes into the given {@link ByteBuf} and return the amount.
     */
    protected abstract int doReadBytes(ByteBuf buf) throws Exception;

    /**
     * Write bytes form the given {@link ByteBuf} to the underlying {@link java.nio.channels.Channel}.
     * @param buf           the {@link ByteBuf} from which the bytes should be written
     * @return amount       the amount of written bytes
     */
    protected abstract int doWriteBytes(ByteBuf buf) throws Exception;

    protected final void setOpWrite() {
        final SelectionKey key = selectionKey();
        // Check first if the key is still valid as it may be canceled as part of the deregistration
        // from the EventLoop
        // See https://github.com/netty/netty/issues/2104
        if (!key.isValid()) {
            return;
        }
        final int interestOps = key.interestOps();
        if ((interestOps & SelectionKey.OP_WRITE) == 0) {
            key.interestOps(interestOps | SelectionKey.OP_WRITE);
        }
    }

    protected final void clearOpWrite() {
        final SelectionKey key = selectionKey();
        // Check first if the key is still valid as it may be canceled as part of the deregistration
        // from the EventLoop
        // See https://github.com/netty/netty/issues/2104
        if (!key.isValid()) {
            return;
        }
        final int interestOps = key.interestOps();
        if ((interestOps & SelectionKey.OP_WRITE) != 0) {
            key.interestOps(interestOps & ~SelectionKey.OP_WRITE);
        }
    }
}
