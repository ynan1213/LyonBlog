package com.ynan._05.atomic;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderMain
{
    public static void main(String[] args)
    {
        LongAdder longAdder = new LongAdder();
        longAdder.decrement();

        longAdder.sum();

        longAdder.reset();

        longAdder.add(3);


    }
}
