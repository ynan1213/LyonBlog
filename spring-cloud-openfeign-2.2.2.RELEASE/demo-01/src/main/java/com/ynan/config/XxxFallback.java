package com.ynan.config;

import com.ynan.entity.User;
import com.ynan.feign.RemoteService;
import feign.Response;

/**
 * @Author yuannan
 * @Date 2021/11/28 22:23
 */
public class XxxFallback implements RemoteService {

	@Override
	public String remote(String name, User user, String address) {
		return "xxx";
	}

	@Override
	public Response remote1(String name, User user, String address) {
		return null;
	}

	@Override
	public String aaa(String name, User user) {
		return null;
	}
}
