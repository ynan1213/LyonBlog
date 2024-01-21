package com.ynan.test;

import com.ynan.controller.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author yuannan
 * @Date 2021/11/17 22:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class HelloTest {

	@Autowired
	private HelloController helloController;


	@Test
	public void test1(){
//		System.out.println(helloController.hello());
	}
}
