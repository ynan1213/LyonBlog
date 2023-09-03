package com.epichust.main2.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author yuannan
 */
public class MyCustomeHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        byte[] by = new byte[1000];
        by[0] = 1;
        by[1] = 1;
        by[2] = 1;
        by[998] = 2;
        by[999] = 2;

        ByteBuf buffer = ctx.alloc().buffer(2048);
        ByteBuf buffer1 = ctx.alloc().buffer(2048);
        buffer.writeBytes(by);
        buffer1.writeBytes(by);
        ctx.channel().write(buffer);
        ctx.channel().write(buffer1);
        ctx.channel().flush();
    }
}
