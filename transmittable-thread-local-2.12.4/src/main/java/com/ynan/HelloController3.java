package com.ynan;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/3/16 23:18
 */
@RestController
public class HelloController3 {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private ThreadLocal<Map<Integer, Integer>> threadLocal = new TransmittableThreadLocal() {
        @Override
        protected Object initialValue() {
            return new HashMap<Integer, Integer>();
        }
    };

    private int i = 0;

    @RequestMapping("/hello3")
    public String m1() {
        threadLocal.get().put(++i, i);
        System.out.println("主线程" + Thread.currentThread().getName() + " ：" + i + "=" + i);
        System.out.println("主线程获取到：" + threadLocal.get());

        executor.submit(Objects.requireNonNull(TtlRunnable.get(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程获取到：" + threadLocal.get());
        })));

        /**
         * 有个问题：对于任务提交完成了，主线程再往这里map值，为什么还是能改变子线程的内容呢？
         * 如果不想在提交任务完后还能改变子线程的内容，有什么办法？
         */
        threadLocal.get().put(++i, i);

        threadLocal.remove();
        return "hello world";
    }
}
