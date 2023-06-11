package com.ynan.namedContextFactory;

import org.springframework.cloud.context.named.NamedContextFactory;

/**
 * @author yuannan
 */
public class TestClient extends NamedContextFactory<TestSpec> {

	private static final String PROPERTY_NAME = "test.context.name";

	public TestClient(Class<?> defaultConfigType) {
		super(defaultConfigType, "testClient", PROPERTY_NAME);
	}
}