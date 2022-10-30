package com.ynan._13.enums;

/**
 * @Author yuannan
 * @Date 2022/6/19 10:23
 */
public class EnumDemo {

    public static void main(String[] args) {

        Season[] values = Season.values();
        for (Season value : values) {
            System.out.println(value.toString());
        }

    }
}
