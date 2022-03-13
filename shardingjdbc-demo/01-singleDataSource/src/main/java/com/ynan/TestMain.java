package com.ynan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @Author yuannan
 * @Date 2021/11/4 21:21
 */

@SpringBootApplication
public class TestMain {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(TestMain.class, args);
    }

}
