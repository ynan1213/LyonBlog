package com.epichust.main7.拆包;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<MessageProtocol>
{
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception
    {
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
