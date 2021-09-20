package com.ynan._018.concurrentSkipListMap;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @program: concurrent_study
 * @author: yn
 * @create: 2021-07-19 18:37
 * @description:
 */
public class ConcurrentSkipListMapMain
{
    public static void main(String[] args)
    {
        ConcurrentSkipListMap map = new ConcurrentSkipListMap();

        map.put("k1", "v1");

    }
}
