package com.ynan._05.atomic;

import java.util.concurrent.atomic.AtomicLong;

public class MainAtomicLong
{
    public static void main(String[] args)
    {
        AtomicLong atomicLong = new AtomicLong();

        atomicLong.compareAndSet(0, 5);

        atomicLong.incrementAndGet();

    }
}
