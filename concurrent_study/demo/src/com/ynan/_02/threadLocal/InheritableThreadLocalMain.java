package com.ynan._02.threadLocal;

/**
 * @program: concurrent_study
 * @description:
 * @author: yn
 * @create: 2021-06-21 10:16
 */
public class InheritableThreadLocalMain
{
    public static void main(String[] args)
    {
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal();

        // 父线程set值
        inheritableThreadLocal.set("hello");

        new Thread(() -> {
            // 子线程获取值
            String s = inheritableThreadLocal.get();
            System.out.println(s);
        }).start();

        // 但是线程创建后，是读取不到父线程再次set进去值的
        inheritableThreadLocal.set("world");
    }
}
