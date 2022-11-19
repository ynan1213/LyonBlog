package com.ynan.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring
 * @description:
 * @author: yn
 * @create: 2021-06-22 17:23
 */

// @Configuration注解可不需要（因为由@ComponentScan）
@Configuration("RootConfigxxxx")
@ComponentScan("com.ynan")
public class RootConfig1
{
}
