package com.ynan._014.copyOnWriteArrayList;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @program: concurrent_study
 * @description: demo
 * @author: yn
 * @create: 2021-06-18 00:07
 */
public class CopyOnWriteArrayListMain
{
    public static void main(String[] args)
    {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();

        list.add("hello");

        Object o = list.get(1);

        Iterator iterator = list.iterator();

    }
}
