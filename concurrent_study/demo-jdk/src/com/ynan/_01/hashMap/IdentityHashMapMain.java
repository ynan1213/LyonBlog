package com.ynan._01.hashMap;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @Author yuannan
 * @Date 2022/3/20 12:33
 */
public class IdentityHashMapMain {

    public static void main(String[] args) {
        IdentityHashMap map = new IdentityHashMap();
        map.put(new Demo(1), 1);
        map.put(new Demo(1), 1);
        map.put(new Demo(1), 1);
        System.out.println(map.size());
    }
}
