/*
 * Copyright 2015-2020 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.jupiter.engine.discovery;

import static org.apiguardian.api.API.Status.INTERNAL;

import org.apiguardian.api.API;
import org.junit.jupiter.engine.descriptor.JupiterEngineDescriptor;
import org.junit.jupiter.engine.discovery.predicates.IsTestClassWithTests;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.support.discovery.EngineDiscoveryRequestResolver;

/**
 * {@code DiscoverySelectorResolver} resolves {@link TestDescriptor TestDescriptors}
 * for containers and tests selected by DiscoverySelectors with the help of the
 * {@code JavaElementsResolver}.
 *
 * <p>This class is the only public entry point into the discovery package.
 *
 * @since 5.0
 */
@API(status = INTERNAL, since = "5.0")
public class DiscoverySelectorResolver {

	// @formatter:off
	private static final EngineDiscoveryRequestResolver<JupiterEngineDescriptor> resolver = EngineDiscoveryRequestResolver.<JupiterEngineDescriptor>builder()
			// IsTestClassWithTests要求：class必须是非private、非abstract、非匿名、如果是内部类必须是静态的
			// 且必须至少有一个 @Test方法 或 @TestFactory方法 或 @TestTemplate方法 或 有一个@Nested注解的非静态内部类
			.addClassContainerSelectorResolver(new IsTestClassWithTests())
			.addSelectorResolver(context -> new ClassSelectorResolver(context.getClassNameFilter(), context.getEngineDescriptor().getConfiguration()))
			.addSelectorResolver(context -> new MethodSelectorResolver(context.getEngineDescriptor().getConfiguration()))
			.addTestDescriptorVisitor(context -> new MethodOrderingVisitor(context.getEngineDescriptor().getConfiguration()))
			.addTestDescriptorVisitor(context -> TestDescriptor::prune)
			.build();
	// @formatter:on

	public void resolveSelectors(EngineDiscoveryRequest request, JupiterEngineDescriptor engineDescriptor) {
		resolver.resolve(request, engineDescriptor);
	}

}
