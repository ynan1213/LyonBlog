package com.ynan;

import com.ynan.dao.UserDao;
import com.ynan.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author yuannan
 * @Date 2021/11/30 22:22
 */
@SpringBootApplication
@MapperScan("com.ynan.dao")
@EnableTransactionManagement
public class MybatisSpringBootMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MybatisSpringBootMain.class, args);
        DataSourceTransactionManager transactionManager = context.getBean(DataSourceTransactionManager.class);

        TransactionStatus transactionStatus = transactionManager.getTransaction(TransactionDefinition.withDefaults());
        try {
            UserDao userDao = context.getBean(UserDao.class);
//            User user = userDao.getById(1);
//            System.out.println(user);

            User user = new User("abc", 0);
            userDao.insert(user);

            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
        }
    }
}
