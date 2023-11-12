package com.ynan.cglib;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;


public class CglibMain {

	public static void main(String[] args) {
		//System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Process.class);
		enhancer.setCallback(new ProcessProxy());

		Process subject = (Process) enhancer.create();
		subject.doFirst();

		System.out.println("subject的Class类是：" + subject.getClass().toString());
		System.out.print("subject中的方法有：");
		Method[] method = subject.getClass().getDeclaredMethods();
		for (Method m : method) {
			System.out.print(m.getName() + ", ");
		}

		System.out.println("\n" + "subject的父类是：" + subject.getClass().getSuperclass());

	}
}
