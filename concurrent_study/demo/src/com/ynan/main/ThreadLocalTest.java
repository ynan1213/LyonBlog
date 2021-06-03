package com.ynan.main;

import org.omg.CORBA.PUBLIC_MEMBER;

public class ThreadLocalTest
{
    public static void main(String[] args)
    {
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set("hello world");
    }
}
