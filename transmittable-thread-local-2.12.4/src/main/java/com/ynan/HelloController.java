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
public class HelloController {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private static ThreadLocal<Map<Integer, Integer>> threadLocal = new TransmittableThreadLocal();

    private static Thread currentThread;

    private int i = 0;

    /**
     * 注意：把 threadLocal 放在 static中初始化是有问题的
     * 是因为static代码块方法在main方法中执行
     */
    static {
        currentThread = Thread.currentThread();
        Map<Integer, Integer> map = threadLocal.get();
        if (map == null) {
            threadLocal.set(new HashMap<>());
        }
    }

    @RequestMapping("/hello")
    public String m1() {

        /**
         * 这里 threadLocal.get() 返回的map是上面static静态代码块中的那个map引用
         * 是因为TransmittableThreadLocal 继承了 InheritableThreadLocal
         * 当前线程是tomcat线程池线程，由main线程创建，所以再创建当前线程的时候new Thread()中会复制main线程的内容
         * 到当前线程的 inheritableThreadLocals 中，也就是线程中一直持有同一个 hashMap
         */
        threadLocal.get().put(++i, i);
        System.out.println("主线程" + Thread.currentThread().getName() + " ：" + i + "=" + i);
        System.out.println("主线程获取到：" + threadLocal.get());

        executor.submit(Objects.requireNonNull(TtlRunnable.get(() -> {
            System.out.println("子线程获取到：" + threadLocal.get());
        })));
        threadLocal.remove();
        return "hello world";
    }
}
