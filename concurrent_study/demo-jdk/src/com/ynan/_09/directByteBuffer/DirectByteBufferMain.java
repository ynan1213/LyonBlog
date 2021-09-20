package com.ynan._09.directByteBuffer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: concurrent_study
 * @author: yn
 * @create: 2021-07-21 17:19
 * @description:
 */
public class DirectByteBufferMain
{
    public static void main(String[] args)
    {
        List<ByteBuffer> list = new ArrayList<ByteBuffer>();
        while (true)
        {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1 * 1024 * 1024);
            list.add(buffer);
        }
    }
}
