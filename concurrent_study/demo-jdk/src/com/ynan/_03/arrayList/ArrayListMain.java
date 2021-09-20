package com.ynan._03.arrayList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ArrayListMain
{
    public static void main(String[] args)
    {
        ArrayList<Integer> list = new ArrayList();

        list.add(null);
        list.add(null);

        list.add(2);
        list.add(3);
        System.out.println(list);
        list.remove(null);
        System.out.println(list);
    }
}
