package com.ynan._09.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class SemaphoreMain
{
    public static void main(String[] args)
    {
        Semaphore semaphore = new Semaphore(3);

        // ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++)
        {
            executorService.execute(() -> {
                try
                {
                    semaphore.acquire();
                    //semaphore.acquire(2);
                    semaphore.acquireUninterruptibly();

                    System.out.println(Thread.currentThread().getName() + "  获取到许可");
                    sleep(5000);

                    semaphore.release();
                    semaphore.release(3);

                    System.out.println(Thread.currentThread().getName() + "  释放了许可");
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        }
    }
}
