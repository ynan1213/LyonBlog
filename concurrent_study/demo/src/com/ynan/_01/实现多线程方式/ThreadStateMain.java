package com.ynan._01.实现多线程方式;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: concurrent_study
 * @description: demo
 * @author: yn
 * @create: 2021-06-18 09:34
 */
public class ThreadStateMain
{
    public static void main(String[] args) throws InterruptedException
    {
        ExecutorService executorService = Executors.newFixedThreadPool(10, new ThreadFactory()
        {
            AtomicInteger integer = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r)
            {
                return new Thread(r, "线程池 -- " + integer.getAndIncrement());
            }
        });

        executorService.execute(() -> {
            try
            {
                Thread.sleep(1000000l);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            while (true)
            {

            }
        });
        executorService.execute(() -> {
            System.out.println("..............");
        });

        System.out.println("main sleep");
        Thread.sleep(100000000000l);
    }
}
