package com.epichust.main8.业务线程;

import com.epichust.main2.netty.MyLoggingHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.ThreadFactory;

public class NettyServer
{
    public static void main(String[] args)
    {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(2);

        EventExecutorGroup businessGroup = new DefaultEventExecutorGroup(2, new ThreadFactory()
        {
            @Override
            public Thread newThread(Runnable r)
            {
                return new Thread(r, "业务线程");
            }
        });//业务线程池
        try
        {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new MyLoggingHandler("服务端-父", LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception
                        {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MyBeforeServerHandler());
                            pipeline.addLast(businessGroup, new MyServerHandler());
                            pipeline.addLast(new MyAfterServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(1234).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
