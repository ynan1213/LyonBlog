package com.ynan.test;

import org.springframework.cloud.context.named.NamedContextFactory;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:17
 */
public class TestClient extends NamedContextFactory<TestSpec> {

	private static final String PROPERTY_NAME = "test.context.name";

	public TestClient(Class<?> defaultConfigType) {
		super(defaultConfigType, "testClient", PROPERTY_NAME);
	}
}
