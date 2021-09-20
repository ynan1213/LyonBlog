package com.ynan._06.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionMain
{
    public static void main(String[] args) throws InterruptedException
    {
        ReentrantLock lock = new ReentrantLock();

        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();

        lock.lock();
        conditionA.await();
    }
}
