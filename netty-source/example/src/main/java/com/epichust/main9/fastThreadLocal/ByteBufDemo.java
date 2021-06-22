package com.epichust.main9.fastThreadLocal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class ByteBufDemo
{
    public static void main(String[] args)
    {
        PooledByteBufAllocator a = new PooledByteBufAllocator(false);
        ByteBuf buf = a.heapBuffer(252);
        System.out.println(buf);
    }
}
