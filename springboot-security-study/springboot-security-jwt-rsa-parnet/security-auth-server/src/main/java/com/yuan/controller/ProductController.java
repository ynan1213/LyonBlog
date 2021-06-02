package com.epichust.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController
{
    @RequestMapping("/findAll")
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
