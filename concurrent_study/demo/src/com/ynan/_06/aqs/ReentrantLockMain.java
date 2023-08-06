package com.ynan._06.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockMain {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock(); // 默认非公平锁
        //ReentrantLock lock = new ReentrantLock(true);// 公平锁

        // 无法被中断，也就是只要没有获取到锁，就会一直阻塞或者自旋阻塞
        // 和lockInterruptibly的区别就是自旋阻塞的方法中，即使被interrupt唤醒了，也会再次进入阻塞知道获取到锁
        lock.lock();

        // 可中断，和lock的区别就是如果被interrupt唤醒了，就抛出InterruptedException异常
        lock.lockInterruptibly();

        // 不分公平锁和非公平锁没有区别，均是nonfairTryAcquire尝试CAS一次，不会加入队列阻塞，也没有是否可中断的概念
        lock.tryLock();

        // 先tryAcquire一次（公平和非公平有区别），失败就加入队列阻塞一定时间，可以响应中断，中断就会抛出InterruptedException异常
        lock.tryLock(3, TimeUnit.SECONDS);

        lock.unlock();

        // 是否是公平锁
        lock.isFair();
        // 当前线程是否独占此锁
        lock.isHeldByCurrentThread();

        // 阻塞队列是否有线程在阻塞，原理是判断head == tail
        lock.hasQueuedThreads();

        // 阻塞队列长度，原理是从tail往前遍历，如果node的thread不为null就+1
        lock.getQueueLength();

        // 锁重入的深度，返回state值大小
        lock.getHoldCount();

    }
}
