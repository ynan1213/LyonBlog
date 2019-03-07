package com.sqsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sqsoft.dao")
public class shiroApplication{
	public static void main(String[] args) {
		SpringApplication.run(shiroApplication.class, args);
	}
}