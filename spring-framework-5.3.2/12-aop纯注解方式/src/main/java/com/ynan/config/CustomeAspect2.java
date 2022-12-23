package com.ynan.config;

import com.ynan.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class CustomeAspect2
{
	@Pointcut(value = "execution(* com.ynan.service.*.*(..))")
	public void xxxx()// 方法名随意
	{
	}

	@Before("xxxx()")
	public void logBefore(JoinPoint point)
	{
		ProceedingJoinPoint p = (ProceedingJoinPoint)point;
		Object[] args = point.getArgs();
		System.out.println("============== before 222 ==============");
	}

	@Around(value = "xxxx()")
	public void logArount(ProceedingJoinPoint point) throws Throwable
	{
		long l1 = System.currentTimeMillis();
		System.out.println("============== around before 222 ==============");
		point.proceed();
		System.out.println("============== around after 222 ==============");
		long l2 = System.currentTimeMillis();
		System.out.println("用时：" + (l2 - l1));
	}

	@After("xxxx()")
	public void logAfter(JoinPoint point)
	{
		Object[] args = point.getArgs();
		System.out.println("============== after 222 ==============");
	}

	@AfterReturning(pointcut = "xxxx()", returning = "user")
	public void logAfterReturning(JoinPoint point, User user)
	{
		Object[] args = point.getArgs();
		System.out.println("============== afterReturning 222 ==============");
	}

	@AfterThrowing(throwing = "ex", pointcut = "xxxx()")
	public void logAfterThrowing(Throwable ex)
	{
		System.out.println("目标方法中抛出的异常:" + ex.getMessage());
	}

}
