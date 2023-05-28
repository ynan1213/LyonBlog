package com.ynan.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuannan
 */
@RestController
public class HelloController {

	@RequestMapping("/hello")
	public User hello(HttpServletResponse response) throws IOException {
		response.addHeader("Content-Type", "application/json");
		if (1==1) throw new RuntimeException("xxxxxxxx");
//		response.setStatus(555);
//		response.sendError(555);

		return new User("xxx", 18);
//		return "hello world!!!";
	}


	public static class User{
		private String name;
		private Integer age;

		public User(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}
	}
}
