package com.ynan.service;

import com.ynan.entity.User;
import com.ynan.feign.RemoteService;
import feign.Request.Options;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @Author yuannan
 * @Date 2021/11/21 09:31
 */
@Service
public class HelloService {

	@Autowired(required = false)
	private RemoteService service;

	@Autowired
	private ApplicationContext applicationContext;

	public String t1(String str) {
		// 方法一：通过 contextId + "FeignClient" 取bean，这里是alias
		RemoteService service = (RemoteService) applicationContext.getBean("aaaFeignClient");

		// 方法二：通过 qualifier 取bean
		// RemoteService service = (RemoteService)applicationContext.getBean("aaa");
		return service.remote(str, new User("zhangsan", 23, ""), "上海111").toString();

		// 参数可以自定义 Options 对象，实现对单个接口的超时时间配置
		//return service.bbb("str", new Options(112, TimeUnit.SECONDS, 223, TimeUnit.SECONDS, true));
	}

}
