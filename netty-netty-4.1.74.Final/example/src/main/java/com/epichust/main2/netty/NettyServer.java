package com.epichust.main2.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.util.AttributeKey;

public class NettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        bossGroup.setIoRatio(20);

        // 无参构造内部线程池大小是 cpu核心数 * 2
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5 * 1000)
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5 * 1000)
                //.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))
                .attr(AttributeKey.newInstance("xxx"), "xxx")
                .childAttr(AttributeKey.newInstance("yyy"), "yyy")
                .handler(new MyLoggingHandler("服务端-父", LogLevel.INFO))
                // childHandler 非空
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
            // 为什么需要下面这句而不是直接跳到下下句呢？ 个人觉得是bindFuturer如果没有bind成功，可能连channel都没初始化
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
