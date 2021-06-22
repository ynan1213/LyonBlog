package com.ynan._02.threadLocal;

public class ThreadLocalTest
{
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        ThreadLocal<String> threadLocal = new ThreadLocal()
        {
            @Override
            protected String initialValue()
            {
                return "hello";
            }
        };

        // 首次get取的是initialValue的值
        String s = threadLocal.get();

        threadLocal.set("hello world");

        threadLocal.remove();

    }
}
