package com.ynan.bubbleSort;

import java.util.Arrays;

/**
 * @program: concurrent_study
 * @author: yn
 * @create: 2021-08-05 08:55
 * @description: 版本3：对于局部有序的数组，其实是可以提前退出的
 * <p>
 * 3 2 3 4 1 5 6 7 8
 * 比如 2 3 4 和 5 6 7 8 都是局部有效的
 */
public class BubbleSortMain3
{
    public static void main(String[] args)
    {
        int[] arr = {2, 1, 9, 6, 7, 8, 3, 4, 5};
        // int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(Arrays.toString(sort(arr)));
    }

    public static int[] sort(int[] arr)
    {
        int index = 0;
        int num = 0;
        for (int j = arr.length - 1; j > 1; j--)
        {
            for (int i = 0; i < j; i++)
            {
                if (arr[i] > arr[i + 1])
                {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;

                    index = i;
                }
                num++;
            }
            j = index + 1;
        }
        System.out.println("经过 " + num + " 轮排序");
        return arr;
    }
}
