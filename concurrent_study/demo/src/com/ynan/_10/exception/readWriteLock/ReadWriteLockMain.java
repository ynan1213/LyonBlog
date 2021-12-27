package com.ynan._10.readWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockMain
{
    public static void main(String[] args)
    {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        readLock.lock();

        writeLock.lock();

        readLock.unlock();

    }
}
