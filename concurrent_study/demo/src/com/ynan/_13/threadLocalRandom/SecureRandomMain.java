package com.ynan._13.threadLocalRandom;

import java.security.SecureRandom;

/**
 * @program: concurrent_study
 * @description: demo
 * @author: yn
 * @create: 2021-06-17 10:19
 */
public class SecureRandomMain
{
    public static void main(String[] args)
    {
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 10; i++)
        {
            System.out.println(random.nextInt());
        }
    }
}
