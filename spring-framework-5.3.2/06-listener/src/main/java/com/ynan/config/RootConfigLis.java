package com.ynan.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @program: spring
 * @description:
 * @author: yn
 * @create: 2021-06-22 16:23
 */


@Configuration
@ComponentScan("com.ynan.event")
@EnableAsync
public class RootConfigLis
{
}
