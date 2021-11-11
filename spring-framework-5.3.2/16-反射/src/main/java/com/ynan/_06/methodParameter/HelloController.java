package com.ynan._06.methodParameter;

import java.util.Optional;

/**
 * @Author yuannan
 * @Date 2021/10/31 09:51
 */
public class HelloController {

	public String hello(Optional<User> user, String name) {
		return "hello world!";
	}
}
