package com.ynan.service1;

import com.ynan.client.ClientCommonBean;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:23
 */
public class Service1Bean1 {

	private final ClientCommonBean clientCommonBean;

	Service1Bean1(ClientCommonBean clientCommonBean) {
		this.clientCommonBean = clientCommonBean;
	}

	@Override
	public String toString() {
		return "Service1Bean1{" +
				"clientCommonBean=" + clientCommonBean +
				'}';
	}
}
