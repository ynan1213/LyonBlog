package com.ynan._01.createMultiThreadMethod;

import java.util.concurrent.*;

public class Main1
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        //下面这种方法可以吗？
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        FutureTask<Integer> task = new FutureTask(() -> {
            System.out.println(Thread.currentThread().getName() + " ======== 基于Runnbale接口");
            TimeUnit.SECONDS.sleep(5);
            return 2;
        });

        Future<?> future = executorService.submit(task);
        // Future<?> future = executorService.submit(() -> {
        //     System.out.println(Thread.currentThread().getName() + " ======== 基于Runnbale接口");
        //     TimeUnit.SECONDS.sleep(5);
        //     return 2;
        // });

        //① 通过返回值 future 获取结果
        System.out.println(System.currentTimeMillis() + " ===== " + future.get());

        //② 通过传入的任务 FutureTask 获取结果
        System.out.println(System.currentTimeMillis() + " ===== " + task.get());
    }
}
