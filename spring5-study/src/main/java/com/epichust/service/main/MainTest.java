package com.epichust.service.main;

import com.epichust.service.config.RootConfig;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

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
        Object userController = context.getBean("userController");

    }
}
