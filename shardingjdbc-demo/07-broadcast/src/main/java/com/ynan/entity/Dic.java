package com.ynan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yuannan
 * @Date 2022/3/12 21:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dic {

    private Long id;
    private String name;
    private String value;
}
