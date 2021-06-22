package com.ynan._04.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Main1
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        /**
         * runAsync没有有返回值，类似ExecutorService.submit(Runnable)
         */
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            try
            {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
        });

        System.out.println("run result->" + cf.get());

    }
}
