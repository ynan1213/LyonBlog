package com.ynan.main;

import com.ynan.convert.UserConvert;
import com.ynan.entity.Dog;
import com.ynan.entity.User;
import com.ynan.entity.UserVO;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author yuannan
 * @Date 2021/11/6 20:43
 */
public class Test01 {

    public static void main(String[] args) {

        Dog dog = new Dog("旺财", 3);

        List<Long> list = Arrays.asList(123l, 456l);

        User user = new User("xxx", 23, new Date(), 123.4567, dog, list);

        UserVO userVO = UserConvert.INSTANCE.user2222UserVO(user);
        System.out.println(userVO);

    }

}
