package com.ynan.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2021/12/27 12:05
 */
@Component
@Aspect
public class MyAspect {

	@Pointcut(value = "execution(* com.ynan.service.*.*(..))")
	public void xxxx() {
	}

	@Before("xxxx()")
	public void logBefore(JoinPoint point) {
		ProceedingJoinPoint p = (ProceedingJoinPoint) point;
		Object[] args = point.getArgs();
		System.out.println("============== before ==============");
	}
}
