package com.ynan._04.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        /**
         * supplyAsync有返回值，类似ExecutorService.submit(Callable)
         */
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            try
            {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
            return 1.2;
        });

        System.out.println("run result->" + cf.get());

    }
}
