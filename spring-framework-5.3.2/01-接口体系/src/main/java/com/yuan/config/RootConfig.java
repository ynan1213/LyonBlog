package com.epichust.config;

import com.epichust.service.impl.BeanService;
import com.epichust.service.impl.UserService1;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Configuration("RootConfigxxxx")
@ComponentScan("com.epichust")
//@PropertySource("classpath:dev.properties")
//@Import(BeanService.class)
@Conditional(RootConfig.ConditionTest.class)
public class RootConfig
{
//    @Autowired(required = false)
//    private UserService1 userService1;
//
//    @Autowired
//    @Qualifier("userService2")
//    private UserService userService;

    @Bean("userService1")
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
