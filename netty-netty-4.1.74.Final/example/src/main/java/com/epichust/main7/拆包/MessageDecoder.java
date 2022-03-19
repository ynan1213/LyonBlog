package com.epichust.main7.拆包;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder
{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);

        MessageProtocol message = new MessageProtocol();
        message.setLen(len);
        message.setContent(content);
        out.add(message);
    }
}
