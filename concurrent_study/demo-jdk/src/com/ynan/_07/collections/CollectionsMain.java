package com.ynan._07.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: concurrent_study
 * @description: demo
 * @author: yn
 * @create: 2021-06-17 23:25
 */
public class CollectionsMain
{
    public static void main(String[] args)
    {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++)
        {
            list.add(i);
        }

        System.out.println("原始的顺序：" + list);

        Collections.shuffle(list);
        System.out.println("打乱后顺序：" + list);

        System.out.println("二分查找20：" + Collections.binarySearch(list, 20));
        System.out.println("二分查找2：" + Collections.binarySearch(list, 2));

    }
}
