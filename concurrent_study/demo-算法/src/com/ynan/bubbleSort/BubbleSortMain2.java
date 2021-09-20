package com.ynan.bubbleSort;

import java.util.Arrays;

/**
 * @program: concurrent_study
 * @author: yn
 * @create: 2021-08-05 08:55
 * @description:
 * 版本2：第一个版本中，如果数组已经是有序的情况，或者经过少数几轮排序后有序，就没有必要再循环了
 *
 * 是不是版本2一定比第一个版本1快呢？
 * 其实不是的，如果数组初始状态是完全无序的，这种情况会多出判断指令，整体效果还会慢一些。
 *
 */
public class BubbleSortMain2
{
    public static void main(String[] args)
    {
        int[] arr = {3, 1, 9, 6, 2, 8, 7, 4};
        // int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(Arrays.toString(sort(arr)));
    }

    public static int[] sort(int[] arr)
    {
        boolean flag = true;
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
                    flag = false;
                }
            }
            num++;
            if (flag)
            {
                System.out.println("经过 " + num + " 轮排序");
                break;
            }
        }
        System.out.println("经过 " + num + " 轮排序");
        return arr;
    }
}
