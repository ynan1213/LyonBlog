package com.epichust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ServletComponentScan
//@MapperScan(basePackages = "com.epichust.dao")
public class shiroApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(shiroApplication.class, new String[]{"--debug"});
    }
}