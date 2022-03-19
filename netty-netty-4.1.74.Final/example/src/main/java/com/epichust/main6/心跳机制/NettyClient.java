package com.epichust.main6.心跳机制;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
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
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try
        {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer()
            {
                @Override
                protected void initChannel(Channel ch) throws Exception
                {

                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 1234).sync();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext())
            {
                channelFuture.channel().writeAndFlush(scanner.nextLine());
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
