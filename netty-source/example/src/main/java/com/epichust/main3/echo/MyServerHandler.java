package com.epichust.main3.echo;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

public class MyServerHandler extends ChannelDuplexHandler
{
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("MyHandler 被添加到pipeline");
        super.handlerAdded(ctx);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception
    {
        System.out.println("成功连接到............地址：" + remoteAddress.toString());
        super.connect(ctx, remoteAddress, localAddress, promise);
    }
}
