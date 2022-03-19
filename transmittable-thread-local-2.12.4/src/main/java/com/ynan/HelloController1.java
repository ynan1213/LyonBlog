package com.ynan;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/3/13 21:23
 */
@RestController
public class HelloController1 {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * 为什么这样初始化就不会出现 HelloController中出现的问题了呢？
     */
    private static ThreadLocal<Map<Integer, Integer>> threadLocal = new InheritableThreadLocal() {
        @Override
        protected Object initialValue() {
            return new HashMap<Integer, Integer>();
        }
    };

    private int i = 0;

    @RequestMapping("/hello1")
    public String m1() {

        Thread thread = Thread.currentThread();// 4952 5232

        /**
         * 这里不会出现 HelloController 中的问题是因为每个线程创建的时候父线程的 inheritableThreadLocals ThreadLocalMap
         * 并没有上面的那个hashmap，所以这里每次都会执行initialValue并初始化一个hashMap
         */
        threadLocal.get().put(++i, i);
        System.out.println("主线程" + Thread.currentThread().getName() + " ：" + i + "=" + i);
        System.out.println("主线程获取到：" + threadLocal.get());
        /**
         * 当第一次提交任务的时候才会创建thread并复制父thread的InheritableThreadLocal内容
         * 后面不会新创建，所以内容一直是 1=1
         */
        executor.submit(() -> {
            System.out.println("子线程获取到：" + threadLocal.get());
        });

        /**
         * 这里remove掉的是当前线程的inheritableThreadLocals ThreadLocalMap中this作为key的那个entity
         * 并不会影响线程池中的map
         */
        threadLocal.remove();
        return "hello world";
    }
}
