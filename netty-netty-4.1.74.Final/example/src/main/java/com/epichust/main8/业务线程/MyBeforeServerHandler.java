package com.epichust.main8.业务线程;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MyBeforeServerHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println(Thread.currentThread().getName() + " ========== brfore ==========");
        super.channelRead(ctx, msg);
    }
}
