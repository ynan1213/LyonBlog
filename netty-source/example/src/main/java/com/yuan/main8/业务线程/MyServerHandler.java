package com.epichust.main8.业务线程;

import com.epichust.main7.拆包.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println(Thread.currentThread().getName() + " ====================");
        super.channelRead(ctx, msg);
    }
}
