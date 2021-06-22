/*
 * Copyright 2013 The Netty Project
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
package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.PromiseNotificationUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * (Transport implementors only) an internal data structure used by {@link AbstractChannel} to store its pending
 * outbound write requests.
 * <p> ChannelOutboundBuffer 数据结构是用来存储 添加出站请求 的数据
 * <p>
 * All methods must be called by a transport implementation from an I/O thread, except the following ones:
 * 除以下方法外，所有方法都必须由I/O线程的传输实现调用：
 * <ul>
 * <li>{@link #size()} and {@link #isEmpty()}</li>
 * <li>{@link #isWritable()}</li>
 * <li>{@link #getUserDefinedWritability(int)} and {@link #setUserDefinedWritability(int, boolean)}</li>
 * </ul>
 * </p>
 * <p>
 * 它是一个通道的出站缓冲区，所有要写的数据都会先存在这里，等到要刷新的时候才会真的写出去
 */
public final class ChannelOutboundBuffer
{
    // Assuming a 64-bit JVM:
    //  - 16 bytes object header
    //  - 8 reference fields
    //  - 2 long fields
    //  - 2 int fields
    //  - 1 boolean field
    //  - padding
    //出站实体的额外开销96字节
    static final int CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.outboundBufferEntrySizeOverhead", 96);

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelOutboundBuffer.class);

    private static final FastThreadLocal<ByteBuffer[]> NIO_BUFFERS = new FastThreadLocal<ByteBuffer[]>()
    {
        @Override
        protected ByteBuffer[] initialValue() throws Exception
        {
            return new ByteBuffer[1024];
        }
    };

    //所绑定的Channel
    private final Channel channel;

    // Entry(flushedEntry) --> ... Entry(unflushedEntry) --> ... Entry(tailEntry)
    // The Entry that is the first in the linked-list structure that was flushed
    // 链表中已经被flush过的entity
    private Entry flushedEntry;

    // The Entry which is the first unflushed in the linked-list structure
    // 第一个没有被flush过的实体
    private Entry unflushedEntry;

    // The Entry which represents the tail of the buffer
    // 尾指针
    private Entry tailEntry;

    // The number of flushed entries that are not written yet
    // 将要写入socket的entry个数
    private int flushed;

    private int nioBufferCount;
    private long nioBufferSize;

    private boolean inFail;

    private static final AtomicLongFieldUpdater<ChannelOutboundBuffer> TOTAL_PENDING_SIZE_UPDATER = AtomicLongFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "totalPendingSize");

    @SuppressWarnings("UnusedDeclaration")
    private volatile long totalPendingSize;

    private static final AtomicIntegerFieldUpdater<ChannelOutboundBuffer> UNWRITABLE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "unwritable");

    // 是否可写的标志，0为可以写，1不能写入
    @SuppressWarnings("UnusedDeclaration")
    private volatile int unwritable;

    private volatile Runnable fireChannelWritabilityChangedTask;

    ChannelOutboundBuffer(AbstractChannel channel)
    {
        this.channel = channel;
    }

    /**
     * Add given message to this {@link ChannelOutboundBuffer}. The given {@link ChannelPromise} will be notified once
     * the message was written.
     */
    public void addMessage(Object msg, int size, ChannelPromise promise)
    {
        Entry entry = Entry.newInstance(msg, size, total(msg), promise);

        if (tailEntry == null)
        {
            // 第一次调用三者都为空，则将新的entry赋值给tailEntry和unflushedEntry，并且将flushedEntry置为空。
            flushedEntry = null;
            tailEntry = entry;
        } else
        {
            // 如果不是第一次调用，则将新的entry添加到tailEntry的后面
            Entry tail = tailEntry;
            tail.next = entry;
            tailEntry = entry;
        }
        if (unflushedEntry == null)
        {
            unflushedEntry = entry;
        }

        // increment pending bytes after adding message to the unflushed arrays.
        // See https://github.com/netty/netty/issues/1619
        // 统计当前有多少字节需要被写出
        incrementPendingOutboundBytes(entry.pendingSize, false);
    }

    /**
     * Add a flush to this {@link ChannelOutboundBuffer}. This means all previous added messages are marked as flushed
     * and so you will be able to handle them.
     */
    public void addFlush()
    {
        // There is no need to process all entries if there was already a flush before and no new messages
        // where added in the meantime.
        //
        // See https://github.com/netty/netty/issues/2577
        Entry entry = unflushedEntry;

        if (entry != null)
        {
            if (flushedEntry == null)
            {
                // there is no flushedEntry yet, so start with the entry
                flushedEntry = entry;
            }
            // 通过do-while将, 不断寻找unflushedEntry后面的节点, 直到没有节点为止
            do
            {
                //flushed自增代表需要刷新多少个节点
                flushed++;
                if (!entry.promise.setUncancellable())
                {
                    // Was cancelled so make sure we free up memory and notify about the freed bytes
                    int pending = entry.cancel();
                    decrementPendingOutboundBytes(pending, false, true);
                }
                entry = entry.next;
            } while (entry != null);

            // 调用flush()操作时就是将该flushedEntry单向链表中的entries的数据发到网络），并将unflushedEntry置为null，
            // 表示没有待发送的entries了。并通过flushed成员属性记录待发送entries的个数
            unflushedEntry = null;
        }
    }

    /**
     * Increment the pending bytes which will be written at some point.
     * This method is thread-safe!
     */
    void incrementPendingOutboundBytes(long size)
    {
        incrementPendingOutboundBytes(size, true);
    }

    // 统计当前有多少字节需要被写出
    private void incrementPendingOutboundBytes(long size, boolean invokeLater)
    {
        if (size == 0)
        {
            return;
        }
        // 计算 totalPendingSize
        long newWriteBufferSize = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, size);

        // 如果待写总字节数超过默认的64KB，也就是说写buffer的最大长度不能超过64k
        // 如果当前操作导致了待写出的数据（包括Entry对象大小以及真实需要传输数据的大小）超过了设置写缓冲区的高水位，
        // 那么将会触发fireChannelWritabilityChanged事件。
        if (newWriteBufferSize > channel.config().getWriteBufferHighWaterMark())
        {
            // 超过64 字节，则设置通过CAS修改unwritable标志位。
            setUnwritable(invokeLater);
        }
    }

    /**
     * Decrement the pending bytes which will be written at some point.
     * This method is thread-safe!
     */
    void decrementPendingOutboundBytes(long size)
    {
        decrementPendingOutboundBytes(size, true, true);
    }

    private void decrementPendingOutboundBytes(long size, boolean invokeLater, boolean notifyWritability)
    {
        if (size == 0)
        {
            return;
        }
        // 每次 减去 -size，newWriteBufferSize 为剩余可写大小
        long newWriteBufferSize = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -size);

        // getWriteBufferLowWaterMark() 默认32，如果newWriteBufferSize < 32 就把不可写状态改为可写状态
        if (notifyWritability && newWriteBufferSize < channel.config().getWriteBufferLowWaterMark())
        {
            setWritable(invokeLater);
        }
    }

    private static long total(Object msg)
    {
        if (msg instanceof ByteBuf)
        {
            return ((ByteBuf) msg).readableBytes();
        }
        if (msg instanceof FileRegion)
        {
            return ((FileRegion) msg).count();
        }
        if (msg instanceof ByteBufHolder)
        {
            return ((ByteBufHolder) msg).content().readableBytes();
        }
        return -1;
    }

    /**
     * Return the current message to write or {@code null} if nothing was flushed before and so is ready to be written.
     */
    public Object current()
    {
        Entry entry = flushedEntry;
        if (entry == null)
        {
            return null;
        }

        return entry.msg;
    }

    /**
     * Notify the {@link ChannelPromise} of the current message about writing progress.
     */
    public void progress(long amount)
    {
        Entry e = flushedEntry;
        assert e != null;
        ChannelPromise p = e.promise;
        if (p instanceof ChannelProgressivePromise)
        {
            long progress = e.progress + amount;
            e.progress = progress;
            ((ChannelProgressivePromise) p).tryProgress(progress, e.total);
        }
    }

    /**
     * Will remove the current message, mark its {@link ChannelPromise} as success and return {@code true}. If no
     * flushed message exists at the time this method is called it will return {@code false} to signal that no more
     * messages are ready to be handled.
     */
    public boolean remove()
    {
        // 获取当前的 Entry
        Entry e = flushedEntry;
        if (e == null)
        {
            clearNioBuffers();
            return false;
        }
        Object msg = e.msg;

        ChannelPromise promise = e.promise;
        int size = e.pendingSize;

        // 将当前的Entry进行移除
        removeEntry(e);

        if (!e.cancelled)
        {
            // only release message, notify and decrement if it was not canceled before.
            ReferenceCountUtil.safeRelease(msg);
            safeSuccess(promise);
            decrementPendingOutboundBytes(size, false, true);
        }

        // recycle the entry
        e.recycle();

        return true;
    }

    /**
     * Will remove the current message, mark its {@link ChannelPromise} as failure using the given {@link Throwable}
     * and return {@code true}. If no   flushed message exists at the time this method is called it will return
     * {@code false} to signal that no more messages are ready to be handled.
     */
    public boolean remove(Throwable cause)
    {
        return remove0(cause, true);
    }

    private boolean remove0(Throwable cause, boolean notifyWritability)
    {
        Entry e = flushedEntry;
        if (e == null)
        {
            clearNioBuffers();
            return false;
        }
        Object msg = e.msg;

        ChannelPromise promise = e.promise;
        int size = e.pendingSize;

        removeEntry(e);

        if (!e.cancelled)
        {
            // only release message, fail and decrement if it was not canceled before.
            ReferenceCountUtil.safeRelease(msg);

            safeFail(promise, cause);
            decrementPendingOutboundBytes(size, false, notifyWritability);
        }

        // recycle the entry
        e.recycle();

        return true;
    }

    private void removeEntry(Entry e)
    {
        if (--flushed == 0)
        { // todo 如果是最后一个节点, 把所有的指针全部设为 null
            // processed everything
            flushedEntry = null;
            if (e == tailEntry)
            {
                tailEntry = null;
                unflushedEntry = null;
            }
        } else
        { //todo 如果 不是最后一个节点, 把当前节点,移动到最后的 节点
            flushedEntry = e.next;
        }
    }

    /**
     * Removes the fully written entries and update the reader index of the partially written entry.
     * This operation assumes all messages in this buffer is {@link ByteBuf}.
     */
    public void removeBytes(long writtenBytes)
    {
        for (; ; )
        {
            Object msg = current();
            if (!(msg instanceof ByteBuf))
            {
                assert writtenBytes == 0;
                break;
            }

            final ByteBuf buf = (ByteBuf) msg;
            final int readerIndex = buf.readerIndex();
            final int readableBytes = buf.writerIndex() - readerIndex;

            if (readableBytes <= writtenBytes)
            {
                if (writtenBytes != 0)
                {
                    progress(readableBytes);
                    writtenBytes -= readableBytes;
                }
                remove();
            } else
            { // readableBytes > writtenBytes
                if (writtenBytes != 0)
                {
                    buf.readerIndex(readerIndex + (int) writtenBytes);
                    progress(writtenBytes);
                }
                break;
            }
        }
        clearNioBuffers();
    }

    // Clear all ByteBuffer from the array so these can be GC'ed.
    // See https://github.com/netty/netty/issues/3837
    private void clearNioBuffers()
    {
        int count = nioBufferCount;
        if (count > 0)
        {
            nioBufferCount = 0;
            Arrays.fill(NIO_BUFFERS.get(), 0, count, null);
        }
    }

    /**
     * Returns an array of direct NIO buffers if the currently pending messages are made of {@link ByteBuf} only.
     * {@link #nioBufferCount()} and {@link #nioBufferSize()} will return the number of NIO buffers in the returned
     * array and the total number of readable bytes of the NIO buffers respectively.
     * <p>
     * Note that the returned array is reused and thus should not escape
     * {@link AbstractChannel#doWrite(ChannelOutboundBuffer)}.
     * Refer to {@link NioSocketChannel#doWrite(ChannelOutboundBuffer)} for an example.
     * </p>
     *
     * 它依次出去每个待写的ByteBuf，然后根据ByteBuf的信息构建一个ByteBuffer
     * （这里的ByteBuf是一个堆外ByteBuf，因此构建出来的ByteBuffer也是一个堆外的ByteBuffer），
     * 并设置该ByteBuffer的readerIndex、readableBytes的值为ByteBuf对应的值。然后返回构建好的ByteBuffer[]数组。
     */
    public ByteBuffer[] nioBuffers()
    {
        long nioBufferSize = 0;
        int nioBufferCount = 0;
        final InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
        ByteBuffer[] nioBuffers = NIO_BUFFERS.get(threadLocalMap);
        Entry entry = flushedEntry;
        while (isFlushedEntry(entry) && entry.msg instanceof ByteBuf)
        {
            if (!entry.cancelled)
            {
                ByteBuf buf = (ByteBuf) entry.msg;
                final int readerIndex = buf.readerIndex();
                final int readableBytes = buf.writerIndex() - readerIndex;

                if (readableBytes > 0)
                {
                    if (Integer.MAX_VALUE - readableBytes < nioBufferSize)
                    {
                        // If the nioBufferSize + readableBytes will overflow an Integer we stop populate the
                        // ByteBuffer array. This is done as bsd/osx don't allow to write more bytes then
                        // Integer.MAX_VALUE with one writev(...) call and so will return 'EINVAL', which will
                        // raise an IOException. On Linux it may work depending on the
                        // architecture and kernel but to be safe we also enforce the limit here.
                        // This said writing more the Integer.MAX_VALUE is not a good idea anyway.
                        //
                        // See also:
                        // - https://www.freebsd.org/cgi/man.cgi?query=write&sektion=2
                        // - http://linux.die.net/man/2/writev
                        break;
                    }
                    nioBufferSize += readableBytes;
                    int count = entry.count;
                    if (count == -1)
                    {
                        //noinspection ConstantValueVariableUse
                        entry.count = count = buf.nioBufferCount();
                    }
                    int neededSpace = nioBufferCount + count;
                    if (neededSpace > nioBuffers.length)
                    {
                        nioBuffers = expandNioBufferArray(nioBuffers, neededSpace, nioBufferCount);
                        NIO_BUFFERS.set(threadLocalMap, nioBuffers);
                    }
                    if (count == 1)
                    {
                        ByteBuffer nioBuf = entry.buf;
                        if (nioBuf == null)
                        {
                            // cache ByteBuffer as it may need to create a new ByteBuffer instance if its a
                            // derived buffer
                            entry.buf = nioBuf = buf.internalNioBuffer(readerIndex, readableBytes);
                        }
                        nioBuffers[nioBufferCount++] = nioBuf;
                    } else
                    {
                        ByteBuffer[] nioBufs = entry.bufs;
                        if (nioBufs == null)
                        {
                            // cached ByteBuffers as they may be expensive to create in terms
                            // of Object allocation
                            entry.bufs = nioBufs = buf.nioBuffers();
                        }
                        nioBufferCount = fillBufferArray(nioBufs, nioBuffers, nioBufferCount);
                    }
                }
            }
            entry = entry.next;
        }
        this.nioBufferCount = nioBufferCount;
        this.nioBufferSize = nioBufferSize;

        return nioBuffers;
    }

    private static int fillBufferArray(ByteBuffer[] nioBufs, ByteBuffer[] nioBuffers, int nioBufferCount)
    {
        for (ByteBuffer nioBuf : nioBufs)
        {
            if (nioBuf == null)
            {
                break;
            }
            nioBuffers[nioBufferCount++] = nioBuf;
        }
        return nioBufferCount;
    }

    private static ByteBuffer[] expandNioBufferArray(ByteBuffer[] array, int neededSpace, int size)
    {
        int newCapacity = array.length;
        do
        {
            // double capacity until it is big enough
            // See https://github.com/netty/netty/issues/1890
            newCapacity <<= 1;

            if (newCapacity < 0)
            {
                throw new IllegalStateException();
            }

        } while (neededSpace > newCapacity);

        ByteBuffer[] newArray = new ByteBuffer[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);

        return newArray;
    }

    /**
     * Returns the number of {@link ByteBuffer} that can be written out of the {@link ByteBuffer} array that was
     * obtained via {@link #nioBuffers()}. This method <strong>MUST</strong> be called after {@link #nioBuffers()}
     * was called.
     */
    public int nioBufferCount()
    {
        return nioBufferCount;
    }

    /**
     * Returns the number of bytes that can be written out of the {@link ByteBuffer} array that was
     * obtained via {@link #nioBuffers()}. This method <strong>MUST</strong> be called after {@link #nioBuffers()}
     * was called.
     */
    public long nioBufferSize()
    {
        return nioBufferSize;
    }

    /**
     * Returns {@code true} if and only if {@linkplain #totalPendingWriteBytes() the total number of pending bytes} did
     * not exceed the write watermark of the {@link Channel} and
     * no {@linkplain #setUserDefinedWritability(int, boolean) user-defined writability flag} has been set to
     * {@code false}.
     */
    public boolean isWritable()
    {
        return unwritable == 0;
    }

    /**
     * Returns {@code true} if and only if the user-defined writability flag at the specified index is set to
     * {@code true}.
     */
    public boolean getUserDefinedWritability(int index)
    {
        return (unwritable & writabilityMask(index)) == 0;
    }

    /**
     * Sets a user-defined writability flag at the specified index.
     */
    public void setUserDefinedWritability(int index, boolean writable)
    {
        if (writable)
        {
            setUserDefinedWritability(index);
        } else
        {
            clearUserDefinedWritability(index);
        }
    }

    private void setUserDefinedWritability(int index)
    {
        final int mask = ~writabilityMask(index);
        for (; ; )
        {
            final int oldValue = unwritable;
            final int newValue = oldValue & mask;
            if (UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue))
            {
                if (oldValue != 0 && newValue == 0)
                {
                    fireChannelWritabilityChanged(true);
                }
                break;
            }
        }
    }

    private void clearUserDefinedWritability(int index)
    {
        final int mask = writabilityMask(index);
        for (; ; )
        {
            final int oldValue = unwritable;
            final int newValue = oldValue | mask;
            if (UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue))
            {
                if (oldValue == 0 && newValue != 0)
                {
                    fireChannelWritabilityChanged(true);
                }
                break;
            }
        }
    }

    private static int writabilityMask(int index)
    {
        if (index < 1 || index > 31)
        {
            throw new IllegalArgumentException("index: " + index + " (expected: 1~31)");
        }
        return 1 << index;
    }

    private void setWritable(boolean invokeLater)
    {
        for (; ; )
        {
            final int oldValue = unwritable;
            final int newValue = oldValue & ~1;
            if (UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue))
            {
                if (oldValue != 0 && newValue == 0)
                {
                    fireChannelWritabilityChanged(invokeLater);
                }
                break;
            }
        }
    }

    // 通过自旋锁机制, 传播 ChannelWritabilityChanged(invokeLater);  传播channel不能写状态
    private void setUnwritable(boolean invokeLater)
    {
        for (; ; )
        {
            final int oldValue = unwritable;
            final int newValue = oldValue | 1;
            if (UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue))
            {
                if (oldValue == 0 && newValue != 0)
                {
                    fireChannelWritabilityChanged(invokeLater);
                }
                break;
            }
        }
    }

    private void fireChannelWritabilityChanged(boolean invokeLater)
    {
        final ChannelPipeline pipeline = channel.pipeline();
        if (invokeLater)
        {
            Runnable task = fireChannelWritabilityChangedTask;
            if (task == null)
            {
                fireChannelWritabilityChangedTask = task = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        pipeline.fireChannelWritabilityChanged();
                    }
                };
            }
            channel.eventLoop().execute(task);
        } else
        {
            pipeline.fireChannelWritabilityChanged();
        }
    }

    /**
     * Returns the number of flushed messages in this {@link ChannelOutboundBuffer}.
     */
    public int size()
    {
        return flushed;
    }

    /**
     * Returns {@code true} if there are flushed messages in this {@link ChannelOutboundBuffer} or {@code false}
     * otherwise.
     */
    public boolean isEmpty()
    {
        return flushed == 0;
    }

    void failFlushed(Throwable cause, boolean notify)
    {
        // Make sure that this method does not reenter.  A listener added to the current promise can be notified by the
        // current thread in the tryFailure() call of the loop below, and the listener can trigger another fail() call
        // indirectly (usually by closing the channel.)
        //
        // See https://github.com/netty/netty/issues/1501
        if (inFail)
        {
            return;
        }

        try
        {
            inFail = true;
            for (; ; )
            {
                if (!remove0(cause, notify))
                {
                    break;
                }
            }
        } finally
        {
            inFail = false;
        }
    }

    void close(final Throwable cause, final boolean allowChannelOpen)
    {
        if (inFail)
        {
            channel.eventLoop().execute(new Runnable()
            {
                @Override
                public void run()
                {
                    close(cause, allowChannelOpen);
                }
            });
            return;
        }

        inFail = true;

        if (!allowChannelOpen && channel.isOpen())
        {
            throw new IllegalStateException("close() must be invoked after the channel is closed.");
        }

        if (!isEmpty())
        {
            throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
        }

        // Release all unflushed messages.
        try
        {
            Entry e = unflushedEntry;
            while (e != null)
            {
                // Just decrease; do not trigger any events via decrementPendingOutboundBytes()
                int size = e.pendingSize;
                TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -size);

                if (!e.cancelled)
                {
                    ReferenceCountUtil.safeRelease(e.msg);
                    safeFail(e.promise, cause);
                }
                e = e.recycleAndGetNext();
            }
        } finally
        {
            inFail = false;
        }
        clearNioBuffers();
    }

    void close(ClosedChannelException cause)
    {
        close(cause, false);
    }

    private static void safeSuccess(ChannelPromise promise)
    {
        // Only log if the given promise is not of type VoidChannelPromise as trySuccess(...) is expected to return
        // false.
        PromiseNotificationUtil.trySuccess(promise, null, promise instanceof VoidChannelPromise ? null : logger);
    }

    private static void safeFail(ChannelPromise promise, Throwable cause)
    {
        // Only log if the given promise is not of type VoidChannelPromise as tryFailure(...) is expected to return
        // false.
        PromiseNotificationUtil.tryFailure(promise, cause, promise instanceof VoidChannelPromise ? null : logger);
    }

    @Deprecated
    public void recycle()
    {
        // NOOP
    }

    public long totalPendingWriteBytes()
    {
        return totalPendingSize;
    }

    /**
     * Get how many bytes can be written until {@link #isWritable()} returns {@code false}.
     * This quantity will always be non-negative. If {@link #isWritable()} is {@code false} then 0.
     */
    public long bytesBeforeUnwritable()
    {
        long bytes = channel.config().getWriteBufferHighWaterMark() - totalPendingSize;
        // If bytes is negative we know we are not writable, but if bytes is non-negative we have to check writability.
        // Note that totalPendingSize and isWritable() use different volatile variables that are not synchronized
        // together. totalPendingSize will be updated before isWritable().
        if (bytes > 0)
        {
            return isWritable() ? bytes : 0;
        }
        return 0;
    }

    /**
     * Get how many bytes must be drained from the underlying buffer until {@link #isWritable()} returns {@code true}.
     * This quantity will always be non-negative. If {@link #isWritable()} is {@code true} then 0.
     */
    public long bytesBeforeWritable()
    {
        long bytes = totalPendingSize - channel.config().getWriteBufferLowWaterMark();
        // If bytes is negative we know we are writable, but if bytes is non-negative we have to check writability.
        // Note that totalPendingSize and isWritable() use different volatile variables that are not synchronized
        // together. totalPendingSize will be updated before isWritable().
        if (bytes > 0)
        {
            return isWritable() ? 0 : bytes;
        }
        return 0;
    }

    /**
     * Call {@link MessageProcessor#processMessage(Object)} for each flushed message
     * in this {@link ChannelOutboundBuffer} until {@link MessageProcessor#processMessage(Object)}
     * returns {@code false} or there are no more flushed messages to process.
     */
    public void forEachFlushedMessage(MessageProcessor processor) throws Exception
    {
        if (processor == null)
        {
            throw new NullPointerException("processor");
        }

        Entry entry = flushedEntry;
        if (entry == null)
        {
            return;
        }

        do
        {
            if (!entry.cancelled)
            {
                if (!processor.processMessage(entry.msg))
                {
                    return;
                }
            }
            entry = entry.next;
        } while (isFlushedEntry(entry));
    }

    private boolean isFlushedEntry(Entry e)
    {
        return e != null && e != unflushedEntry;
    }

    public interface MessageProcessor
    {
        /**
         * Will be called for each flushed message until it either there are no more flushed messages or this
         * method returns {@code false}.
         */
        boolean processMessage(Object msg) throws Exception;
    }

    static final class Entry
    {
        private static final Recycler<Entry> RECYCLER = new Recycler<Entry>()
        {
            @Override
            protected Entry newObject(Handle<Entry> handle)
            {
                return new Entry(handle);
            }
        };

        private final Handle<Entry> handle;
        Entry next;
        // 原始消息对象的引用
        Object msg;
        ByteBuffer[] bufs;
        ByteBuffer buf;
        ChannelPromise promise;
        long progress;
        // 待发送数据包的总大小（该属性与pendingSize的区别在于，如果是待发送的是FileRegion数据对象，则pengdingSize中只有对象内存的大小，
        // 即真实的数据大小被记录为0；但total属性则是会记录FileRegion中数据大小，并且total属性是不包含对象内存大小，仅仅是对数据本身大小的记录
        long total;
        // 记录有该ByteBuf or ByteBufs 中待发送数据大小 和 对象本身内存大小 的累加和;
        // 一个对象占用的内存大小除了实例数据（instance data），还包括对象头（header）以及对齐填充（padding）。
        // 所以一个对象所占的内存大小为『对象头 + 实例数据 + 对齐填充』
        // 假设的是64位操作系统下，且没有使用各种压缩选项的情况。对象头的长度占16字节；引用属性占8字节；long类型占8字节；int类型占4字节；
        // boolean类型占1字节。同时，由于HotSpot VM的自动内存管理系统要求对象起始地址必须是8字节的整数倍，也就是说对象的大小必须是8字节的整数倍，
        // 如果最终字节数不为8的倍数，则padding会补足至8的倍数
        // static final int CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.outboundBufferEntrySizeOverhead", 96);
        int pendingSize;
        // 写消息数据个数的记录（如果写消息数据是个数组的话，该值会大于1）
        int count = -1;
        boolean cancelled;

        private Entry(Handle<Entry> handle)
        {
            this.handle = handle;
        }

        static Entry newInstance(Object msg, int size, long total, ChannelPromise promise)
        {
            Entry entry = RECYCLER.get();
            entry.msg = msg;
            entry.pendingSize = size + CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD;
            entry.total = total;
            entry.promise = promise;
            return entry;
        }

        int cancel()
        {
            if (!cancelled)
            {
                cancelled = true;
                int pSize = pendingSize;

                // release message and replace with an empty buffer
                ReferenceCountUtil.safeRelease(msg);
                msg = Unpooled.EMPTY_BUFFER;

                pendingSize = 0;
                total = 0;
                progress = 0;
                bufs = null;
                buf = null;
                return pSize;
            }
            return 0;
        }

        void recycle()
        {
            next = null;
            bufs = null;
            buf = null;
            msg = null;
            promise = null;
            progress = 0;
            total = 0;
            pendingSize = 0;
            count = -1;
            cancelled = false;
            handle.recycle(this);
        }

        Entry recycleAndGetNext()
        {
            Entry next = this.next;
            recycle();
            return next;
        }
    }
}
