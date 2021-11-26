package com.ynan.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author yuannan
 * @Date 2021/11/21 11:20
 */
@FeignClient(value = "remote-demo", contextId = "aaa", path = "/remote")
public interface RemoteService {

	@RequestMapping("/name")
	String remote(@RequestParam String name);
}
