package com.ynan.client;

import com.ynan.base.BaseBean;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:22
 */
public class ClientCommonBean {

	private final String name;
	private final BaseBean baseBean;

	ClientCommonBean(String name, BaseBean baseBean) {
		this.name = name;
		this.baseBean = baseBean;
	}

	@Override
	public String toString() {
		return "ClientCommonBean{" +
				"name='" + name + '\'' +
				", baseBean=" + baseBean +
				'}';
	}
}
