package com.ynan;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/3/13 21:23
 */
@RestController
public class HelloController2 {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * 注意这里是 TransmittableThreadLocal 类型
     */
    private ThreadLocal<Map<Integer, Integer>> threadLocal = new TransmittableThreadLocal() {
        @Override
        protected Object initialValue() {
            return new HashMap<Integer, Integer>();
        }
    };

    private int i = 0;

    @RequestMapping("/hello2")
    public String m1() {
        threadLocal.get().put(++i, i);
        System.out.println("主线程" + Thread.currentThread().getName() + " ：" + i + "=" + i);
        System.out.println("主线程获取到：" + threadLocal.get());

        /**
         * TransmittableThreadLocal 配合 TtlRunnable 就可以解决 HelloController1 中线程池的内容
         * 不会跟随父线程改变的问题
         */
        executor.submit(Objects.requireNonNull(TtlRunnable.get(() -> {
            System.out.println("子线程获取到：" + threadLocal.get());
        })));

        threadLocal.remove();
        return "hello world";
    }
}
