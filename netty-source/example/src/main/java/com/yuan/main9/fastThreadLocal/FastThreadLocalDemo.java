package com.epichust.main9.fastThreadLocal;

import io.netty.util.concurrent.FastThreadLocal;

public class FastThreadLocalDemo
{
    private final FastThreadLocalTest fastThreadLocalTest;

    public FastThreadLocalDemo()
    {
        fastThreadLocalTest = new FastThreadLocalTest();
    }

    final class FastThreadLocalTest extends FastThreadLocal<Object>
    {
        @Override
        protected Object initialValue() throws Exception
        {
            return new Object();
        }
    }

    public static void main(String[] args)
    {
        FastThreadLocalDemo fastThreadLocalDemo = new FastThreadLocalDemo();

        new Thread(() -> {
            Object obj = fastThreadLocalDemo.fastThreadLocalTest.get();
            try
            {
                for (int i = 0; i < 10; i++)
                {
                    fastThreadLocalDemo.fastThreadLocalTest.set(new Object());
                    Thread.sleep(1000);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try
            {
                Object obj = fastThreadLocalDemo.fastThreadLocalTest.get();
                for (int i = 0; i < 10; i++)
                {
                    System.out.println(obj == fastThreadLocalDemo.fastThreadLocalTest.get());
                    Thread.sleep(1000);
                }
            } catch (Exception e)
            {

            }
        }).start();
    }
}