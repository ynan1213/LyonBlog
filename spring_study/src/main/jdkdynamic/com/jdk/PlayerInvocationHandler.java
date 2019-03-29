package com.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PlayerInvocationHandler implements InvocationHandler{
	
	private Player p;
	
	public PlayerInvocationHandler(Player p) {
		this.p = p;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("aop之前");
		method.invoke(p, args);
		System.out.println("aop之后");
		return null;
	}

}
