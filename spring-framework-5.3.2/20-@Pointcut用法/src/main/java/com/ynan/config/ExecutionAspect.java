package com.ynan.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2022/5/30 17:40
 */
@Aspect
@Component
public class ExecutionAspect {

	@Pointcut(value = "execution(* com.ynan.service.MyService.*(..))")
	public void xxx() {
	}

	@Before("xxx()")
	public void before(JoinPoint point) {
		System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>> 切面执行 <<<<<<<<<<<<<<<<<<<<<<<");
	}

}
