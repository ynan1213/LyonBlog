package com.epichust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityApplication
{
    public static void main(String[] args)
    {
        new SpringApplication(SpringSecurityApplication.class).run(args);
    }
}
