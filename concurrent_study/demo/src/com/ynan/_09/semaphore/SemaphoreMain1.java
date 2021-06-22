package com.ynan._09.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class SemaphoreMain1
{
    public static void main(String[] args) throws InterruptedException
    {
        Semaphore semaphore = new Semaphore(3);

        semaphore.acquire(2);
        System.out.println("-------");
        semaphore.release(3);
        System.out.println(semaphore.availablePermits());
    }
}
