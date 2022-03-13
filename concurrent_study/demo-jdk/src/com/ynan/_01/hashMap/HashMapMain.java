package com.ynan._01.hashMap;

import java.util.*;
import java.util.Map.Entry;

public class HashMapMain
{
    public static void main(String[] args)
    {
        HashMap map = new HashMap();
        map.put("1", "2");

        Map map1 = Collections.emptyMap();

        Set<Map.Entry> set = map1.entrySet();
        for (Entry entry : set) {
            System.out.println(entry.getKey());
        }


    }
}