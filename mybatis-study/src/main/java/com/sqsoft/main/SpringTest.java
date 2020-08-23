package com.sqsoft.main;

import com.sqsoft.service.UserService;
import com.sqsoft.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest
{
    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:springApplication.xml");
        UserService service = context.getBean("userService", UserService.class);
        User user = service.test(2);
        System.out.println(user);
    }
}
