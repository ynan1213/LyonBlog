package com.ynan.advisor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author yuannan
 */
public class LogAdvice implements MethodInterceptor {

	@Nullable
	@Override
	public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
		System.out.println("hello world!!!");
		return invocation.proceed();
	}
}
