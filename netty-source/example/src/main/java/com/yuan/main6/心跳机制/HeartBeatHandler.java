package com.epichust.main6.心跳机制;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatHandler extends SimpleChannelInboundHandler<String>
{

    int readIdleTimes = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception
    {
        System.out.println(" ====== > [server] message received : " + s);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
    {
        if (evt instanceof IdleStateEvent)
        {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state())
            {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    readIdleTimes++; //读写空闲的计数加1
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "超时事件：" + eventType);
            if (readIdleTimes > 3)
            {
                System.out.println(" [server]读空闲超过3次，关闭连接");
                ctx.channel().writeAndFlush("you are out");
                ctx.channel().close();
            }
        }
    }
}
