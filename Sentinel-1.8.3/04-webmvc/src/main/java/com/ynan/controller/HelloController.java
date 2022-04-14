package com.ynan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/4/14 22:07
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return " ======= 04mvc hello world !!! =======";
    }

}
