package com.epichust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//
//@EnableWebMvc 会导致SpringBoot 对SpringMVC的自动配置失效
@SpringBootApplication
public class HelloWorldMain
{
	public static void main(String[] args)
	{
		SpringApplication.run(HelloWorldMain.class, args);
	}
}
