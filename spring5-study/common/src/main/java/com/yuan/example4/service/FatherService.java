package com.epichust.example4.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FatherService
{
    @Bean("ffff")
    public TestBean getTestBean()
    {
        return new TestBean("ffffffffff");
    }
}
