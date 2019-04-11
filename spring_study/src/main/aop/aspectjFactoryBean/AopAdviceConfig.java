package aspectjFactoryBean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@EnableAspectJAutoProxy
@Aspect
public class AopAdviceConfig {
	
	@Pointcut("execution(* aspectjFactoryBean.*.*(..))")
	public void pointcut() {
		
	}
	
	@After("pointcut()")
	public void afterAdvice() {
		System.out.println("《《《《《后置通知：after---------------");
	}
	
	
	@Before("pointcut()")
	public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("《《《《《前置通知：before---------------");
    }
	
	@AfterReturning("pointcut()")
	public void afterReturningAdvice(JoinPoint joinPoint) {
        System.out.println("《《《《《后置通知：afterReturning---------------");
    }
	
	@Around("pointcut()")
	public void aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("《《《《《环绕通知：前around---------------");
        joinPoint.proceed();
        System.out.println("《《《《《环绕通知：后around---------------");
    }
	
	@AfterThrowing("pointcut()")
	public void afterThrowingAdvice(JoinPoint joinPoint) {
		System.out.println("《《《《《异常通知：afterThrowing---------------");
	}
}
