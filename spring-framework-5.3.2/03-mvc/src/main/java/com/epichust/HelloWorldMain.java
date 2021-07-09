package com.epichust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

//@EnableWebMvc
@SpringBootApplication
public class HelloWorldMain
{
	public static void main(String[] args)
	{
		SpringApplication.run(HelloWorldMain.class, args);
	}
}
