package com.epichust.main1.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

public class ByteBufferDemo
{
    public static void main(String[] args)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(1024);

        ByteBuf byteBuf = Unpooled.directBuffer(1024);
    }
}
