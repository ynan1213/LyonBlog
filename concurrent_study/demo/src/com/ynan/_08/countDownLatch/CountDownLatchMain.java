package com.ynan._08.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchMain
{
    public static void main(String[] args) throws InterruptedException
    {
        // 构造一个资源
        CountDownLatch latch = new CountDownLatch(1000);

        Demo demo = new Demo();

        for (int i = 0; i < 1000; i++)
        {
            new Thread(() -> {
               // i++;
            }).start();
        }
        latch.await();

    }
}

class Demo
{
    private int i;

    public void increment()
    {
        new Thread(() -> {
            i++;
        }).start();
    }
}