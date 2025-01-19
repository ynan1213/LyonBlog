package com.ynan._01.createMultiThreadMethod;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池内线程抛了异常后，会发生说明，
 */
public class ExceptionThreadDemo {

    private static final ExecutorService executor = Executors.newFixedThreadPool(5);


    public static void main(String[] args) {

        executor.submit(() -> {
            System.out.println("---------------- inner thread ------------------");
            int i = 3/0;
        });


    }


}
