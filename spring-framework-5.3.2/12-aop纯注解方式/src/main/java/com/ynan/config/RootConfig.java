package com.ynan.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
@ComponentScan("com.ynan")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
public class RootConfig
{
}
