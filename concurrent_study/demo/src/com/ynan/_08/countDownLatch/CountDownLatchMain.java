package com.ynan._08.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchMain
{
    public static void main(String[] args) throws InterruptedException
    {
        // 构造一个资源
        CountDownLatch latch = new CountDownLatch(2);

        // 释放一个资源
        latch.countDown();

        latch.await();
    }
}
