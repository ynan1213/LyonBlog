package com.ynan.selectionSort;

import java.util.Arrays;

/**
 * @program: concurrent_study
 * @author: yn
 * @create: 2021-08-05 09:52
 * @description: 版本1：选择排序
 *
 * 选择排序无法做到像冒泡排序那样，可以判断后面是否是有序的
 * 优化版本就是使用堆排序
 *
 */
public class SelectionSortMain1
{
    public static void main(String[] args)
    {
        int[] arr = {2, 1, 9, 6, 7, 8, 3, 4, 5};
        // int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(Arrays.toString(sort(arr)));
    }

    public static int[] sort(int[] arr)
    {
        for (int end = arr.length - 1; end > 0; end--)
        {
            int max = 0;
            for (int begin = 1; begin <= end; begin++)
            {
                if (arr[begin] > arr[max])
                {
                    max = begin;
                }

            }
            int temp = arr[end];
            arr[end] = arr[max];
            arr[max] = temp;
        }
        return arr;
    }
}
