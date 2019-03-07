package proxyFactoryBean;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class MethodLoggerAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		String name = method.getName();
		System.out.println("method name "+ name +" now is invoke");
	}

}
