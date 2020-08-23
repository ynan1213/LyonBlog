package com.sqsoft.controller;

import com.sqsoft.entity.User;
import com.sqsoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController
{
    @Autowired
    private UserService service;

    @RequestMapping("/user")
    @ResponseBody
    public User getUser()
    {
        User user = service.test(3);
        return user;
    }

}
