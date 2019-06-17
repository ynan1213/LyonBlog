package com.sqsoft.test;

import org.springframework.beans.BeanWrapperImpl;

public class TestMain {
	
	public static void main(String[] args) {
		Dog d = new Dog();
		System.out.println(d);
		BeanWrapperImpl bw = new BeanWrapperImpl(d);
		bw.setPropertyValue("sex", "1");
		System.out.println(d);
	}
	
}
