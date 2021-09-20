package com.ynan._017.forkJoinPool;

import java.util.concurrent.ForkJoinPool;

/**
 * @program: concurrent_study
 * @author: yn
 * @create: 2021-07-13 13:06
 * @description:
 */
public class ForkJoinPoolMain
{
    public static void main(String[] args)
    {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        long l = System.currentTimeMillis();
        getSum();
        long l1 = System.currentTimeMillis();
        System.out.println((l1 - l));

    }

    public static int getSum()
    {
        int sum = 0;
        for (int i = 0; i < 1000000; i++)
        {
            sum += i;
        }
        return sum;
    }


}
