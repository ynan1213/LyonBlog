package com.ynan._03.annotatedElementUtils.autoproxy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopAdviceConfig
{

	@Pointcut(value = "execution(* aspectjFactoryBean.*.*(..))")
	public void pointcut()
	{
	}

	@AfterThrowing(value = "pointcut()", throwing = "xxx")
	public Object afterThrowing(JoinPoint pjp, FileNotException xxx) throws Throwable
	{
		System.out.println("后置抛出方法----------------");
		return "aaaa";
	}

}
