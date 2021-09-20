package com.ynan._01.实现多线程方式;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main4
{
    public static void main(String[] args)
    {
        // ExecutorService executorService = Executors.newFixedThreadPool(4);
        // for (int i = 0; i <= 100; i++)
        // {
        //     int finalI = i;
        //     executorService.execute(() -> {
        //         if (finalI < 4)
        //         {
        //             Thread.currentThread().setName("xxxx - " + (finalI + 1));
        //         }
        //         System.out.println(Thread.currentThread().getName());
        //     });
        // }

        System.out.println(Runtime.getRuntime().availableProcessors());

    }
}
