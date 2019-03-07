package com.sqsoft.use.xml;

public class AspectTest {

	public void before(String s1, String s2) {
		System.out.println("执行前置通知：" + s1 + "-" + s2);
	}
}
