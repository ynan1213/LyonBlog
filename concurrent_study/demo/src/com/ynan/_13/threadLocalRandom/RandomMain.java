package com.ynan._13.threadLocalRandom;

import java.util.Random;

/**
 * @program: concurrent_study
 * @description: demo
 * @author: yn
 * @create: 2021-06-17 09:44
 */
public class RandomMain
{
    public static void main(String[] args)
    {
        Random random = new Random();
        for (int i = 0; i < 10; i++)
        {
            System.out.println(random.nextDouble());
        }

    }
}
