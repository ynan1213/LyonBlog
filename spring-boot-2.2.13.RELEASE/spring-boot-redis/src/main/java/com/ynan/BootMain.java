package com.ynan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: redis-study
 * @description:
 * @author: yn
 * @create: 2021-07-01 21:30
 */
@SpringBootApplication
public class BootMain
{
	public static void main(String[] args)
	{
		SpringApplication.run(BootMain.class, args);
	}

}
