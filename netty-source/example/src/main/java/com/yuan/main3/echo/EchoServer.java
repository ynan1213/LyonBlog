package com.epichust.main3.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.example.echo.EchoServerHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer
{
    public static void main(String[] args) throws Exception
    {
        /**
         * EventLoopGroup的创建总结：
         * 一.创建Executor(ThreadPerTaskExecutor类型)线程池，该线程池内部最多只会创建一个线程，且该Executor最终会传递到
         *    NioEventLoopGroup内部的每一个NioEventLoop中。
         * 二.初始化内部的EventExecutor[]数组，真实类型是NioEventLoop[]类型。
         * 三.NioEventLoop的初始化总结：
         *      1) 创建两个任务队列，
         *      2）接收Executor、RejectedExecutionHandler、SelectStrategy
         *      3）接收SelectorProvider(每个NioEventLoop会用它创建自己的Selector)
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try
        {
            /**
             * ServerBootstrap起到了收集器的作用，group、channel、option、handler、childHandler等方法
             * 仅仅是将方法参数的对象保存起来，真正做处理的是在后面的bind方法。
             */
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //.handler(new MyServerHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        protected void initChannel(SocketChannel socketChannel) throws Exception
                        {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(new EchoServerHandler());
                            pipeline.addLast(new MyServerHandler());
                        }
                    });

            /**
             * bind方法总结：
             * 一、工厂模式ChannelFactory<NioServerSocketChannel>创建NioServerSocketChannel，步骤总结：
             *      1) 通过jdk原生方法provider.openServerSocketChannel()和provider.openSocketChannel()获取对应的channel;
             *      2) 服务端设置OP_ACCEPT事件，客户端设置OP_READ事件;
             *      3) 设置通道为非阻塞，ch.configureBlocking(false);
             *      4) 创建unsafe和pipeline对象；
             *      5) 创建ServerSocketChannelConfig对象;
             * 二、init(Channel channel)初始化上面创建的NioServerSocketChannel，步骤总结：
             *      1) 设置ChannelOption或者AttributeKey参数;
             *      2) 给内部的ChannelPipeline管道addLast一个ChannelInitializer，用于初始化上面传入的父handler和ServerBootstrapAcceptor
             * 三、注册Channel，步骤总结：
             *      1) 交给bossGroup注册；
             *      2) bossGroup使用EventExecutorChooser选择器选择内部的一个NioEventLoop，交由它注册;
             *      3) 来到NioEventLoop中，创建异步结果ChannelFuture，又转交给Channel内部的Unsafe对象,同时传递的还有NioEventLoop对象自己
             *      4) 来到NioServerSocketChannel的内部类对象Unsafe对象中，在这里会把NioEventLoop传递给外部对象NioServerSocketChannel
             *         至此，NioServerSocketChannel已经和一个NioEventLoop进行绑定了，注意NioEventLoop是不会存储任务channel的信息的，所
             *         以NioEventLoop可以被和多个NioServerSocketChannel关联;
             *      5) 仍在Unsafe对象中，接下来创建并启动NioEventLoop中的线程，并封装register0任务交由该线程处理;
             * 四、register0的具体步骤总结：
             *      1) 在Unsafe对象中，调用jdk方法将channel注册进NioEventLoop中持有的Selector中，返回的SelectionKey对象赋给NioServerSocketChannel对象；
             *      2)
             *
             */
            ChannelFuture future = bootstrap.bind(6666);

            /**
             * sync方法会让main线程阻塞等待bind任务完成
             *
             * 阻塞的本质是两个线程拥有同一个对象DefaultPromise
             * main线程获取到DefaultPromise的锁后，会将DefaultPromise的waiters变量+1，
             * 另一个线程在异步完成后会更新DefaultPromise的状态，根据waiters进行调用notifyAll
             *
             */
            //System.out.println("main线程进入wait等待..................");
            future = future.sync();
            //System.out.println("main线程被唤醒..................");

            /**
             * 下面这句代码的作用：让线程进入wait状态，也就是main线程暂时不会执行到finally里面，
             *  nettyserver也持续运行，如果监听到关闭事件，可以优雅的关闭通道和nettyserver
             *
             *  如果缺失下面这句代码，main线程即主线程会在执行完bind().sync()方法后，
             *  会进入finally 代码块，之前的启动的nettyserver也会随之关闭掉，整个程序都结束了
             *
             *  等同于 new DefaultChannelPromise(null).sync(),为的就是让主线程进入wait阻塞
             *  或者直接一点  Thread.currentThread().wait();后来研究了下，直接这样使用有问题
             */
            future.channel().closeFuture().sync();

        } finally
        {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
