package com.ynan._01.实现多线程方式;

import java.util.concurrent.*;

public class Main3
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // ---------------------------------------------------------
        // 结论：入参为Runnable时，返回值Future.get()的结果为null
        Runnable runnable = () -> {
            System.out.println("runnable执行完成");
        };

        Future<?> submit = executorService.submit(runnable);
        System.out.println(submit.get());

        // ---------------------------------------------------------
        Future<?> submit1 = executorService.submit(runnable, "hello world");
        System.out.println(submit1.get());

        // ---------------------------------------------------------
        // 入参为Callable时，返回值Future.get()的结果为call()的返回值
        Callable callable = () -> {
            return "xxxxxxx";
        };
        Future submit2 = executorService.submit(callable);
        Object o = submit2.get();
        System.out.println(o);
    }
}
