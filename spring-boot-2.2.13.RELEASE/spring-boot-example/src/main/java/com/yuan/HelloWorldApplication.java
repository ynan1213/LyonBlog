package com.epichust;

import com.epichust.bean.Bean1;
import com.epichust.listener.MyApplicationEvent;
import com.epichust.listener.MyApplicationListener;
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
		SpringApplication.run(HelloWorldApplication.class);
//		SpringApplication.run(HelloWorldApplication.class, new String[] {"--spring.config.location=E:/"});
//		SpringApplication.run(HelloWorldApplication.class, new String[] {});

//		SpringApplication springApplication = new SpringApplication(HelloWorldApplication.class);
//		springApplication.addListeners(new MyApplicationListener());
//		ConfigurableApplicationContext context = springApplication.run(args);

//		//手动发布事件
//		context.publishEvent(new MyApplicationEvent(new Object()));
	}

}
