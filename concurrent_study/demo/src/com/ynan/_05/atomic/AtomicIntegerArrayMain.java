package com.ynan._05.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayMain
{
    public static void main(String[] args)
    {
        AtomicIntegerArray array = new AtomicIntegerArray(3);
        array.compareAndSet(1, 0, 2);

        array.set(0, 3);

        System.out.println(array);
    }
}
