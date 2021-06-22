package com.ynan._02.string;

/**
 * @program: concurrent_study
 * @description:
 * @author: yn
 * @create: 2021-06-18 23:07
 */
public class StringMain3
{
    public static void main(String[] args)
    {
        System.out.println("ABCDEa123abc".hashCode());  // 165374702
        System.out.println("ABCDFB123abc".hashCode());

        System.out.println("ABCDEa123abc".equals("ABCDFB123abc"));

        System.gc();
        System.runFinalization();

    }
}
