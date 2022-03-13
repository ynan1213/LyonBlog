package com.ynan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yuannan
 * @Date 2022/3/12 20:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dog {
    private Long did;
    private String name;
    private Integer age;
}
