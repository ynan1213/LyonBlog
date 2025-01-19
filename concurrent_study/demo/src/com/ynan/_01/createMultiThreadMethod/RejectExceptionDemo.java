package com.ynan._01.createMultiThreadMethod;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 当队列数小于一次性要提交的任务时，大概率触发拒绝
 */
public class RejectExceptionDemo {

    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(64, 64, 0, TimeUnit.MINUTES, new ArrayBlockingQueue<>(32));

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            CountDownLatch countDownLatch = new CountDownLatch(34);

            for (int j = 0; j < 34; j++) {
                executor.execute(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" 第" + (i+1) + "轮 end -----------------------------------------");
            System.out.println(" ------------ 此时 executor 的参数:");
            System.out.println(" ------------ 线程数:" + executor.getPoolSize());
            System.out.println(" ------------ 任务队列剩余任务数:" + executor.getQueue().size());
            System.out.println(" ------------ 已处理的任务数:" + executor.getTaskCount());

        }
        System.out.println(" 任务执行完成 -----------------------------------------");


    }
}
