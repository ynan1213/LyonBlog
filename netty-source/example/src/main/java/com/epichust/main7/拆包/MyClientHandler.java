package com.epichust.main7.拆包;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        for (int i = 0; i <= 10; i++)
        {
            byte[] content = "醉卧沙场君莫笑".getBytes();
            int len = content.length;

            MessageProtocol message = new MessageProtocol();
            message.setLen(len);
            message.setContent(content);
            ctx.writeAndFlush(message);
        }
    }
}
