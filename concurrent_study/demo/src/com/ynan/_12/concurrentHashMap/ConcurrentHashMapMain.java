package com.ynan._12.concurrentHashMap;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: concurrent_study
 * @description: demo
 * @author: yn
 * @create: 2021-06-16 18:04
 */
public class ConcurrentHashMapMain
{
    public static void main(String[] args)
    {
        ConcurrentHashMap map = new ConcurrentHashMap();
        ConcurrentHashMap map1 = new ConcurrentHashMap(8);
        ConcurrentHashMap map2 = new ConcurrentHashMap(16, 0.75f, 16);

        map.put("k1", "v1");

        map.get("k1");

        map.size();

        map.contains("k1");


    }
}
