package com.sqsoft.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.sqsoft.dao")
@ComponentScan("com.sqsoft")
public class SpringBootTest
{
    public static void main(String[] args)
    {
        new SpringApplication(SpringBootTest.class).run(args);
    }
}
