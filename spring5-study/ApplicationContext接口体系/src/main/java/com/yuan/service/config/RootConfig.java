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
import org.springframework.core.type.AnnotatedTypeMetadata;

//@Configuration("RootConfigxxxx")
@ComponentScan("com.epichust")
//@PropertySource("classpath:dev.properties")
@Import(BeanService.class)
@Conditional(RootConfig.ConditionTest.class)
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

    /**
     * 测试 @Conditional 注解，如果是内部类，必须定义成static
     */
    public static class ConditionTest implements Condition
    {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
        {
            return true;
        }
    }

}
