package com.epichust.main2.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;

/**
 * 一个 bossGroup 线程池 绑定两个端口
 */
public class NettyServer1
{
    public static void main(String[] args)
    {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try
        {
            ServerBootstrap bootstrap1 = new ServerBootstrap();
            bootstrap1.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new MyLoggingHandler("服务端-父", LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception
                        {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MyLoggingHandler("服务端-子", LogLevel.INFO));
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new MyHttpServerHandler());
                        }
                    });
            ServerBootstrap bootstrap2 = new ServerBootstrap();
            bootstrap2.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new MyLoggingHandler("服务端-父", LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception
                        {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MyLoggingHandler("服务端-子", LogLevel.INFO));
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new MyHttpServerHandler());
                        }
                    });
            ChannelFuture future1 = bootstrap1.bind(6666).sync();
            ChannelFuture future2 = bootstrap1.bind(6667).sync();

            future1.channel().closeFuture().sync();
            future2.channel().closeFuture().sync();

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
