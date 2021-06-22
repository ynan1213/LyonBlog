package com.ynan._03.arrayList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ArrayListMain
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        // list.add("111");
        // list.add("222");
        // list.add("333");
        // list.add("444");
        // list.add("555");
        // list.add("666");
        // list.add("777");
        // list.add("888");
        //
        // Iterator iterator = list.iterator();
        // ListIterator listIterator = list.listIterator(3);
        //
        // list.remove(3);
        // list.remove("555");


        list.add("111");
        list.add(1, "333");
        list.set(3, "333");
        System.out.println(list.size());
        Iterator iterator = list.iterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }

    }
}
