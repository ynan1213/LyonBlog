package com.ynan._04.linkedList;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListMain
{
    public static void main(String[] args)
    {
        LinkedList list = new LinkedList();

        list.add("111");
        list.add(1, "333");

        int i = list.indexOf("333");
        list.lastIndexOf("333");

        list.get(1);

        list.remove("333");


        // 作为栈使用
        list.push("22");
        list.peek();
        list.poll();
        list.pop();

        // 作为queue使用
        list.offer("444");


    }
}
