package com.ynan._01.hashMap;

import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

/**
 * @Author yuannan
 * @Date 2022/3/21 22:40
 */
public class WeakHashMapMain {

    public static void main(String[] args) {
        Object obj = new Object();
        Map<Object, Object> map = new WeakHashMap();
        for (int i = 0; i < 1000; i++) {
            byte[] bytes = new byte[1024 * 1024];
            map.put(bytes, obj);
        }
        System.out.println(map.size());
        int i = 0;
        for (Entry<Object, Object> entry : map.entrySet()) {
            System.out.println("" + (i++) + "  :" + entry.getKey().toString());
        }
    }
}
