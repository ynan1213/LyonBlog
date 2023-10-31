package com.ynan.entity;

import com.ynan.enum1.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yuannan
 * @Date 2021/11/30 22:24
 */
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Integer age;
//    private GenderEnum gender;


//    public User(String name, Integer age) {
//        this.name = name;
//        this.age = AgeEnum.fromInt(age);
//    }
}
