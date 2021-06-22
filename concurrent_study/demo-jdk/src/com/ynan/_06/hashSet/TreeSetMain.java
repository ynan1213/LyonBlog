package com.ynan._06.hashSet;

import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetMain
{
    public static void main(String[] args)
    {
        TreeSet set = new TreeSet();
        set.add("222");
        set.add("fff");
        set.add("111");
        set.add("aaa");

        Iterator iterator = set.iterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }
    }
}
