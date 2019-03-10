package com.sqsoft.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
	
	@Pointcut("execution(* com.sqsoft..*.*(..))")
	public void log() {
		
	}
	
	@Before("log()")
	public void doBefore(JoinPoint joinpoint) {
		System.out.println("切面方法----执行前输出----方法名称："+joinpoint.getSignature().getName());
	}
	
	@AfterReturning("log()")
	public void doAfter(JoinPoint joinpoint) {
		System.out.println("切面方法----执行后输出----方法名称："+joinpoint.getSignature().getName());
	}
}
