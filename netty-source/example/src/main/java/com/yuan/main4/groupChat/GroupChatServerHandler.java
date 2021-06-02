package com.epichust.main4.groupChat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String>
{
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        channelGroup.writeAndFlush("【客户端" + ctx.channel().remoteAddress() + "】加入聊天");
        System.out.println("【客户端" + ctx.channel().remoteAddress() + "】加入聊天");
        channelGroup.add(ctx.channel());
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        channelGroup.writeAndFlush("【客户端" + ctx.channel().remoteAddress() + "】下线了");
        System.out.println("【客户端" + ctx.channel().remoteAddress() + "】下线了");
        //channelGroup.remove(ctx.channel());//这句可不加，会自动移除，具体实现原理有待研究
        super.handlerRemoved(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
    {
        channelGroup.forEach(ch -> {
            if (ch == ctx.channel())
            {
                ch.writeAndFlush("【客户端" + ctx.channel().remoteAddress() + "】说：" + msg);
            } else
            {
                ch.writeAndFlush("【自  己】说：" + msg + "\n");
            }
        });
    }
}
