package com.ynan.test;

import org.springframework.cloud.context.named.NamedContextFactory;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:18
 */
public class TestSpec implements NamedContextFactory.Specification {

	private final String name;
	private final Class<?>[] configurations;

	public TestSpec(String name, Class<?>[] configurations) {
		this.name = name;
		this.configurations = configurations;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<?>[] getConfiguration() {
		return configurations;
	}
}
