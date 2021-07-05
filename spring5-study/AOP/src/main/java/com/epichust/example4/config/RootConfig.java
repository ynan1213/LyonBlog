package com.epichust.example4.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.epichust.example4")
@EnableAspectJAutoProxy
public class RootConfig
{
}
