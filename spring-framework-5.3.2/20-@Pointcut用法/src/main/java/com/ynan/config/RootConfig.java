package com.ynan.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author yuannan
 * @Date 2022/5/30 17:39
 */
@Configuration
@ComponentScan("com.ynan")
@EnableAspectJAutoProxy
public class RootConfig {

}
