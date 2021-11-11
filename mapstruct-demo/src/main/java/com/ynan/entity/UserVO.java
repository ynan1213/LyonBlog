package com.ynan.entity;

import java.util.Date;
import lombok.Data;

/**
 * @Author yuannan
 * @Date 2021/11/6 20:38
 */
@Data
public class UserVO {

    private String name;

    private int age;

    private Date birthday;

    private String pri;

    private DogVO dogVO;

    private Boolean hasMoney;
}
