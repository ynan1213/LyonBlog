package com.ynan._01.hashMap;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class HashMapMain {

    public static void main(String[] args) {
        Object obj = new Object();
        Map<WeakReference<byte[]>, Object> map = new HashMap();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        for (int i = 0; i < 1000; i++) {
            byte[] bytes = new byte[1024 * 1024];
            Wr wr = new Wr(bytes, queue);
            map.put(wr, obj);
        }
        System.out.println(map.size());
        int i = 0;
    }

}

class Wr extends WeakReference {
    int i = 0;
    public Wr(byte[] bytes, ReferenceQueue q) {
        super(bytes, q);
        i = bytes.length;
    }
}
