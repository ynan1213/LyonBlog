package com.epichust.main8.业务线程;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MyServerHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println(Thread.currentThread().getName() + " =========== business =========");

        ctx.channel().writeAndFlush(Unpooled.wrappedBuffer("xxxxx".getBytes()));

        super.channelRead(ctx, msg);
    }
}
