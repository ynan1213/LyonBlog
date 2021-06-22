package com.ynan._01.hashMap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: concurrent_study
 * @description: LinkedHashMap demo
 * @author: yn
 * @create: 2021-06-16 16:51
 **/
public class LinkedHashMapMain
{
    public static void main(String[] args)
    {
        Map map = new LinkedHashMap(10, 0.75f, true);

        map.put("name", "张三");
        map.put("age", "31");
        map.put("address", "武汉");

        System.out.println(map);

        map.get("age");

        System.out.println(map);


    }
}
