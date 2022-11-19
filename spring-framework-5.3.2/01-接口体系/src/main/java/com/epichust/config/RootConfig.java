package com.epichust.config;

import com.epichust.service.UserService;
import com.epichust.service.impl.UserService1;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

// @Configuration注解可不需要
@Configuration
@ComponentScan("com.epichust")
public class RootConfig
{

}
