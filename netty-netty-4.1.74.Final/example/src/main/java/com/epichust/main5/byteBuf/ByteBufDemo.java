package com.epichust.main5.byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * @Author yuannan
 * @Date 2022/4/3 17:12
 */
public class ByteBufDemo {

    public static void main(String[] args) {

//        ByteBuf byteBuf = Unpooled.directBuffer();
//        ByteBuf directBuffer = UnpooledByteBufAllocator.DEFAULT.directBuffer();
        ByteBuf directBuffer = UnpooledByteBufAllocator.DEFAULT.directBuffer(1000);
        System.out.println(directBuffer.refCnt());
        directBuffer.retain();
        System.out.println(directBuffer.refCnt());
    }
}