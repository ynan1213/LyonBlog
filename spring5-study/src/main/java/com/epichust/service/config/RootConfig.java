package com.epichust.service.config;

import com.epichust.service.UserService;
import com.epichust.service.impl.BeanService;
import com.epichust.service.impl.UserService1;
import com.epichust.service.impl.UserService2;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.*;

@Configuration("RootConfigxxxx")
@ComponentScan("com.epichust")
//@PropertySource("classpath:dev.properties")
@Import(BeanService.class)
public class RootConfig
{
    @Autowired(required = false)
    private UserService1 userService1;

    @Autowired
    @Qualifier("userService2")
    private UserService userService;

    @Bean
    public UserService1 gg()
    {
        return new UserService1();
    }


}
