package com.ynan.config;

import com.ynan.entity.Father;
import com.ynan.entity.Son;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring
 * @description:
 * @author: yn
 * @create: 2021-07-01 23:06
 */
@Configuration
@ComponentScan("com.ynan")
public class RootConfig
{
	@Bean
	@ConditionalOnMissingBean
	public Son getSon()
	{
		return new Son();
	}

	@Bean
	public Son getFather()
	{
		return new Son();
	}
}
