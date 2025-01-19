package com.ynan._01.createMultiThreadMethod;

import java.util.concurrent.*;

public class Main
{
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException
    {
        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(5);
            // if (1 == 1)
            //     throw new RuntimeException("出了异常");
            return "执行完";
        };

        FutureTask<String> task = new FutureTask(callable);

        new Thread(task).start();

        // 主线程阻塞等待task执行完毕
        // 如果call方法内抛了异常，这里的get方法也会抛出异常
        String result = task.get();

        // 添加超时机制，超时了不是返回null，而是抛出 TimeoutException 异常
        String result1 = task.get(3, TimeUnit.SECONDS);

        System.out.println("主线程中拿到异步任务执行的结果为：" + result);

    }
}
