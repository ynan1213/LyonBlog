package com.ynan._06.hashSet;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class LinkedHashSetMain
{
    public static void main(String[] args)
    {
        LinkedHashSet set = new LinkedHashSet();

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
