package com.ynan;

import com.ynan.dao.UserDao;
import com.ynan.entity.User;
import com.ynan.entity.UserCondition;
import com.ynan.enum1.GenderEnum;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @Author yuannan
 * @Date 2021/11/30 22:22
 */
@SpringBootApplication
@MapperScan(value = "com.ynan.dao")
@EnableTransactionManagement
public class MybatisSpringBootMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MybatisSpringBootMain.class, args);
        TransactionTemplate transactionTemplate = context.getBean(TransactionTemplate.class);

        transactionTemplate.execute(status -> {
            UserDao userDao = context.getBean(UserDao.class);

//            User user = new User();
//            user.setName("111");
//            user.setGender(GenderEnum.MAN);
//            userDao.insert(user);

            List<User> userList = userDao.get(new UserCondition().setXxx(0), "yyy");
            System.out.println(userList);
            return null;
        });

    }
}
