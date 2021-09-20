package com.ynan.insertionSort;

import java.util.Arrays;

/**
 * @program: concurrent_study
 * @author: yn
 * @create: 2021-08-05 10:35
 * @description: 插入排序
 */
public class InsertionSortMain1
{
    public static void main(String[] args)
    {
        int[] arr = {2, 1, 9, 6, 7, 8, 3, 4, 5};
        // int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(Arrays.toString(sort(arr)));
    }

    public static int[] sort(int[] arr)
    {
        for (int i = 1; i < arr.length; i++)
        {
            for (int j = i; j > 0; j--)
            {
                if (arr[j] < arr[j - 1])
                {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    continue;
                }
                break;
            }
        }
        return arr;
    }
}
