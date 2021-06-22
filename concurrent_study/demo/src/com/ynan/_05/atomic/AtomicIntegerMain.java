package com.ynan._05.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerMain
{
    public static void main(String[] args)
    {
        AtomicInteger i = new AtomicInteger(2);

        i.accumulateAndGet(5, (x, y) -> {
            return x * y;
        });

        System.out.println(i.get());
    }
}
