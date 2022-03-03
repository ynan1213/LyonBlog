package com.ynan.enum1;

import java.util.Arrays;

/**
 * @Author yuannan
 * @Date 2022/3/3 23:22
 */
public enum AgeEnum {

    MAN(0), WOMAN(1);

    private int age;

    AgeEnum(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public static AgeEnum fromInt(int i) {
        AgeEnum[] values = AgeEnum.values();
        return Arrays.stream(AgeEnum.values()).filter(en -> en.getAge() == i)
            .findFirst().orElseThrow(() -> new RuntimeException("xxx"));
    }
}
