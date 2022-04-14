package com.ynan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/4/14 20:26
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return " ======= hello world !!! =======";
    }
}
