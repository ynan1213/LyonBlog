package com.ynan.service;

import com.ynan.feign.RemoteService;
import com.ynan.feign.RemoteServiceXxx;
import org.springframework.beans.factory.annotation.Autowired;
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
	private RemoteServiceXxx serviceXxx;

	public String say(String str) {
		return service.remote(str);
	}

}
