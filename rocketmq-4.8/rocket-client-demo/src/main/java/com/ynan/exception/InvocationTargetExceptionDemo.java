package com.ynan.exception;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author yuannan
 * @Date 2021/12/27 10:58
 */
public class InvocationTargetExceptionDemo {

    public static void main(String[] args)
        throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> aClass = Class.forName("com.ynan.exception.A");
        Method run = aClass.getMethod("run");
        run.invoke(aClass.newInstance());
    }
}

class A {

    public void run() {
        throw new RuntimeException("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    }
}