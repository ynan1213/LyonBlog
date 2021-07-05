package com.ynan.jdk.dynamic;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkMain {

	public static void main(String[] args) {
		Player p = new RunPlayer();

		Class<?>[] interfaces = p.getClass().getInterfaces();

		Player subject = (Player)Proxy.newProxyInstance(JdkMain.class.getClassLoader(), interfaces, new PlayerInvocationHandler(p));

		subject.play();

		System.out.println("------------------------------------------------------------");
		System.out.println("subject instanceof Proxy:"+(subject instanceof Proxy));
		System.out.println("subject instanceof Player:"+(subject instanceof Player));
		System.out.println("subject instanceof RunPlayer:"+(subject instanceof RunPlayer));
		System.out.println("------------------------------------------------------------");
		System.out.println("subject的Class类是：" + subject.getClass().toString());

		System.out.print("subject中的方法有：");
		Method[] method=subject.getClass().getDeclaredMethods();
		for(Method m:method){  
			System.out.print(m.getName()+", ");  
		}

		System.out.println("\n"+"subject的父类是："+subject.getClass().getSuperclass());
	}

}
