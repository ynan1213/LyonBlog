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
package io.netty.channel.nio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopException;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.IntSupplier;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReflectionUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@link SingleThreadEventLoop} implementation which register the {@link Channel}'s to a
 * {@link Selector} and so does the multi-plexing of these in the event loop.
 */
public final class NioEventLoop extends SingleThreadEventLoop {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioEventLoop.class);

    private static final int CLEANUP_INTERVAL = 256; // XXX Hard-coded value, but won't need customization.

    private static final boolean DISABLE_KEYSET_OPTIMIZATION =
            SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);

    private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
    private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;

    private final IntSupplier selectNowSupplier = new IntSupplier() {
        @Override
        public int get() throws Exception {
            return selectNow();
        }
    };
    private final Callable<Integer> pendingTasksCallable = new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {
            return NioEventLoop.super.pendingTasks();
        }
    };

    // Workaround for JDK NIO bug.
    //
    // See:
    // - http://bugs.sun.com/view_bug.do?bug_id=6427854
    // - https://github.com/netty/netty/issues/203
    static {
        final String key = "sun.nio.ch.bugLevel";
        final String buglevel = SystemPropertyUtil.get(key);
        if (buglevel == null) {
            try {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    @Override
                    public Void run() {
                        System.setProperty(key, "");
                        return null;
                    }
                });
            } catch (final SecurityException e) {
                logger.debug("Unable to get/set System Property: " + key, e);
            }
        }

        int selectorAutoRebuildThreshold = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
        if (selectorAutoRebuildThreshold < MIN_PREMATURE_SELECTOR_RETURNS) {
            selectorAutoRebuildThreshold = 0;
        }

        SELECTOR_AUTO_REBUILD_THRESHOLD = selectorAutoRebuildThreshold;

        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.noKeySetOptimization: {}", DISABLE_KEYSET_OPTIMIZATION);
            logger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", SELECTOR_AUTO_REBUILD_THRESHOLD);
        }
    }

    /**
     * The NIO {@link Selector}.
     */
    private Selector selector;
    private Selector unwrappedSelector;
    private SelectedSelectionKeySet selectedKeys;

    private final SelectorProvider provider;

    /**
     * Boolean that controls determines if a blocked Selector.select should
     * break out of its selection process. In our case we use a timeout for
     * the select method and the select method will block for that time unless
     * waken up.
     */
    private final AtomicBoolean wakenUp = new AtomicBoolean();

    private final SelectStrategy selectStrategy;

    private volatile int ioRatio = 50;
    private int cancelledKeys;
    private boolean needsToSelectAgain;

    //default 修饰构造函数，只有包访问权限
    NioEventLoop(NioEventLoopGroup parent, Executor executor, SelectorProvider selectorProvider,
                 SelectStrategy strategy, RejectedExecutionHandler rejectedExecutionHandler) {

        /**
         * 父类创建TaskQueue(创建了两个队列，tailTasks和taskQueue）
         * 注意第三个参数 addTaskWakesUp 置为false
         */
        super(parent, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);

        if (selectorProvider == null) {
            throw new NullPointerException("selectorProvider");
        }
        if (strategy == null) {
            throw new NullPointerException("selectStrategy");
        }

        //每一个NioEventLoop拥有一个SelectorProvider
        provider = selectorProvider;

        // 每一个NioEventLoop都创建了个属于自己的Selector
        // 获取Selector选择器, 和原生jdk有些出入
        final SelectorTuple selectorTuple = openSelector();

        // SelectorTuple是netty维护jdk 原生的Selector的包装类, 下面看,他有两个Selector,一个是经过包装的,一个是未经过包装的
        selector = selectorTuple.selector;  //
        unwrappedSelector = selectorTuple.unwrappedSelector;  //Jdk 原生的Selector
        selectStrategy = strategy;
    }

    private static final class SelectorTuple {
        final Selector unwrappedSelector;
        final Selector selector;

        SelectorTuple(Selector unwrappedSelector) {
            this.unwrappedSelector = unwrappedSelector;
            this.selector = unwrappedSelector;
        }

        SelectorTuple(Selector unwrappedSelector, Selector selector) {
            this.unwrappedSelector = unwrappedSelector;
            this.selector = selector;
        }
    }

    /**
     * 这里进行了优化,netty把hashSet转换成了数组
     * 因为在JDK的NIO模型中,获取Selector时,Selector里面内置的存放SelectionKey的容器是Set集合
     *
     * 说这个优化之前就不得不回想一下原生的JDK的 NIO编程模型中的几大组件, 1. Selector 2. Channel 3. ByteBuffer
     * 其中的Selector 中主要维护了三个set集合, 分别是 1. keySet  2. Selectedkey  3.cannelledKey  这三个容器的底层都是set结构
     *
     * 而netty把上面的SelectedKey 替换成了自己的的数据接口, 数组, 从而使在任何情况下,它的时间复杂度都是 O1
     */
    private SelectorTuple openSelector() {
        final Selector unwrappedSelector;
        try {
            // 使用原生jdk的api创建新的selector
            unwrappedSelector = provider.openSelector();
        } catch (IOException e) {
            throw new ChannelException("failed to open a new selector", e);
        }

        // 如果不需要优化,就返回原生的selector , 默认为false 即使用优化
        if (DISABLE_KEYSET_OPTIMIZATION) {
            return new SelectorTuple(unwrappedSelector);
        }
        // 接下来 netty会用下面这个SelectedSelectionKeySet数据结构 替换原来的 keySet
        final SelectedSelectionKeySet selectedKeySet = new SelectedSelectionKeySet();

        Object maybeSelectorImplClass = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    // 通过反射  sun.nio.ch.SelectorImpl 或者这个类
                    return Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
                } catch (Throwable cause) {
                    return cause;
                }
            }
        });
        // 判断是否获取到了这个类
        if (!(maybeSelectorImplClass instanceof Class) ||
                // ensure the current selector implementation is what we can instrument.
                !((Class<?>) maybeSelectorImplClass).isAssignableFrom(unwrappedSelector.getClass())) {
            if (maybeSelectorImplClass instanceof Throwable) {
                Throwable t = (Throwable) maybeSelectorImplClass;
                logger.trace("failed to instrument a special java.util.Set into: {}", unwrappedSelector, t);
            }
            return new SelectorTuple(unwrappedSelector);
        }

        // 确定是Selector的实现类  换了个名字
        final Class<?> selectorImplClass = (Class<?>) maybeSelectorImplClass;

        /**
         * 类java.security.AccessController提供了一个默认的安全策略执行机制，它使用栈检查来决定潜在不安全的操作是否被允许。
         * 这个访问控制器不能被实例化，它不是一个对象，而是集合在单个类中的多个静态方法。
         */
        Object maybeException = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    // 通过反射, 获取到 selectorImplClass的两个字段 selectedKeys   publicSelectedKeys
                    // selectedKeys   publicSelectedKeys底层都是 hashSet() 实现的, 现在获取出来了, 放入上面的数组数据结构中
                    Field selectedKeysField = selectorImplClass.getDeclaredField("selectedKeys");
                    Field publicSelectedKeysField = selectorImplClass.getDeclaredField("publicSelectedKeys");

                    // trySetAccessible 可以强制访问私有的对象
                    Throwable cause = ReflectionUtil.trySetAccessible(selectedKeysField);
                    if (cause != null) {
                        return cause;
                    }
                    // trySetAccessible 可以强制访问私有的对象
                    cause = ReflectionUtil.trySetAccessible(publicSelectedKeysField);
                    if (cause != null) {
                        return cause;
                    }
                    // 真正的把通过反射得到的 那两个字段放入我们自己的数据结构中
                    // 下面是把我们的NioEventLoop中的 unwrappedSelector 的 selectedKeysField的属性 直接设置成 优化后的selectedKeySet
                    selectedKeysField.set(unwrappedSelector, selectedKeySet);
                    publicSelectedKeysField.set(unwrappedSelector, selectedKeySet);
                    return null;
                } catch (NoSuchFieldException e) {
                    return e;
                } catch (IllegalAccessException e) {
                    return e;
                }
            }
        });

        if (maybeException instanceof Exception) {
            selectedKeys = null;
            Exception e = (Exception) maybeException;
            logger.trace("failed to instrument a special java.util.Set into: {}", unwrappedSelector, e);
            return new SelectorTuple(unwrappedSelector);
        }

        // 初始化自己维护被选中的key的集合  --> 数组类型的
        selectedKeys = selectedKeySet;
        logger.trace("instrumented a special java.util.Set into: {}", unwrappedSelector);
        return new SelectorTuple(unwrappedSelector, new SelectedSelectionKeySetSelector(unwrappedSelector, selectedKeySet));
    }

    /**
     * Returns the {@link SelectorProvider} used by this {@link NioEventLoop} to obtain the {@link Selector}.
     */
    public SelectorProvider selectorProvider() {
        return provider;
    }

    /**
     * 来这里之前, 我们将线程的执行器executor交给了EventLoop管理, 使得EventLoop拥有了执行任务的能力
     * 现在, 为每一个EventLoop创建一个任务队列, MpscQueue, 这个队列的特点就是使用于Netty现在使用的线程模型, 单消费者,多生产者
     *
     * • SPSC：单个生产者对单个消费者（无等待、有界和无界都有实现）
     * • MPSC：多个生产者对单个消费者（无锁、有界和无界都有实现）
     * • SPMC：单生产者对多个消费者（无锁 有界）
     * • MPMC：多生产者对多个消费者（无锁、有界）
     */
    @Override
    protected Queue<Runnable> newTaskQueue(int maxPendingTasks) {
        // This event loop never calls takeTask()
        return maxPendingTasks == Integer.MAX_VALUE ?
            PlatformDependent.<Runnable>newMpscQueue() : PlatformDependent.<Runnable>newMpscQueue(maxPendingTasks);
    }

    @Override
    public int pendingTasks() {
        // As we use a MpscQueue we need to ensure pendingTasks() is only executed from within the EventLoop as
        // otherwise we may see unexpected behavior (as size() is only allowed to be called by a single consumer).
        // See https://github.com/netty/netty/issues/5297
        if (inEventLoop()) {
            return super.pendingTasks();
        } else {
            return submit(pendingTasksCallable).syncUninterruptibly().getNow();
        }
    }

    /**
     * Registers an arbitrary {@link SelectableChannel}, not necessarily created by Netty, to the {@link Selector}
     * of this event loop.  Once the specified {@link SelectableChannel} is registered, the specified {@code task} will
     * be executed by this event loop when the {@link SelectableChannel} is ready.
     */
    public void register(final SelectableChannel ch, final int interestOps, final NioTask<?> task) {
        if (ch == null) {
            throw new NullPointerException("ch");
        }
        if (interestOps == 0) {
            throw new IllegalArgumentException("interestOps must be non-zero.");
        }
        if ((interestOps & ~ch.validOps()) != 0) {
            throw new IllegalArgumentException(
                    "invalid interestOps: " + interestOps + "(validOps: " + ch.validOps() + ')');
        }
        if (task == null) {
            throw new NullPointerException("task");
        }

        if (isShutdown()) {
            throw new IllegalStateException("event loop shut down");
        }

        try {
            ch.register(selector, interestOps, task);
        } catch (Exception e) {
            throw new EventLoopException("failed to register a channel", e);
        }
    }

    /**
     * Returns the percentage of the desired amount of time spent for I/O in the event loop.
     */
    public int getIoRatio() {
        return ioRatio;
    }

    /**
     * Sets the percentage of the desired amount of time spent for I/O in the event loop.  The default value is
     * {@code 50}, which means the event loop will try to spend the same amount of time for I/O as for non-I/O tasks.
     */
    public void setIoRatio(int ioRatio) {
        if (ioRatio <= 0 || ioRatio > 100) {
            throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio <= 100)");
        }
        this.ioRatio = ioRatio;
    }

    /**
     * Replaces the current {@link Selector} of this event loop with newly created {@link Selector}s to work
     * around the infamous epoll 100% CPU bug.
     */
    public void rebuildSelector() {
        if (!inEventLoop()) {
            execute(new Runnable() {
                @Override
                public void run() {
                    rebuildSelector0();
                }
            });
            return;
        }
        rebuildSelector0();
    }

    private void rebuildSelector0() {
        final Selector oldSelector = selector;
        final SelectorTuple newSelectorTuple;

        if (oldSelector == null) {
            return;
        }

        try {
            // 重新创建一个selector
            newSelectorTuple = openSelector();
        } catch (Exception e) {
            logger.warn("Failed to create a new Selector.", e);
            return;
        }

        // Register all channels to the new Selector.
        int nChannels = 0;
        // 循环原来的keys
        for (SelectionKey key : oldSelector.keys()) {
            Object a = key.attachment();
            try {
                if (!key.isValid() || key.channel().keyFor(newSelectorTuple.unwrappedSelector) != null) {
                    continue;
                }

                int interestOps = key.interestOps();
                // 把原来的key取消,
                key.cancel();
                // 把原来的注册进新的seleor上面
                SelectionKey newKey = key.channel().register(newSelectorTuple.unwrappedSelector, interestOps, a);
                if (a instanceof AbstractNioChannel) {
                    // Update SelectionKey
                    ((AbstractNioChannel) a).selectionKey = newKey;
                }
                nChannels++;
            } catch (Exception e) {
                logger.warn("Failed to re-register a Channel to the new Selector.", e);
                if (a instanceof AbstractNioChannel) {
                    AbstractNioChannel ch = (AbstractNioChannel) a;
                    ch.unsafe().close(ch.unsafe().voidPromise());
                } else {
                    @SuppressWarnings("unchecked")
                    NioTask<SelectableChannel> task = (NioTask<SelectableChannel>) a;
                    invokeChannelUnregistered(task, key, e);
                }
            }
        }

        selector = newSelectorTuple.selector;
        unwrappedSelector = newSelectorTuple.unwrappedSelector;

        try {
            // time to close the old selector as everything else is registered to the new one
            oldSelector.close();
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to close the old Selector.", t);
            }
        }

        //logger.info("Migrated " + nChannels + " channel(s) to the new Selector.");
    }

    /**
     *  select()                    检查是否有IO事件
     *  ProcessorSelectedKeys()     处理IO事件
     *  RunAllTask()                处理非IO事件(异步任务队列)
     */
    @Override
    protected void run() {
        for (; ; ) {
            try {
                /**
                 * 根据是否有非IO任务 hasTasks() 判断：
                 *    如果有，执行selectNowSupplier.get方法(Selector.selectNow()非阻塞式)，该方法返回值 >= 0，不会进入任何case
                 *    如果没有，进入case:SelectStrategy.SELECT。
                 * 从run方法的整体顺序中可以看到，每次循环中都是先执行IO任务，再执行非IO任务。
                 * 但如果队列中有非IO任务待处理，那么为提高框架处理性能，就不允许执行阻塞的select方法，
                 * 而是执行非阻塞的selectNow方法，这样就能快速处理完channel事件后去处理队列中的任务。
                 *
                 * 当SelectStrategy的实现类是DefaultSelectStrategy的情况下，好像永远进不了case:SelectStrategy.CONTINUE
                 */
                switch (selectStrategy.calculateStrategy(selectNowSupplier, hasTasks())) {
                    case SelectStrategy.CONTINUE:
                        continue;
                    case SelectStrategy.SELECT:
                        /**
                         * --------------------------------------------
                         * AtomicBoolean.getAndSet(boolean):以原子方式设置为给定值，并返回以前的值
                         * AtomicBoolean.compareAndSet(boolean A, boolean B):内存值V，旧的预期值A，要修改的新值B,
                         * 当且仅当预期值A和内存值V相同时，将内存值V修改为B，并返回true，否则什么都不做，并返回false
                         * --------------------------------------------
                         * wakenUp变量：
                         *      如果为true，则表示最近调用过wakeup方法，
                         *      如果为false，则表示最近未调用过wakeup方法,
                         * 该变量在本类的protected void wakeup(boolean inEventLoop)方法内被设置为true。
                         *
                         * 既然走到了这一步，说明任务队列没有需要处理的任务
                         * 进入select(boolean)方法都会将wakenUp置为false,然后将原值传进去
                         * 原值什么时候会为true呢？说明是走到这一步后外部线程添加了任务，并且将wakenUp置为true
                         */
                        select(wakenUp.getAndSet(false));

                        if (wakenUp.get()) {
                            selector.wakeup();
                        }
                    default:
                }

                cancelledKeys = 0;//已取消的key的数量
                needsToSelectAgain = false;//是否需要再次选择

                final int ioRatio = this.ioRatio;//控制 IO 操作所占的时间比重
                if (ioRatio == 100) {
                    //如果设置为 100，那么先执行 IO 操作，然后再执行任务队列中的任务。
                    try {
                        processSelectedKeys();//IO操作，不限制时间
                    } finally {
                        runAllTasks();//非IO操作，不限制时间
                    }
                } else {
                    //如果不是100，那么先执行 IO 操作，然后执行 taskQueue 中的任务
                    //也就是说，非IO操作可以占用的时间，通过 ioRatio 以及这次 IO 操作耗时计算得出。
                    final long ioStartTime = System.nanoTime();
                    try {
                        processSelectedKeys();//IO操作，不限制时间
                    } finally {
                        final long ioTime = System.nanoTime() - ioStartTime;
                        runAllTasks(ioTime * (100 - ioRatio) / ioRatio);//非IO操作，限制时间
                    }
                }
            } catch (Throwable t) {
                handleLoopException(t);
            }
            // Always handle shutdown even if the loop processing threw an exception.
            try {
                if (isShuttingDown()) {
                    closeAll();
                    if (confirmShutdown()) {
                        return;
                    }
                }
            } catch (Throwable t) {
                handleLoopException(t);
            }
        }
    }

    private static void handleLoopException(Throwable t) {
        logger.warn("Unexpected exception in the selector loop.", t);

        // Prevent possible consecutive immediate failures that lead to
        // excessive CPU consumption.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore.
        }
    }

    private void processSelectedKeys() {
        if (selectedKeys != null) {
            // selectedKeys 不为空，代表是优化之后的处理
            processSelectedKeysOptimized();
        } else {
            //未优化的处理
            processSelectedKeysPlain(selector.selectedKeys());
        }
    }

    @Override
    protected void cleanup() {
        try {
            selector.close();
        } catch (IOException e) {
            logger.warn("Failed to close a selector.", e);
        }
    }

    void cancel(SelectionKey key) {
        key.cancel();
        cancelledKeys++;
        if (cancelledKeys >= CLEANUP_INTERVAL) {
            cancelledKeys = 0;
            needsToSelectAgain = true;
        }
    }

    @Override
    protected Runnable pollTask() {
        Runnable task = super.pollTask();
        if (needsToSelectAgain) {
            selectAgain();
        }
        return task;
    }

    private void processSelectedKeysPlain(Set<SelectionKey> selectedKeys) {
        // check if the set is empty and if so just return to not create garbage by
        // creating a new Iterator every time even if there is nothing to process.
        // See https://github.com/netty/netty/issues/597
        if (selectedKeys.isEmpty()) {
            return;
        }

        Iterator<SelectionKey> i = selectedKeys.iterator();
        for (; ; ) {
            final SelectionKey k = i.next();
            final Object a = k.attachment();
            i.remove();

            if (a instanceof AbstractNioChannel) {
                processSelectedKey(k, (AbstractNioChannel) a);
            } else {
                @SuppressWarnings("unchecked")
                NioTask<SelectableChannel> task = (NioTask<SelectableChannel>) a;
                processSelectedKey(k, task);
            }

            if (!i.hasNext()) {
                break;
            }

            if (needsToSelectAgain) {
                selectAgain();
                selectedKeys = selector.selectedKeys();

                // Create the iterator again to avoid ConcurrentModificationException
                if (selectedKeys.isEmpty()) {
                    break;
                } else {
                    i = selectedKeys.iterator();
                }
            }
        }
    }

    private void processSelectedKeysOptimized() {
        for (int i = 0; i < selectedKeys.size; ++i) {
            final SelectionKey k = selectedKeys.keys[i];

            /**
             * null out entry in the array to allow to have it GC'ed once the Channel close
             * 数组输出空项, 从而允许在channel 关闭时对其进行垃圾回收
             * See https://github.com/netty/netty/issues/2363
             * 数组中当前循环对应的keys置空, 这种感兴趣的事件只处理一次就行
             */
            selectedKeys.keys[i] = null;

            /**
             * 获取出 attachment,默认情况下就是注册进Selector时,传入的第三个参数  this ===> NioServerSocketChannel
             * 一个Selector中可能被绑定上了成千上万个Channel,  通过K+attachment 的手段, 精确的取出发生指定事件的channel,
             * 进而获取channel中的unsafe类进行下一步处理
             */
            final Object a = k.attachment();

            if (a instanceof AbstractNioChannel) {
                processSelectedKey(k, (AbstractNioChannel) a);
            } else {
                @SuppressWarnings("unchecked")
                NioTask<SelectableChannel> task = (NioTask<SelectableChannel>) a;
                processSelectedKey(k, task);
            }

            if (needsToSelectAgain) {
                // null out entries in the array to allow to have it GC'ed once the Channel close
                // See https://github.com/netty/netty/issues/2363
                selectedKeys.reset(i + 1);

                selectAgain();
                i = -1;
            }
        }
    }

    private void processSelectedKey(SelectionKey k, AbstractNioChannel ch) {
        final AbstractNioChannel.NioUnsafe unsafe = ch.unsafe();
        if (!k.isValid()) {
            //针对无效key的处理
            final EventLoop eventLoop;
            try {
                eventLoop = ch.eventLoop();
            } catch (Throwable ignored) {
                // If the channel implementation throws an exception because there is no event loop, we ignore this
                // because we are only trying to determine if ch is registered to this event loop and thus has authority
                // to close ch.
                return;
            }
            // Only close ch if ch is still registered to this EventLoop. ch could have deregistered from the event loop
            // and thus the SelectionKey could be cancelled as part of the deregistration process, but the channel is
            // still healthy and should not be closed.
            // See https://github.com/netty/netty/issues/5125
            if (eventLoop != this || eventLoop == null) {
                return;
            }
            // close the channel if the key is not valid anymore
            unsafe.close(unsafe.voidPromise());
            return;
        }

        try {
            /**
             * OP_READ = 1 << 0     =1
             * OP_WRITE = 1 << 2    =4
             * OP_CONNECT = 1 << 3  =8
             * OP_ACCEPT = 1 << 4   =16
             */
            int readyOps = k.readyOps();

            /**
             * We first need to call finishConnect() before try to trigger a read(...) or write(...) as otherwise
             * the NIO JDK channel implementation may throw a NotYetConnectedException.
             *
             *  在read()、write()之前我们需要调用 finishConnect() 方法，确认连接成功
             */
            if ((readyOps & SelectionKey.OP_CONNECT) != 0) {
                // remove OP_CONNECT as otherwise Selector.select(..) will always return without blocking
                // See https://github.com/netty/netty/issues/924
                int ops = k.interestOps();
                ops &= ~SelectionKey.OP_CONNECT;
                k.interestOps( );
                unsafe.finishConnect();
            }

            // Process OP_WRITE first as we may be able to write some queued buffers and so free memory.
            if ((readyOps & SelectionKey.OP_WRITE) != 0) {
                // Call forceFlush which will also take care of clear the OP_WRITE once there is nothing left to  write
                ch.unsafe().forceFlush();
            }

            // Also check for readOps of 0 to workaround possible JDK bug which may otherwise lead
            // to a spin loop
            // 读事件和接受连接事件
            if ((readyOps & (SelectionKey.OP_READ | SelectionKey.OP_ACCEPT)) != 0 || readyOps == 0) {
                unsafe.read();
            }
        } catch (CancelledKeyException ignored) {
            unsafe.close(unsafe.voidPromise());
        }
    }

    private static void processSelectedKey(SelectionKey k, NioTask<SelectableChannel> task) {
        int state = 0;
        try {
            task.channelReady(k.channel(), k);
            state = 1;
        } catch (Exception e) {
            k.cancel();
            invokeChannelUnregistered(task, k, e);
            state = 2;
        } finally {
            switch (state) {
                case 0:
                    k.cancel();
                    invokeChannelUnregistered(task, k, null);
                    break;
                case 1:
                    if (!k.isValid()) { // Cancelled by channelReady()
                        invokeChannelUnregistered(task, k, null);
                    }
                    break;
            }
        }
    }

    private void closeAll() {
        selectAgain();
        Set<SelectionKey> keys = selector.keys();
        Collection<AbstractNioChannel> channels = new ArrayList<AbstractNioChannel>(keys.size());
        for (SelectionKey k : keys) {
            Object a = k.attachment();
            if (a instanceof AbstractNioChannel) {
                channels.add((AbstractNioChannel) a);
            } else {
                k.cancel();
                @SuppressWarnings("unchecked")
                NioTask<SelectableChannel> task = (NioTask<SelectableChannel>) a;
                invokeChannelUnregistered(task, k, null);
            }
        }

        for (AbstractNioChannel ch : channels) {
            ch.unsafe().close(ch.unsafe().voidPromise());
        }
    }

    private static void invokeChannelUnregistered(NioTask<SelectableChannel> task, SelectionKey k, Throwable cause) {
        try {
            task.channelUnregistered(k.channel(), cause);
        } catch (Exception e) {
            logger.warn("Unexpected exception while running NioTask.channelUnregistered()", e);
        }
    }

    @Override
    protected void wakeup(boolean inEventLoop) {
        /**
         * inEventLoop：只有非NioEventLoop内部线程才执行（内部只有一个线程，既然都跑到这里了，肯定是没有阻塞的，唤醒个寂寞？）
         * wakenUp设置为true，表明selector已经被唤醒
         */
        if (!inEventLoop && wakenUp.compareAndSet(false, true)) {
            //如果调用wakeup方法时处于selector.select阻塞方法中，则会直接唤醒处于selector.select阻塞中的线程
            //而如果调用wakeup方法时selector不处于selector.select阻塞方法中，则效果是在下一次调selector.select方法时不阻塞
            selector.wakeup();
        }
    }

    Selector unwrappedSelector() {
        return unwrappedSelector;
    }

    int selectNow() throws IOException {
        try {
            return selector.selectNow();
        } finally {
            // restore wakeup state if needed
            if (wakenUp.get()) {
                selector.wakeup();
            }
        }
    }

    private void select(boolean oldWakenUp) throws IOException {
        Selector selector = this.selector;
        try {
            int selectCnt = 0;//记录select轮询次数
            long currentTimeNanos = System.nanoTime();//记录当前时间，这个方法返回的是相对值

            //计算本次轮询的截止时间：如果没有定时任务加1秒，有定时任务，加上定时任务的时间
            long selectDeadLineNanos = currentTimeNanos + delayNanos(currentTimeNanos);

            for (; ;) {
                /**
                 * timeoutMillis为本次轮询的超时时间（加500000L为了方便四舍五入，除以1000000L是为了将纳秒换算成毫秒）
                 * 因为每一次for循环会重置currentTimeNanos，如果timeoutMillis <= 0，说明超时了，要跳出循环
                 */
                long timeoutMillis = (selectDeadLineNanos - currentTimeNanos + 500000L) / 1000000L;
                if (timeoutMillis <= 0) {
                    if (selectCnt == 0) {
                        selector.selectNow();
                        selectCnt = 1;
                    }
                    break;
                }

                /**
                 * 通过判断hasTasks()是否有任务，决定是执行非阻塞的select方法还是后面阻塞的select方法，也就是如果队列中
                 * 有新的任务来了，能不阻塞就不阻塞，提高效率
                 *
                 * 有个疑问：
                 *      因为能进入这个方法，说明hasTasks()=false，然后NioEventLoop内部线程走到这里，如果任务队列别添加了
                 *      任务，此时hasTasks()=true，又因为在添加任务的同时会将wakenUp设置为true，所以第二个判断应该会返回
                 *      false，那if里面的逻辑什么场景下才会走到呢？
                 * 原因：
                 *      因为有新任务来的时候，是先往队列中添加任务，再将wakenUp置为true（selector.wakeup()方法可以认为与
                 *      置为true是同时发生的），即如果添加了task但还没来得及将wakenUp置为true时才会进入这个if中。
                 * 新的疑惑：
                 *      为什么将wakenUp置为true了就不用进if中呢？
                 * 原因：
                 *      是因为如果wakenUp已经是true了，那么可以认为已经执行了selector.wakeup方法了，既然如此，
                 *      selector.select虽然是阻塞方法也就不会再阻塞了，而是直接返回结果，所以没必要再进if中。
                 */
                if (hasTasks() && wakenUp.compareAndSet(false, true)) {
                    selector.selectNow();
                    selectCnt = 1;
                    break;
                }

                //----------------------------------------- 下面是阻塞式的select() ----------------------------

                /**
                 * NIO的介绍：调用Selector对象的wakeup( )方法将使得选择器上的第一个还没有返回的选择操作立即返回。
                 *            如果当前没有在进行中的选择，那么下一次对select( )方法的一种形式的调用将立即返回。
                 *
                 * 上面设置的超时时间没到,而且任务为空,进行阻塞式的 select() , timeoutMillis 默认1000，这里有可能发生空轮询的bug
                 */
                int selectedKeys = selector.select(timeoutMillis);

                // 表示当前已经轮询了SelectCnt次了
                selectCnt++;

                /**
                 * 阻塞完成轮询后,马上进一步判断 只要满足下面的任意一条. 将退出无限for循环
                 *   selectedKeys != 0     表示轮询到了事件
                 *   oldWakenUp            为true，即进select(boolean)方法之前为true，说明队列中有新任务来了，所以也要跳
                 *                         出循环，出去处理；
                 *   wakenUp.get()         wakenUp现在为true，说明在进入select(boolean)方法之后队列中有新任务来了，需跳
                 *                         出循环处理；
                 *   hasTasks()            任务队列中又有新任务了
                 *   hasScheduledTasks()   当时定时任务队列里面也有任务
                 */
                if (selectedKeys != 0 || oldWakenUp || wakenUp.get() || hasTasks() || hasScheduledTasks()) {
                    break;
                }
                //如果当前没有事件过来，队列中又没有任务处理，那么就继续走select(boolean)的无限for循环

                //尚不清楚什么时候会进入下面的if
                if (Thread.interrupted()) {
                    // Thread was interrupted so reset selected keys and break so we not run into a busy loop.
                    // As this is most likely a bug in the handler of the user or it's client library we will
                    // also log it.
                    //
                    // See https://github.com/netty/netty/issues/2426
                    if (logger.isDebugEnabled()) {
                        logger.debug("Selector.select() returned prematurely because " +
                                "Thread.currentThread().interrupt() was called. Use " +
                                "NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop.");
                    }
                    selectCnt = 1;
                    break;
                }
                // 每次执行到这里就说明,已经进行了一次阻塞式操作 ,并且还没有监听到任何感兴趣的事件
                // 也没有新的任务添加到队列, 记录当前的时间
                long time = System.nanoTime();

                /**
                 * 每次循环走到这里，说明执行了select(timeoutMillis)方法并且有timeoutMillis时长的阻塞
                 * 如果当前时间 - timeoutMillis < 开始时间, 说明没有发生阻塞, 就表明是一次空轮询
                 * 如果selectCnt如果大于 512，达到阈值，于是rebuild Selector
                 */
                if (time - TimeUnit.MILLISECONDS.toNanos(timeoutMillis) >= currentTimeNanos) {
                    selectCnt = 1;
                } else if (SELECTOR_AUTO_REBUILD_THRESHOLD > 0 && selectCnt >= SELECTOR_AUTO_REBUILD_THRESHOLD) {
                    //logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding Selector {}.",selectCnt, selector);
                    // 创建一个新的selectKey , 把老的Selector上面的key注册进这个新的selector上面
                    rebuildSelector();
                    selector = this.selector;
                    selector.selectNow();
                    selectCnt = 1;
                    break;
                }

                currentTimeNanos = time;
            }

            if (selectCnt > MIN_PREMATURE_SELECTOR_RETURNS) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Selector.select() returned prematurely {} times in a row for Selector {}.", selectCnt - 1, selector);
                }
            }
        } catch (CancelledKeyException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector {} - JDK bug?", selector, e);
            }
            // Harmless exception - log anyway
        }
    }

    private void selectAgain() {
        needsToSelectAgain = false;
        try {
            selector.selectNow();
        } catch (Throwable t) {
            logger.warn("Failed to update SelectionKeys.", t);
        }
    }
}
