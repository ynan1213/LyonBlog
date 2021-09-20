package com.ynan._016.arrayBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @program: concurrent_study
 * @author: yn
 * @create: 2021-07-13 00:51
 * @description:
 */
public class ArrayBlockingQueueMain
{
    public static void main(String[] args) throws InterruptedException
    {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(16);

        arrayBlockingQueue.put("hello");

    }
}
