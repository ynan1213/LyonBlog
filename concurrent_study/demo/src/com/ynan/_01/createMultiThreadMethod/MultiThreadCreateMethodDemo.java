package com.ynan._01.createMultiThreadMethod;

import java.util.concurrent.TimeUnit;

/**
 * 线程创建方式
 */
public class MultiThreadCreateMethodDemo extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "-----------------");
        throw new RuntimeException("xxxxxxxxxxxxxxxxxx");
    }

    public static void main(String[] args) {

        MultiThreadCreateMethodDemo threadMethod = new MultiThreadCreateMethodDemo();
        threadMethod.start();

        while (true) {
            System.out.println("main --------------------");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
