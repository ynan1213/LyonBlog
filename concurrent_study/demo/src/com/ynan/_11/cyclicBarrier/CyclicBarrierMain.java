package com.ynan._11.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierMain
{
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException
    {
        CyclicBarrier cb = new CyclicBarrier(3);

        cb.await();

    }
}
