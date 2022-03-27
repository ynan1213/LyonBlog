package com.epichust.main9.fastThreadLocal;

import io.netty.util.concurrent.FastThreadLocal;

public class FastThreadLocalDemo {

    public static void main(String[] args) {
        FastThreadLocal threadLocal = new FastThreadLocal();

        threadLocal.get();
        threadLocal.isSet();

        threadLocal.set("xxx");

    }


}