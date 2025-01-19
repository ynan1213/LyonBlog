package com.ynan._01.createMultiThreadMethod;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yuannan
 */
public class ScheduledMain {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);

        executor.schedule(() -> {}, 100, TimeUnit.MINUTES);
        executor.schedule(() -> 1, 200, TimeUnit.MINUTES);

        executor.execute(() -> {

        });
    }
}
