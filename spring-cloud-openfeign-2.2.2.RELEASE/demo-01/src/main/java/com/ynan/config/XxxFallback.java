package com.ynan.config;

import com.ynan.entity.User;
import com.ynan.feign.RemoteService;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2021/11/28 22:23
 */
@Component
public class XxxFallback implements RemoteService {

	@Override
	public User remote(User user) {
		return null;
	}
}
