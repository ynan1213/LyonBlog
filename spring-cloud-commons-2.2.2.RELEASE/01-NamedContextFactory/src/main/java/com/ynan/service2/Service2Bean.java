package com.ynan.service2;

import com.ynan.client.ClientCommonBean;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:25
 */
public class Service2Bean {

	private final ClientCommonBean clientCommonBean;

	Service2Bean(ClientCommonBean clientCommonBean) {
		this.clientCommonBean = clientCommonBean;
	}

	@Override
	public String toString() {
		return "Service2Bean{" +
				"clientCommonBean=" + clientCommonBean +
				'}';
	}
}
