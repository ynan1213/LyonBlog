package com.ynan;


import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Runner;

/**
 * FilterRequest ?? ClassRequest ???
 */
public class Test03 {

    public static void main(String[] args) {
        // ????? ClassRequest???????@Test??
        Request request = Request.aClass(Test03.class);
        Runner runner = request.getRunner();

        // ???????FilterRequest?????test1
        Request request1 = Request.method(Test03.class, "test2");
        Runner runner1 = request1.getRunner();

        JUnitCore jUnitCore = new JUnitCore();
        jUnitCore.run(runner1);
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void test3() {
        System.out.println("test3");
    }

}
