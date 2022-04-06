package com.epichust.main5.byteBuf;

import java.nio.ByteBuffer;
import sun.misc.VM;

/**
 * @Author yuannan
 * @Date 2022/4/3 17:19
 */
public class ByteBufferDemo {

    public static void main(String[] args) {
//        ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
//        ByteBuffer direct = ByteBuffer.allocateDirect(1024);
        System.out.println(VM.maxDirectMemory());
    }

}
