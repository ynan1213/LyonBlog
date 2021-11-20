package com.ynan;

import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.rabbit.RabbitMessageChannelBinder;
import org.springframework.cloud.stream.reflection.GenericsUtils;

/**
 * @Author yuannan
 * @Date 2021/11/14 21:24
 */
public class TestMain {

    public static void main(String[] args) {
        Class<?> parameterType = GenericsUtils.getParameterType(RabbitMessageChannelBinder.class, Binder.class, 1);
        System.out.println(parameterType);
    }

}
