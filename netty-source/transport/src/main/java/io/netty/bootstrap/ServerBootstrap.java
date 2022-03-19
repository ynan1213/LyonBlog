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
package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * {@link Bootstrap} sub-class which allows easy bootstrap of {@link ServerChannel}
 *
 *  注意父类的泛型，有点循环的意思
 *  public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel>
 *  B = ServerBootstrap
 *  C = ServerChannel
 */
public class ServerBootstrap extends AbstractBootstrap<ServerBootstrap, ServerChannel> {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);

    // 关于协议的配置项信息的封装
    private final Map<ChannelOption<?>, Object> childOptions = new LinkedHashMap<ChannelOption<?>, Object>();

    // 运行时用户存储进去的数据的封装容器
    private final Map<AttributeKey<?>, Object> childAttrs = new LinkedHashMap<AttributeKey<?>, Object>();

    private final ServerBootstrapConfig  config = new ServerBootstrapConfig(this);

    // volatile确保编译时不会出现指令的重排序
    // 明确 happen 和 before 的关系
    private volatile EventLoopGroup childGroup;

    // 服务于 childGroup, 处理这个循环组中请求
    private volatile ChannelHandler childHandler;

    public ServerBootstrap() {
    }

    private ServerBootstrap(ServerBootstrap bootstrap) {
        super(bootstrap);
        // 从NioServerBootstrapt角度来看,  实际上并没有执行这个构造函数,去初始化下面的childGroup  -- 处理IO事件的事件循环组
        // 而是在下面的大约100行的地方初始化的, 下面去看100行
        childGroup = bootstrap.childGroup;
        childHandler = bootstrap.childHandler;
        synchronized (bootstrap.childOptions) {
            childOptions.putAll(bootstrap.childOptions);
        }
        synchronized (bootstrap.childAttrs) {
            childAttrs.putAll(bootstrap.childAttrs);
        }
    }

    /**
     * Specify the {@link EventLoopGroup} which is used for the parent (acceptor) and the child (client).
     */
    @Override
    public ServerBootstrap group(EventLoopGroup group) {
        return group(group, group);
    }


    /**
     * Set the {@link EventLoopGroup} for the parent (acceptor) and the child (client). These
     * {@link EventLoopGroup}'s are used to handle all the events and IO for {@link ServerChannel} and
     * {@link Channel}'s.
     */
    public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
        // 把parentGroup传递给父类AbstractBootstrap
        super.group(parentGroup);

        if (childGroup == null) {
            throw new NullPointerException("childGroup");
        }
        if (this.childGroup != null) {
            throw new IllegalStateException("childGroup set already");
        }
        // childGroup给自己维护
        // 在注册 Channel的步骤中,获取childGroup(),Channel要注册进事件循环组!!! 它发生在他的父类中!!!(AbstractBootStrap)
        this.childGroup = childGroup;

        // 链式编程风格
        return this;
    }

    /**
     * Allow to specify a {@link ChannelOption} which is used for the {@link Channel} instances once they get created
     * (after the acceptor accepted the {@link Channel}). Use a value of {@code null} to remove a previous set
     * {@link ChannelOption}.
     */
    public <T> ServerBootstrap childOption(ChannelOption<T> childOption, T value) {
        if (childOption == null) {
            throw new NullPointerException("childOption");
        }
        if (value == null) {
            synchronized (childOptions) {
                childOptions.remove(childOption);
            }
        } else {
            synchronized (childOptions) {
                childOptions.put(childOption, value);
            }
        }
        return this;
    }

    /**
     * Set the specific {@link AttributeKey} with the given value on every child {@link Channel}. If the value is
     * {@code null} the {@link AttributeKey} is removed
     */
    public <T> ServerBootstrap childAttr(AttributeKey<T> childKey, T value) {
        if (childKey == null) {
            throw new NullPointerException("childKey");
        }
        if (value == null) {
            childAttrs.remove(childKey);
        } else {
            childAttrs.put(childKey, value);
        }
        return this;
    }

    /**
     * Set the {@link ChannelHandler} which is used to serve the request for the {@link Channel}'s.
     */
    // 初始化添加的这个 ChannelHandler , 用服务于所有的Channel的请求
    public ServerBootstrap childHandler(ChannelHandler childHandler) {
        if (childHandler == null) {
            throw new NullPointerException("childHandler");
        }
        this.childHandler = childHandler;
        return this;
    }

    @Override
    void init(Channel channel) throws Exception {
        // 这些常量值是关于TCP协议相关的信息
        final Map<ChannelOption<?>, Object> options = options0();
        synchronized (options) {
            setChannelOptions(channel, options, logger);
        }

        // 这个map中维护的是程序运行时的动态的业务数据,可以实现让业务数据随着netty的运行原来存进去的数据还能取出来
        final Map<AttributeKey<?>, Object> attrs = attrs0();
        synchronized (attrs) {
            for (Entry<AttributeKey<?>, Object> e : attrs.entrySet()) {
                @SuppressWarnings("unchecked")
                AttributeKey<Object> key = (AttributeKey<Object>) e.getKey();
                channel.attr(key).set(e.getValue());
            }
        }

        /**
         * ChannelPipeline 是一个重要的组件, 类似JavaEE中的过滤器链
         * 下面直接就调用了p ,说明在channel调用pipeline方法之前, pipeline已经被创建出来了!
         * 到底是什么时候创建出来的 ?  其实是在创建 NioServerSocketChannel 这个通道对象时,在他的顶级抽象父类(AbstractChannel)中创建了一个默认的pipeline对象
         */
        ChannelPipeline p = channel.pipeline();

        // workerGroup 处理IO线程
        final EventLoopGroup currentChildGroup = childGroup;
        // 自定义的handler被封装为 ChannelInitializer类型
        final ChannelHandler currentChildHandler = childHandler;

        final Entry<ChannelOption<?>, Object>[] currentChildOptions;
        final Entry<AttributeKey<?>, Object>[] currentChildAttrs;

        // 这里是我们在Server类中添加的一些针对新连接channel的属性设置, 这两者属性被acceptor使用到!!!
        synchronized (childOptions) {
            currentChildOptions = childOptions.entrySet().toArray(newOptionArray(childOptions.size()));
        }
        synchronized (childAttrs) {
            currentChildAttrs = childAttrs.entrySet().toArray(newAttrArray(childAttrs.size()));
        }

        /**
         *  往 NioServerSocketChannel 的 ChannelPipeline 里面添加了一个 ChannelInitializer
         *  ChannelInitializer 是个辅助类，主要作用就是将 handler 添加到 Pipeline中
         *  疑问：这里为什么不直接添加呢？而是封装为 ChannelInitializer对象 ？
         *
         *  此时addLast进pipeline的handler并不会被初始化，而是会添加到pendingHandlerCallbackHead链表中，后序再初始化
         */
        p.addLast(new ChannelInitializer<Channel>() {
            @Override
            public void initChannel(final Channel ch) throws Exception {
                final ChannelPipeline pipeline = ch.pipeline();

                // config 是 ServerBootstrap 的成员变量，这里的handler方法返回的是 ServerBootstrap 的handler，注意不是childHandler
                ChannelHandler handler = config.handler();
                // 做了非空判断，也就是说 ServerBootstrap 的 handler可以不设置
                if (handler != null) {
                    pipeline.addLast(handler);
                }

                // 疑问：这里同样为什么不直接addLast，而是添加到任务队列中
                ch.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * ServerBootstrapAcceptor接收器, 是一个特殊的 channelHandler
                         * 专门处理新连接的接入, 把新连接的channel绑定在 workerGroup中的某一条线程上
                         */
                        pipeline.addLast(new ServerBootstrapAcceptor(
                                // 这些参数是用户自定义的参数
                                // NioServerSocketChannel, worker线程组  处理器   关系的事件
                                ch, currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs));
                    }
                });
            }
        });
    }

    @Override
    public ServerBootstrap validate() {
        super.validate();
        if (childHandler == null) {
            throw new IllegalStateException("childHandler not set");
        }
        if (childGroup == null) {
            logger.warn("childGroup is not set. Using parentGroup instead.");
            childGroup = config.group();
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    private static Entry<AttributeKey<?>, Object>[] newAttrArray(int size) {
        return new Entry[size];
    }

    @SuppressWarnings("unchecked")
    private static Map.Entry<ChannelOption<?>, Object>[] newOptionArray(int size) {
        return new Map.Entry[size];
    }


    // ServerBootstrapAcceptor 是内部类 继承了ChannelInboundHandlerAdapter
    // ServerBootstrapAcceptor本质上它 也是个handler, 作用是把当前的channel 传递给 workergroup

    // 适配器模式
    // 可以看到这个Acceptor是个 入站处理器适配器, 下面的重写了
    private static class ServerBootstrapAcceptor extends ChannelInboundHandlerAdapter {

        private final EventLoopGroup childGroup;
        private final ChannelHandler childHandler;
        private final Entry<ChannelOption<?>, Object>[] childOptions;
        private final Entry<AttributeKey<?>, Object>[] childAttrs;
        private final Runnable enableAutoReadTask;

        ServerBootstrapAcceptor(
                final Channel channel, EventLoopGroup childGroup, ChannelHandler childHandler,
                Entry<ChannelOption<?>, Object>[] childOptions, Entry<AttributeKey<?>, Object>[] childAttrs) {
            this.childGroup = childGroup;
            this.childHandler = childHandler;
            this.childOptions = childOptions;
            this.childAttrs = childAttrs;

            // Task which is scheduled to re-enable auto-read.
            // It's important to create this Runnable before we try to submit it as otherwise the URLClassLoader may
            // not be able to load the class because of the file limit it already reached.
            //
            // See https://github.com/netty/netty/issues/1328
            enableAutoReadTask = new Runnable() {
                @Override
                public void run() {
                    channel.config().setAutoRead(true);
                }
            };
        }
        // 方法的是如何触发的?  当新链接到来时,Selector会把连接交给服务端的NioMessageUnsafe进一步的IO操作, read()后触发pipeline.fireChannelRead() , 事件从head传递到这里
        // 通过这个channelRead 方法将当前连接的通道扔给了 childGorup;
        @Override
        @SuppressWarnings("unchecked")
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            final Channel child = (Channel) msg;
            // 给这个来连接的通道添加 childHandler,是我在Server中添加的childHandler, 实际上是那个MyChannelInitializer , 最终目的是添加handler
            child.pipeline().addLast(childHandler);
            // 给新来的Channel设置 options 选项
            setChannelOptions(child, childOptions, logger);
            // 给新来的Channel设置 attr属性
            for (Entry<AttributeKey<?>, Object> e : childAttrs) {
                child.attr((AttributeKey<Object>) e.getKey()).set(e.getValue());
            }

            try {
                // 这里把新的channel注册进 childGroup
                childGroup.register(child).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            System.out.println("ChannelFutureListener回调成功...");
                            forceClose(child, future.cause());
                        }
                    }
                });
            } catch (Throwable t) {
                forceClose(child, t);
            }
        }

        private static void forceClose(Channel child, Throwable t) {
            child.unsafe().closeForcibly();
            logger.warn("Failed to register an accepted channel: {}", child, t);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            final ChannelConfig config = ctx.channel().config();
            if (config.isAutoRead()) {
                // stop accept new connections for 1 second to allow the channel to recover
                // See https://github.com/netty/netty/issues/1328
                config.setAutoRead(false);
                ctx.channel().eventLoop().schedule(enableAutoReadTask, 1, TimeUnit.SECONDS);
            }
            // still let the exceptionCaught event flow through the pipeline to give the user
            // a chance to do something with it
            ctx.fireExceptionCaught(cause);
        }
    }

    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public ServerBootstrap clone() {
        return new ServerBootstrap(this);
    }

    /**
     * Return the configured {@link EventLoopGroup} which will be used for the child channels or {@code null}
     * if non is configured yet.
     *
     * @deprecated Use {@link #config()} instead.
     */
    @Deprecated
    public EventLoopGroup childGroup() {
        return childGroup;
    }

    final ChannelHandler childHandler() {
        return childHandler;
    }

    final Map<ChannelOption<?>, Object> childOptions() {
        return copiedMap(childOptions);
    }

    final Map<AttributeKey<?>, Object> childAttrs() {
        return copiedMap(childAttrs);
    }

    @Override
    public final ServerBootstrapConfig config() {
        return config;
    }
}
