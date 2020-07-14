package com.sqsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController
{
    @RequestMapping(value = {"/aaa"})
    public String login()
    {
        return "hello";
    }

    @RequestMapping(value = {"/bbb"})
    public String submit()
    {
        return "pages/yuan";
    }


}
