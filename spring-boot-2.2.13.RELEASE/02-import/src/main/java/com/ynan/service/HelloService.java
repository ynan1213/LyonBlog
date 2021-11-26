package com.ynan.service;

/**
 * @Author yuannan
 * @Date 2021/11/26 09:55
 */
public class HelloService {

	public String str;

	public HelloService(String str) {
		this.str = str;
	}

	public void xxx() {
		System.out.println("hello service : " + str);
	}
}
