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

	/**
	 * 子容器中的bean 在父容器中是没有的
	 */
	//	@Autowired
	//	private XxxFallback fallback;

	@Test
	public void test1() {
		try {
			System.out.println(service.t1("world"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
