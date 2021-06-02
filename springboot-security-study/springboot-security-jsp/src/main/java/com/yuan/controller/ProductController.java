package com.epichust.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController
{
    @RequestMapping("/findAll")
    @ResponseBody
    @Secured("ADMIN")
    public String findAll()
    {
        return "product-list";
    }

    @RequestMapping("/index")
    public String goIndex()
    {
        return "index";
    }


}
