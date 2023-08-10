package com.ynan;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yuannan
 */
public class TestMain {

    public static final ThreadLocal<String> local1 = new TransmittableThreadLocal();
    public static final ThreadLocal<String> local2 = new TransmittableThreadLocal();
//    public static final ThreadLocal<String> local = new ThreadLocal();

    public static void main(String[] args) throws InterruptedException {

        // 在线程创建之前set
        // 因为TransmittableThreadLocal继承了InheritableThreadLocal，线程创建之前的值本身就会被传递到子线程中
        local1.set("local1 ---- ");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {});

        // 线程创建之后再set
        local2.set("local2 ---- ");

        TtlRunnable ttlRunnable = TtlRunnable.get(() -> {
            int i = 1;
            while (i <= 2) {
                System.out.println(" ---local1--- " + local1.get());
                System.out.println(" ---local2--- " + local2.get());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        });

        executorService.submit(ttlRunnable);

        TimeUnit.SECONDS.sleep(3);

        // runnable创建之后是不会再传递了的
        local1.set(" local1 已修改 ----");
        local2.set(" local2 已修改 ----");
    }

}
