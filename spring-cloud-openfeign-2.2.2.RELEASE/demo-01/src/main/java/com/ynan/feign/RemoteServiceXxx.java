package com.ynan.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author yuannan
 * @Date 2021/11/22 19:31
 */
//@FeignClient("Xxx-Remote")
@FeignClient(value = "remote-demo", contextId = "bbb", path = "/remote")
public interface RemoteServiceXxx {

	@RequestMapping("/name")
	String remote(@RequestParam String name);
}
