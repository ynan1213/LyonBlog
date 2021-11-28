package com.ynan.service;

import com.ynan.entity.User;
import com.ynan.feign.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @Author yuannan
 * @Date 2021/11/21 09:31
 */
@Service
public class HelloService {

	@Autowired
	private RemoteService service;

	@Autowired
	private ApplicationContext applicationContext;

	//	@Autowired
	//	private RemoteServiceXxx serviceXxx;

	public String say(String str) {
		return service.remote(str, new User("xxx", 18), "上海");
	}


	public String t1(String str) {
		// 方法一：通过 contextId + "FeignClient" 取bean
		RemoteService service = (RemoteService) applicationContext.getBean("aaaFeignClient");

		// 方法二：通过 qualifier 取bean
		// RemoteService service = (RemoteService)applicationContext.getBean("aaa");
		return service.remote(str, new User("xxx", 18), "上海111");
	}

}
