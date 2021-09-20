package com.ynan._015.linkedBlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @program: concurrent_study
 * @author: yn
 * @create: 2021-07-13 00:29
 * @description:
 */
public class LinedBlockingQueueMain
{
    public static void main(String[] args) throws InterruptedException
    {
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();

        /**
         * 入队方法
         */
        linkedBlockingQueue.put("hello");
        linkedBlockingQueue.add("hello");
        boolean b = linkedBlockingQueue.offer("hello");
        boolean b1 = linkedBlockingQueue.offer("hello", 10, TimeUnit.SECONDS);


        Object poll = linkedBlockingQueue.poll();
        Object remove = linkedBlockingQueue.remove();
        Object take = linkedBlockingQueue.take();
    }
}
