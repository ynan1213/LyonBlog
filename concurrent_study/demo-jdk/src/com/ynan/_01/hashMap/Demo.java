package com.ynan._01.hashMap;

import java.util.Objects;

/**
 * @Author yuannan
 * @Date 2022/3/20 12:29
 */
public class Demo {
    private int i;

    public Demo(int i) {
        this.i = i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Demo demo = (Demo) o;
        return i == demo.i;
    }

    @Override
    public int hashCode() {
        return i;
    }
}
