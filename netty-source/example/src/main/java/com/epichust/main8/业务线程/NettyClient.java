package com.epichust.main8.业务线程;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class NettyClient
{
    public static void main(String[] args)
    {
        EventLoopGroup workGroup = new NioEventLoopGroup(1);

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

                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 1234).sync();
            //channelFuture.channel().write(Unpooled.wrappedBuffer("你好，中国".getBytes()));

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine())
            {
                String s = scanner.nextLine();
                ByteBuf buffer = channelFuture.channel().alloc().buffer(s.length());
                buffer.writeBytes(s.getBytes());
                channelFuture.channel().writeAndFlush(buffer);
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
