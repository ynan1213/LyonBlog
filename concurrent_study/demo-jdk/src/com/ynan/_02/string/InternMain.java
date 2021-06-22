package com.ynan._02.string;

/**
 * @program: concurrent_study
 * @description:
 * @author: yn
 * @create: 2021-06-18 23:19
 */
public class InternMain
{
    public static void main(String[] args)
    {
        int MAX_COUNT = 1000 * 10000; // 1千万
        String[] arr = new String[MAX_COUNT];

        int[] data = new int[]{1,2,3,4,5,6,7,8,9,10};

        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_COUNT; i++)
        {
            //arr[i] = new String(String.valueOf(data[i % data.length]));// 耗时 9001
            arr[i] = new String(String.valueOf(data[i % data.length])).intern();// 耗时 694
        }
        long end = System.currentTimeMillis();
        System.out.println("共耗时：" + (end - start));


    }
}
