package com.epichust.main2.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Scanner;

public class NettyClient
{
    public static void main(String[] args)
    {
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try
        {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception
                        {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MyLoggingHandler("客户端", LogLevel.INFO));
                            pipeline.addLast(new StringEncoder());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
            channelFuture.channel().writeAndFlush("你好，中国 ===============").addListener(new ChannelFutureListener()
            {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception
                {
                    if (future.isSuccess())
                    {
                        System.out.println("写出成功");
                    } else
                    {
                        System.out.println("写出失败");
                    }
                }
            });

            channelFuture.channel().isWritable();

            // await() 和 sync() 功能是一样的，不过如果任务失败，await()它不会抛出执行过程中的异常
            //channelFuture.await();
            //channelFuture.sync();

            //不会被打断
            //channelFuture.syncUninterruptibly();
            //channelFuture.awaitUninterruptibly();

            //channelFuture.await(1000);

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine())
            {
                String s = scanner.nextLine();
                ByteBuf buffer = channelFuture.channel().alloc().buffer(s.length());
                buffer.writeBytes(s.getBytes());
                ChannelFuture future = channelFuture.channel().writeAndFlush(buffer).addListener(new GenericFutureListener<Future<? super Void>>()
                {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception
                    {
                        System.out.println("写已完成");
                    }
                });

                channelFuture.sync();

            }

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            workGroup.shutdownGracefully();
        }
    }
}
