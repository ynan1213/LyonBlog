package com.epichust.service.main;

import com.epichust.controller.UserController;
import com.epichust.service.config.RootConfig;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.HashMap;

public class MainTest
{
    public static void main(String[] args)
    {
        //需要web环境
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.epichust");
        context.register(RootConfig.class);
        context.refresh();
        UserController userController = context.getBean("userController", UserController.class);
        userController.test();
    }
}
