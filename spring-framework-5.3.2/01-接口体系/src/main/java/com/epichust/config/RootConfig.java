package com.epichust.config;

import com.epichust.service.UserService;
import com.epichust.service.impl.UserService1;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

// @Configuration注解可不需要
@Configuration("RootConfigxxxx")
@ComponentScan("com.epichust")
//@Conditional(RootConfig.ConditionTest.class)
public class RootConfig
{


	/**
	 * 测试 @Conditional 注解，如果是内部类，必须定义成static
	 */
	public static class ConditionTest implements Condition
	{
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
		{
			return true;
		}
	}

	// @Bean("xxxx")
	// public UserService getUserService()
	// {
	// 	return new UserService1();
	// }

}
