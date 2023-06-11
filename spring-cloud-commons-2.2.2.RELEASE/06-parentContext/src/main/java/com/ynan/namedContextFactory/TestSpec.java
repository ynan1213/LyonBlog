package com.ynan.namedContextFactory;

import org.springframework.cloud.context.named.NamedContextFactory;

/**
 * @author yuannan
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