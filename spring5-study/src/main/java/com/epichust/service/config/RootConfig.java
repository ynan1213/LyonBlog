package com.epichust.service.config;

import com.epichust.service.UserService;
import com.epichust.service.impl.UserService1;
import com.epichust.service.impl.UserService2;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

//@Configuration("RootConfigxxxx")
@ComponentScan("com.epichust")
@PropertySource("classpath:dev.properties")
public class RootConfig
{
    @Autowired
    private UserService1 userService1;

    @Autowired
    @Qualifier("userService2")
    private UserService userService;



}
