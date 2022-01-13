package com.ynan._10.exception;

/**
 * @Author yuannan
 * @Date 2021/12/27 10:55
 */
public class InvocationTargetExceptionDemo {

    public static void main(String[] args) throws Exception {
        A a = new A();
        a.run();
    }
}

class A {

    public void run() throws Exception {
        throw new Exception("XXXXXXXXXXXXXXXX");
    }
}