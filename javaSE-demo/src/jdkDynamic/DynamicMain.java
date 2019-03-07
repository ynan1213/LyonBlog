package jdkDynamic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicMain {
	public static void main(String[] args) {
		Subject sb = new RealSubject();
		InvocationHandler ih = new DynamicSubject(sb);
		Subject subject =(Subject) Proxy.newProxyInstance(sb.getClass().getClassLoader(), sb.getClass().getInterfaces(), ih);
		
		subject.request();
		
		System.out.println("subject instanceof Proxy:" + (subject instanceof Proxy));
		System.out.println("subject.getClass().getName():" + subject.getClass().getName());
		System.out.print("subject中的属性有：");  
		Field[] field=subject.getClass().getDeclaredFields();  
			for(Field f:field){  
			System.out.print(f.getName()+", ");  
		}
		System.out.print("\n"+"subject中的方法有：");  
		Method[] method=subject.getClass().getDeclaredMethods();  
		for(Method m:method){  
			System.out.print(m.getName()+", ");  
		} 
		System.out.println("\n"+"subject的父类是："+subject.getClass().getSuperclass());  
		System.out.print("\n"+"subject实现的接口是：");  
		Class<?>[] interfaces=subject.getClass().getInterfaces();  
			for(Class<?> i:interfaces){  
			System.out.print(i.getName());  
		}
	}
	
}
