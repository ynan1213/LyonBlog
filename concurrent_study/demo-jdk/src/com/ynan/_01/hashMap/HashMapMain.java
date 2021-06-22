package com.ynan._01.hashMap;

import java.util.*;

public class HashMapMain
{
    public static void main(String[] args)
    {
        HashMap map = new HashMap();

        map.put(null, "xxx");
        map.put(null, "yyy");
        System.out.println(map.size());
        System.out.println(map.get(null));

    }
}
