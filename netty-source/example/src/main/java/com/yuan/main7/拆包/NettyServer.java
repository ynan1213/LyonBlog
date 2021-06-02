package com.epichust.main7.拆包;

import com.epichust.main2.netty.MyLoggingHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;

public class NettyServer
{
    public static void main(String[] args)
    {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

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
                            pipeline.addLast(new MessageDecoder());
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            bootstrap.bind(3333).sync().channel().closeFuture().sync();
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
