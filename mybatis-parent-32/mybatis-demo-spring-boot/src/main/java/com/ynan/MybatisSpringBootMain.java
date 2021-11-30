package com.ynan;

import com.ynan.dao.UserDao;
import com.ynan.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author yuannan
 * @Date 2021/11/30 22:22
 */
@SpringBootApplication
@MapperScan("com.ynan.dao")
public class MybatisSpringBootMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MybatisSpringBootMain.class, args);

        UserDao userDao = context.getBean(UserDao.class);
        User user = userDao.getById(1);
        System.out.println(user);
    }
}
