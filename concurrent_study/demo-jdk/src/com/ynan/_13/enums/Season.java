package com.ynan._13.enums;

/**
 * @Author yuannan
 * @Date 2022/6/19 10:17
 */
public enum Season {

    SPRING("春天", 001),
    Summer("夏天", 002),
    AUTUMN("秋天", 003);

    private String desc;
    private int code;

    Season(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

}
