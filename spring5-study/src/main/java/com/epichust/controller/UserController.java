package com.epichust.controller;

import com.epichust.service.UserService;
import com.epichust.service.impl.BeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController
{
//    @Autowired
//    private UserService service;

    @Autowired
    private BeanService service;

    public void test()
    {
        service.print();
    }


}
