package com.epichust.entity;

import com.epichust.service.UserService;
import com.epichust.service.impl.UserService1;
import com.epichust.service.impl.UserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class User1
{
    @Autowired(required = false)
    public User1(UserService1 service1)
    {
        service1.print("");
    }

    @Autowired(required = false)
    public User1(UserService1 service1, UserService2 service2)
    {
        service2.print("2个参数");
    }

    @Autowired(required = false)
    public User1(UserService1 service1, UserService2 service2 , UserService2 service3)
    {
        System.out.println("3个参数===================");
    }
}
