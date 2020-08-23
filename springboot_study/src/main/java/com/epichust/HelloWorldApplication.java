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
//@EnableConfigurationProperties(Bean1.class)
//@ComponentScan("com.epichust.controller")
@ServletComponentScan
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
//		//在合适的时机会执行
//		ConfigurableApplicationContext context = springApplication.run(args);
//		//手动发布事件，这种情况下意义不大，容器已经全部初始化完成
//		context.publishEvent(new MyApplicationEvent(new Object()));
	}

}
