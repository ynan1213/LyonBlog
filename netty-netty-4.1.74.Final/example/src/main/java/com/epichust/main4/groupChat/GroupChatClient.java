package com.epichust.main4.groupChat;

import com.sun.org.apache.bcel.internal.generic.MethodGen;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient
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
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
                                {
                                    System.out.println((String)msg);
                                    super.channelRead(ctx, msg);
                                }
                            });
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 1234).sync();
            Scanner in = new Scanner(System.in);
            while (in.hasNextLine())
            {
                String msg = in.nextLine();
                channelFuture.channel().writeAndFlush(msg);
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
