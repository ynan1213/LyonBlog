package com.ynan.advice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author yuannan
 * @Date 2021/12/27 14:32
 */
public class IServiceProxy implements InvocationHandler {

	private Object target;

	public IServiceProxy(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(target, args);
	}
}
