package com.epichust.example4.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SonService extends FatherService
{
    @Bean("ssss")
    @Override
    public TestBean getTestBean()
    {
        return new TestBean("sssssssssss");
    }
}
