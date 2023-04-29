package com.epichust;

import com.epichust.bean.Bean1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

// 该自定义的@ComponentScan 会覆盖 @SpringBootApplication里面的 @ComponentScan
//@ComponentScan("com.epichust.controller")

//@EnableConfigurationProperties(Bean1.class)
//@ServletComponentScan
public class HelloWorldApplication
{
	public static void main(String[] args)
	{
//		System.getProperties().setProperty("spring.profiles.active", "dev");
		ConfigurableApplicationContext applicationContext = SpringApplication.run(HelloWorldApplication.class, args);
//		SpringApplication.run(HelloWorldApplication.class, new String[] {"--spring.config.location=E:/"});
//		SpringApplication.run(HelloWorldApplication.class, new String[] {});

	}

}
