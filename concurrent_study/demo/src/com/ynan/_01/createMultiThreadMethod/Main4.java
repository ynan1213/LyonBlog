package com.ynan._01.createMultiThreadMethod;

import java.util.concurrent.*;

public class Main4 {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(0, 5, 1000, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>());

        poolExecutor.submit(() -> {});
        poolExecutor.submit(() -> {});
        poolExecutor.submit(() -> {});
        poolExecutor.submit(() -> {});
        poolExecutor.submit(() -> {});
        poolExecutor.submit(() -> {});
        poolExecutor.submit(() -> {});




    }
}
