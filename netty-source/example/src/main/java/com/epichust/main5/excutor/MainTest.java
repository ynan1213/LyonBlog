package com.epichust.main5.excutor;

import io.netty.util.concurrent.*;

public class MainTest
{
    public static void main(String[] args)
    {
        // 构造线程池
        //EventExecutor executor = new DefaultEventExecutor();
        DefaultEventExecutor executor = new DefaultEventExecutor();

        // 创建 DefaultPromise 实例
        DefaultPromise promise = new DefaultPromise(executor);

        // 下面给这个promise 添加两个 listener
        promise.addListener(new GenericFutureListener<Future<Integer>>() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("任务成功，结果：" + future.get());
                } else {
                    System.out.println("任务失败，异常：" + future.cause());
                }
            }
        }).addListener(new GenericFutureListener<Future<Integer>>() {
            @Override
            public void operationComplete(Future future) throws Exception {
                System.out.println("任务结束，balabala...");
            }
        });
        // 提交任务到线程池，五秒后执行结束，设置执行 promise 的结果
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
                // 设置 promise 的结果
                // promise.setFailure(new RuntimeException());
                promise.setSuccess(1213);
            }
        });

        try {
            //promise的sync和await都会阻塞线程，等待任务执行完成
            //main线程阻塞等待任务完成,完成后继续往后走，但是NioEventLoop中的线程还在阻塞中等待任务队列的任务，所以jvm并不会停止
            //阻塞的实现是通过wait和notifyAll实现的
            promise.sync();

            //await和sync的区别：sync内部调用的正是await，
            // 但是sync阻塞等待任务结束，如果任务失败，将“导致失败的异常”重新抛出来,await不会
            //promise.await();
            System.out.println("main线程走完");
        } catch (Exception e) {
        }
    }
}
