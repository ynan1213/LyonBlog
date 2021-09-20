package com.ynan._06.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockMain
{
    public static void main(String[] args) throws InterruptedException
    {

        ReentrantLock lock = new ReentrantLock(); // 默认非公平锁
        //ReentrantLock lock = new ReentrantLock(true);// 公平锁

        lock.lock();
        lock.lockInterruptibly();// 可中断



        // lock.tryLock();// 不分公平锁和非公平锁，均是state如果等于0就CAS一次，也没有是否可中断的概念
        // lock.tryLock(3, TimeUnit.SECONDS);// 可以响应中断，中断就会抛出InterruptedException异常

        // lock.isFair();// 是否是公平锁
        // lock.isHeldByCurrentThread();// 当前线程是否独占此锁
        //
        // lock.hasQueuedThreads();// 阻塞队列是否有线程在阻塞
        //
        // lock.getQueueLength();// 阻塞队列长度
        // lock.getHoldCount();// 锁重入的深度

        lock.unlock();

    }
}
