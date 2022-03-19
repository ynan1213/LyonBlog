package com.epichust.main2.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.util.AttributeKey;

public class NettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 无参构造内部线程池大小是 cpu核心数 * 2
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5 * 1000)
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5 * 1000)
                .attr(AttributeKey.newInstance("xxx"), "xxx")
                .childAttr(AttributeKey.newInstance("yyy"), "yyy")
                .handler(new MyLoggingHandler("服务端-父", LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MyLoggingHandler("服务端-子", LogLevel.INFO));
                        // pipeline.addLast(new HttpServerCodec());
                        // pipeline.addLast(new MyHttpServerHandler());
                    }
                });
            ChannelFuture bindFuture = bootstrap.bind(6666);
            ChannelFuture syncFuture = bindFuture.sync();
            syncFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
