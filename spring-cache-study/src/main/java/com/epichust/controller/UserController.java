package com.epichust.controller;

import com.epichust.entity.User;
import com.epichust.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    @ResponseBody
    public String getUser()
    {
        String result = userService.hello(11111,new User("张三",23));
        System.out.println(result);
        return result;
    }

    @RequestMapping("/info")
    @ResponseBody
    public String getInfo()
    {
        User user = userService.getInfo(11111,new User("张三",23));
        System.out.println(user);
        return "result";
    }

}
