package com.epichust.controller;

import com.epichust.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MyController
{
    @Autowired
    private MyService myService;

    @RequestMapping("/")
    public String hello()
    {
        return myService.print("go go go");
    }

}
