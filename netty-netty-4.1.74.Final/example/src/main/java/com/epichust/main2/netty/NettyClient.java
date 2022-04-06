package com.epichust.main2.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.Scanner;

public class NettyClient {

    public static void main(String[] args) {
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MyLoggingHandler("客户端", LogLevel.INFO));
//                        pipeline.addLast(new StringEncoder());
                    }
                });
            ChannelFuture connectFuture = bootstrap.connect("127.0.0.1", 6666);
            System.out.println("connectFuture sync 之前");
            /**
             * 在sync过程中，main线程会阻塞住，如果再connect过程中发生了异常：Connection refused: /127.0.0.1:6666
             * 会唤醒main线程，然后main线程发现异常不为空，就会往外抛
             */
            connectFuture.sync();
            System.out.println("connectFuture sync 之后");
            //            connectFuture.channel().writeAndFlush("你好，中国 ===============").addListener(new ChannelFutureListener() {
            //                @Override
            //                public void operationComplete(ChannelFuture future) throws Exception {
            //                    if (future.isSuccess()) {
            //                        System.out.println("写出成功");
            //                    } else {
            //                        System.out.println("写出失败");
            //                    }
            //                }
            //            });

            //            connectFuture.channel().isWritable();

            // await() 和 sync() 功能是一样的，不过如果任务失败，await()它不会抛出执行过程中的异常
            //channelFuture.await();
            //channelFuture.sync();

            //不会被打断
            //channelFuture.syncUninterruptibly();
            //channelFuture.awaitUninterruptibly();

            //channelFuture.await(1000);

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                ByteBuf buffer = connectFuture.channel().alloc().buffer(5);
                buffer.writeBytes(s.getBytes());
                connectFuture.channel().write(buffer);
                connectFuture.channel().write(buffer);
                connectFuture.channel().flush();
//                ChannelFuture future = connectFuture.channel().writeAndFlush(buffer)
//                    .addListener(new GenericFutureListener<Future<? super Void>>() {
//                        @Override
//                        public void operationComplete(Future<? super Void> future) throws Exception {
//                            System.out.println("写已完成");
//                        }
//                    });
            }

            connectFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
