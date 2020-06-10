package com.sqsoft.controller;

import com.sqsoft.entity.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HelloWorldController
{
    @RequestMapping(value = {"/login"})
    @ResponseBody
    public String login()
    {
        return "redirect:login";
    }

    @RequestMapping(value = {"/submit"})
    public String submit(@RequestParam Date birthday)
    {
        System.out.println(birthday);
        return "success";
    }

    @RequestMapping(value = {"/get"})
    @ResponseBody
    public User get()
    {
        User user = new User();
        user.setUsername("张三");
        user.setPassword(222222222);
        user.setBirthday(new Date());
        return user;
    }

    @RequestMapping(value = {"/getInt"})
    @ResponseBody
    public int getInt()
    {
        return 123;
    }


}
