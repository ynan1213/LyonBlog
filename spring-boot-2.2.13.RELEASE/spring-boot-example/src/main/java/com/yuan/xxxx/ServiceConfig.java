package com.epichust.xxxx;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig extends ServiceConfigFather
{

	@Bean
	public AService getA()
	{
		return super.getA();
	}

	@Bean
	@ConditionalOnMissingBean
	public BService getB()
	{
		return super.getB();
	}

}
