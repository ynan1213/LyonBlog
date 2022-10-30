package com.ynan.feign;

import com.ynan.config.XxxCustomConfiguration;
import com.ynan.config.XxxFallback;
import com.ynan.config.XxxFallbackFactory;
import com.ynan.entity.User;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author yuannan
 * @Date 2021/11/21 11:20
 */
@FeignClient(
	value = "remote-demo",
	/*url = "http://localhost:8002",*/
	/*contextId = "aaa",*/
	path = "/remote",
	configuration = XxxCustomConfiguration.class,
	fallbackFactory = XxxFallbackFactory.class
	/*fallback = XxxFallback.class*/
)
public interface RemoteService {

	@PostMapping(value = "/name")
	User remote(@Valid User user);

}
