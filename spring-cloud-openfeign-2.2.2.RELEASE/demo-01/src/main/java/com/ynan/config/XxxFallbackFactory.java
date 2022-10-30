package com.ynan.config;

import com.ynan.entity.User;
import com.ynan.feign.RemoteService;
import feign.hystrix.FallbackFactory;
import javax.validation.Valid;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2021/11/28 18:04
 */
@Component
public class XxxFallbackFactory implements FallbackFactory<RemoteService> {

	@Override
	public RemoteService create(Throwable cause) {
		return new RemoteService() {
			@Override
			public User remote(@Valid User user) {
				return new User("熔断降级开启", 666, "====================");
			}
		};
	}
}
