package com.ynan.test;

import com.ynan.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author yuannan
 * @Date 2021/11/21 09:32
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StartTest {

	@Autowired
	private HelloService service;

	@Test
	public void test1() {
		for (int i = 0; i < 6; i++) {
			System.out.println(service.say("world"));
		}
	}
}
