package com.ynan.controller;

import com.ynan.client.RemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/4/14 23:11
 */
@RestController
public class HelloController {

    @Autowired
    private RemoteClient remoteClient;

    @RequestMapping("/hello")
    public String hello() {
        return remoteClient.remote("hello");
    }

}
