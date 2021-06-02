/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.socket.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.util.internal.SocketUtils;
import io.netty.channel.nio.AbstractNioByteChannel;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;

/**
 * {@link io.netty.channel.socket.SocketChannel} which uses NIO selector based implementation.
 */
public class NioSocketChannel extends AbstractNioByteChannel implements io.netty.channel.socket.SocketChannel
{
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSocketChannel.class);
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();

    private static SocketChannel newSocket(SelectorProvider provider)
    {
        try
        {
            /**
             *  Use the {@link SelectorProvider} to open {@link SocketChannel} and so remove condition in
             *  {@link SelectorProvider#provider()} which is called by each SocketChannel.open() otherwise.
             *
             *  See <a href="https://github.com/netty/netty/issues/2308">#2308</a>.
             */
            return provider.openSocketChannel();
        } catch (IOException e)
        {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    private final SocketChannelConfig config;

    /**
     * Create a new instance
     */
    public NioSocketChannel()
    {
        this(DEFAULT_SELECTOR_PROVIDER);
    }

    /**
     * Create a new instance using the given {@link SelectorProvider}.
     */
    public NioSocketChannel(SelectorProvider provider)
    {
        this(newSocket(provider));
    }

    /**
     * Create a new instance using the given {@link SocketChannel}.
     */
    public NioSocketChannel(SocketChannel socket)
    {
        this(null, socket);
    }

    /**
     * Create a new instance
     *
     * @param parent the {@link Channel} which created this instance or {@code null} if it was created by the user
     * @param socket the {@link SocketChannel} which will be used
     */
    public NioSocketChannel(Channel parent, SocketChannel socket)
    {
        // 向上传递,逐层调用父类的构造函数,初始化id pipeline unsafe
        super(parent, socket);
        //主要是设置 禁用了 NoDelay算法
        config = new NioSocketChannelConfig(this, socket.socket());
    }

    @Override
    public ServerSocketChannel parent()
    {
        return (ServerSocketChannel) super.parent();
    }

    @Override
    public SocketChannelConfig config()
    {
        return config;
    }

    @Override
    protected SocketChannel javaChannel()
    {
        return (SocketChannel) super.javaChannel();
    }

    @Override
    public boolean isActive()
    {
        SocketChannel ch = javaChannel();
        return ch.isOpen() && ch.isConnected();
    }

    @Override
    public boolean isOutputShutdown()
    {
        return javaChannel().socket().isOutputShutdown() || !isActive();
    }

    @Override
    public boolean isInputShutdown()
    {
        return javaChannel().socket().isInputShutdown() || !isActive();
    }

    @Override
    public boolean isShutdown()
    {
        Socket socket = javaChannel().socket();
        return socket.isInputShutdown() && socket.isOutputShutdown() || !isActive();
    }

    @Override
    public InetSocketAddress localAddress()
    {
        return (InetSocketAddress) super.localAddress();
    }

    @Override
    public InetSocketAddress remoteAddress()
    {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override
    public ChannelFuture shutdownOutput()
    {
        return shutdownOutput(newPromise());
    }

    @Override
    public ChannelFuture shutdownOutput(final ChannelPromise promise)
    {
        Executor closeExecutor = ((NioSocketChannelUnsafe) unsafe()).prepareToClose();
        if (closeExecutor != null)
        {
            closeExecutor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    shutdownOutput0(promise);
                }
            });
        } else
        {
            EventLoop loop = eventLoop();
            if (loop.inEventLoop())
            {
                shutdownOutput0(promise);
            } else
            {
                loop.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        shutdownOutput0(promise);
                    }
                });
            }
        }
        return promise;
    }

    @Override
    public ChannelFuture shutdownInput()
    {
        return shutdownInput(newPromise());
    }

    @Override
    protected boolean isInputShutdown0()
    {
        return isInputShutdown();
    }

    @Override
    public ChannelFuture shutdownInput(final ChannelPromise promise)
    {
        Executor closeExecutor = ((NioSocketChannelUnsafe) unsafe()).prepareToClose();
        if (closeExecutor != null)
        {
            closeExecutor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    shutdownInput0(promise);
                }
            });
        } else
        {
            EventLoop loop = eventLoop();
            if (loop.inEventLoop())
            {
                shutdownInput0(promise);
            } else
            {
                loop.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        shutdownInput0(promise);
                    }
                });
            }
        }
        return promise;
    }

    @Override
    public ChannelFuture shutdown()
    {
        return shutdown(newPromise());
    }

    @Override
    public ChannelFuture shutdown(final ChannelPromise promise)
    {
        Executor closeExecutor = ((NioSocketChannelUnsafe) unsafe()).prepareToClose();
        if (closeExecutor != null)
        {
            closeExecutor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    shutdown0(promise);
                }
            });
        } else
        {
            EventLoop loop = eventLoop();
            if (loop.inEventLoop())
            {
                shutdown0(promise);
            } else
            {
                loop.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        shutdown0(promise);
                    }
                });
            }
        }
        return promise;
    }

    private void shutdownOutput0(final ChannelPromise promise)
    {
        try
        {
            shutdownOutput0();
            promise.setSuccess();
        } catch (Throwable t)
        {
            promise.setFailure(t);
        }
    }

    private void shutdownOutput0() throws Exception
    {
        if (PlatformDependent.javaVersion() >= 7)
        {
            javaChannel().shutdownOutput();
        } else
        {
            javaChannel().socket().shutdownOutput();
        }
        ((AbstractUnsafe) unsafe()).shutdownOutput();
    }

    private void shutdownInput0(final ChannelPromise promise)
    {
        try
        {
            shutdownInput0();
            promise.setSuccess();
        } catch (Throwable t)
        {
            promise.setFailure(t);
        }
    }

    private void shutdownInput0() throws Exception
    {
        if (PlatformDependent.javaVersion() >= 7)
        {
            javaChannel().shutdownInput();
        } else
        {
            javaChannel().socket().shutdownInput();
        }
    }

    private void shutdown0(final ChannelPromise promise)
    {
        Throwable cause = null;
        try
        {
            shutdownOutput0();
        } catch (Throwable t)
        {
            cause = t;
        }
        try
        {
            shutdownInput0();
        } catch (Throwable t)
        {
            if (cause == null)
            {
                promise.setFailure(t);
            } else
            {
                logger.debug("Exception suppressed because a previous exception occurred.", t);
                promise.setFailure(cause);
            }
            return;
        }
        if (cause == null)
        {
            promise.setSuccess();
        } else
        {
            promise.setFailure(cause);
        }
    }

    @Override
    protected SocketAddress localAddress0()
    {
        return javaChannel().socket().getLocalSocketAddress();
    }

    @Override
    protected SocketAddress remoteAddress0()
    {
        return javaChannel().socket().getRemoteSocketAddress();
    }

    @Override
    protected void doBind(SocketAddress localAddress) throws Exception
    {
        doBind0(localAddress);
    }

    private void doBind0(SocketAddress localAddress) throws Exception
    {
        if (PlatformDependent.javaVersion() >= 7)
        {
            SocketUtils.bind(javaChannel(), localAddress);
        } else
        {
            SocketUtils.bind(javaChannel().socket(), localAddress);
        }
    }

    @Override
    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception
    {
        if (localAddress != null)
        {
            doBind0(localAddress);
        }

        boolean success = false;
        try
        {
            /**
             * 为什么这里会返回false？？？
             * 原因：在非阻塞模式下，connect操作会立即返回false，后面会发出CONNECT事件来表示连接，但是这里其实没有区分成功还是失败
             *
             * 后面会发出CONNECT事件来表示连接，但是这里其实没有区分成功还是失败，
             * 所以这个事件发生的时候不能简单呢的认为连接成功，要使用finishConnect判断下，如果连接失败，会抛出异常
             */
            boolean connected = SocketUtils.connect(javaChannel(), remoteAddress);
            if (!connected)
            {
                //返回false后注册事件
                selectionKey().interestOps(SelectionKey.OP_CONNECT);
            }
            success = true;
            return connected;
        } finally
        {
            if (!success)
            {
                doClose();
            }
        }
    }

    @Override
    protected void doFinishConnect() throws Exception
    {
        if (!javaChannel().finishConnect())
        {
            throw new Error();
        }
    }

    @Override
    protected void doDisconnect() throws Exception
    {
        doClose();
    }

    @Override
    protected void doClose() throws Exception
    {
        super.doClose();
        javaChannel().close();
    }

    @Override
    protected int doReadBytes(ByteBuf byteBuf) throws Exception
    {
        final RecvByteBufAllocator.Handle allocHandle = unsafe().recvBufAllocHandle();
        allocHandle.attemptedBytesRead(byteBuf.writableBytes());
        return byteBuf.writeBytes(javaChannel(), allocHandle.attemptedBytesRead());
    }

    @Override
    protected int doWriteBytes(ByteBuf buf) throws Exception
    {
        final int expectedWrittenBytes = buf.readableBytes();
        // 将字节数据, 写入到 java 原生的 channel中
        return buf.readBytes(javaChannel(), expectedWrittenBytes);
    }

    @Override
    protected long doWriteFileRegion(FileRegion region) throws Exception
    {
        final long position = region.transferred();
        return region.transferTo(javaChannel(), position);
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer in) throws Exception
    {
        for (; ; )
        {
            int size = in.size();
            if (size == 0)
            {
                // All written so clear OP_WRITE
                // 如果已经传输完了，并且发现NioSocketChannel还注册有SelectionKey.OP_WRITE事件，则将SelectionKey.OP_WRITE从感兴趣的事件中移除，
                // 即，Selector不在监听该NioSocketChannel的可写事件了
                clearOpWrite();
                break;
            }
            // 本次循环已经写出的字节数
            long writtenBytes = 0;
            // 本次循环是否写出了所有待写出的数据
            boolean done = false;
            // 是否需要设置SelectionKey.OP_WRITE事件的标志
            boolean setOpWrite = false;

            // Ensure the pending writes are made of ByteBufs only.
            // 获取所有待写出的ByteBuffer，它会将ChannelOutboundBuffer中所有待写出的ByteBuf转换成JDK Bytebuffer
            ByteBuffer[] nioBuffers = in.nioBuffers();
            // 获取本次循环需要写出的ByteBuffer个数
            int nioBufferCnt = in.nioBufferCount();
            // 获取本次循环总共需要写出的数据的字节总数
            long expectedWrittenBytes = in.nioBufferSize();
            SocketChannel ch = javaChannel();

            // Always us nioBuffers() to workaround data-corruption.
            // See https://github.com/netty/netty/issues/2761
            // 根据nioBufferCnt值的不同执行不同的传输流程
            switch (nioBufferCnt)
            {
                /**
                 *  [1] nioBufferCnt == 0 ：对非ByteBuffer对象的数据进行普通的写操作。
                 *  上面我们说了in.nioBuffers()会将ChannelOutboundBuffer中所有待发送的ByteBuf转换成Bytebuffer后返回一个ByteBuffer[]数组，
                 *  以便后面进行ByteBuffer的传输，而nioBufferCnt则表示待发送ByteBuffer的个数，即ByteBuffer[]数组的长度。
                 *  注意，这里nioBuffers()仅仅是对ByteBuf对象进行了操作，但是我们从前面的流程可以得知，除了ByteBuf外FileRegion对象也是
                 *  可以进行底层的网络传输的。因此当待传输的对象是FileRegion时“nioBufferCnt == 0”，那么这是就会
                 *  调用『AbstractNioByteChannel#doWrite(ChannelOutboundBuffer in)』方法来完成数据的传输。
                 *  实际上底层就是依靠JDK NIO 的 FileChannel来实现零拷贝的数据传输。
                 */
                case 0:
                    // We have something else beside ByteBuffers to write so fallback to normal writes.
                    super.doWrite(in);
                    return;

                /**
                 *  [2] nioBufferCnt == 1 ：说明只有一个ByteBuffer等待被传输，那么不使用gather的write操作来传输数据
                 *  （JDK NIO 支持一次写单个ByteBuffer 以及 一次写多个ByteBuffer的聚集写模式）
                 */
                case 1:
                    // Only one ByteBuf so use non-gathering write
                    ByteBuffer nioBuffer = nioBuffers[0];
                    for (int i = config().getWriteSpinCount() - 1; i >= 0; i--)
                    {
                        final int localWrittenBytes = ch.write(nioBuffer);
                        if (localWrittenBytes == 0)
                        {
                            setOpWrite = true;
                            break;
                        }
                        expectedWrittenBytes -= localWrittenBytes;
                        writtenBytes += localWrittenBytes;
                        if (expectedWrittenBytes == 0)
                        {
                            done = true;
                            break;
                        }
                    }
                    break;

                /**
                 *  [3] nioBufferCnt > 1 ：说明有多个ByteBuffer等待被传输，那么使用JDK NIO的聚集写操作，一次性传输多个ByteBuffer到NioSocketChannel中。
                 */
                default:
                    // config().getWriteSpinCount()为16，也就是一次写操作会最多执行16次的SocketChannel.write操作来将数据写到网络中
                    for (int i = config().getWriteSpinCount() - 1; i >= 0; i--)
                    {
                        // ch.write操作会返回本次写操作写出的字节数
                        final long localWrittenBytes = ch.write(nioBuffers, 0, nioBufferCnt);
                        if (localWrittenBytes == 0)
                        {
                            setOpWrite = true;
                            break;
                        }
                        expectedWrittenBytes -= localWrittenBytes;
                        writtenBytes += localWrittenBytes;

                        // 一次写操作会最多执行16次的SocketChannel.write操作来将数据写到网络中
                        if (expectedWrittenBytes == 0)
                        {
                            done = true;
                            break;
                        }
                    }
                    break;
            }

            // Release the fully written buffers, and update the indexes of the partially written buffer.
            // 释放所有已经写出去的缓存对象，并修改部分写缓冲的索引
            in.removeBytes(writtenBytes);

            // done为false，说明在这16次的socket写操作后依旧还有未写完的数据等待被继续写，
            // 那么就会将flush操作封装为一个task提交至NioEventLoop的任务队列中
            if (!done)
            {
                // Did not write all buffers completely.
                incompleteWrite(setOpWrite);
                break;
            }
        }
    }

    @Override
    protected AbstractNioUnsafe newUnsafe()
    {
        return new NioSocketChannelUnsafe();
    }

    private final class NioSocketChannelUnsafe extends NioByteUnsafe
    {
        @Override
        protected Executor prepareToClose()
        {
            try
            {
                if (javaChannel().isOpen() && config().getSoLinger() > 0)
                {
                    // We need to cancel this key of the channel so we may not end up in a eventloop spin
                    // because we try to read or write until the actual close happens which may be later due
                    // SO_LINGER handling.
                    // See https://github.com/netty/netty/issues/4449
                    doDeregister();
                    return GlobalEventExecutor.INSTANCE;
                }
            } catch (Throwable ignore)
            {
                // Ignore the error as the underlying channel may be closed in the meantime and so
                // getSoLinger() may produce an exception. In this case we just return null.
                // See https://github.com/netty/netty/issues/4449
            }
            return null;
        }
    }

    private final class NioSocketChannelConfig extends DefaultSocketChannelConfig
    {
        private NioSocketChannelConfig(NioSocketChannel channel, Socket javaSocket)
        {
            super(channel, javaSocket);
        }

        @Override
        protected void autoReadCleared()
        {
            clearReadPending();
        }
    }
}
