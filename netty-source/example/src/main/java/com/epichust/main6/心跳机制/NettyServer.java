package com.epichust.main6.心跳机制;

import com.epichust.main2.netty.MyLoggingHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyServer
{
    public static void main(String[] args)
    {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(2);

        try
        {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new MyLoggingHandler("服务端", LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception
                        {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
                            pipeline.addLast(new HeartBeatHandler());
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
