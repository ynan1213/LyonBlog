package com.ynan._02.string;

/**
 * @program: concurrent_study
 * @description: demo
 * @author: yn
 * @create: 2021-06-18 23:03
 */
public class StringMain2
{
    public static void main(String[] args)
    {
        final String s1 = "a";
        final String s2 = "b";
        String s3 = "ab";

        String s4 = s1 + s2;

        System.out.println(s3 == s4);

    }
}
