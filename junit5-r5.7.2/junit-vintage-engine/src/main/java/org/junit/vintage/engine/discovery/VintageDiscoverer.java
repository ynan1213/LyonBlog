/*
 * Copyright 2015-2020 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.vintage.engine.discovery;

import static org.apiguardian.api.API.Status.INTERNAL;

import org.apiguardian.api.API;
import org.junit.platform.commons.util.ClassFilter;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.discovery.EngineDiscoveryRequestResolver;
import org.junit.vintage.engine.descriptor.RunnerTestDescriptor;
import org.junit.vintage.engine.descriptor.TestSourceProvider;
import org.junit.vintage.engine.descriptor.VintageEngineDescriptor;

/**
 * @since 4.12
 */
@API(status = INTERNAL, since = "4.12")
public class VintageDiscoverer {

	/**
	 * Potential: 潜在的;可能性
	 * 用于校验Class是否是测试类，必须满足以下条件：
	 * 1.public访问权限；
	 * 2.非abstract；
	 * 3.非内部类；
	 */
	private static final IsPotentialJUnit4TestClass isPotentialJUnit4TestClass = new IsPotentialJUnit4TestClass();

	// @formatter:off
	private static final EngineDiscoveryRequestResolver<TestDescriptor> resolver = EngineDiscoveryRequestResolver.builder()
		    // 处理 ClasspathRootSelector、PackageSelector 类型
			.addClassContainerSelectorResolver(isPotentialJUnit4TestClass)
			// 处理 ClassSelector 类型
			.addSelectorResolver(context -> new ClassSelectorResolver(ClassFilter.of(context.getClassNameFilter(), isPotentialJUnit4TestClass)))
		    // 处理 MethodSelector 类型
			.addSelectorResolver(new MethodSelectorResolver())
			.build();
	// @formatter:on

	public VintageEngineDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId uniqueId) {
		TestSourceProvider testSourceProvider = new TestSourceProvider();

		/**
		 * VintageEngineDescriptor相当于root，经过后面的 EngineDiscoveryRequestResolver#resolve处理，
		 * 会将测试类解析成 RunnerTestDescriptor，并添加到root的children列表中
		 */
		VintageEngineDescriptor engineDescriptor = new VintageEngineDescriptor(uniqueId, testSourceProvider);
		/**
		 *  VintageDiscoverer预设了三个 SelectorResolver:
		 * 	  1.ClassContainerSelectorResolver: 处理 ClasspathRootSelector、PackageSelector 类型
		 * 	  2.ClassSelectorResolver: 处理 ClassSelector 类型
		 * 	  3.MethodSelectorResolver: 处理 MethodSelector 类型
		 * 	以2为例，将测试类解析成Runner（junit4的逻辑），然后包装成 RunnerTestDescriptor，并添加到 VintageEngineDescriptor 的children中
		 */
		resolver.resolve(discoveryRequest, engineDescriptor);
		RunnerTestDescriptorPostProcessor postProcessor = new RunnerTestDescriptorPostProcessor(testSourceProvider);
		// @formatter:off
		// engineDescriptor.getChildren().stream()
		//		.filter(RunnerTestDescriptor.class::isInstance)
		//		.map(RunnerTestDescriptor.class::cast)
		//		.forEach(postProcessor::applyFiltersAndCreateDescendants);
		/**
		 * 上面的不好debug，重写后如下，方便debug
		 * 经过上面的resolve处理，一个测试类被包装成 RunnerTestDescriptor
		 * 一个RunnerTestDescriptor持有一个Description，Description表示一个测试类的描述，属于JUnit4的概念，Description也有一个children集合，一个children对应一个@Test方法
		 *
		 * TestDescriptor才是Junit5的概念，遍历Description的children集合，每一个@Test方法包装成VintageTestDescriptor，添加到RunnerTestDescriptor的children集合中
		 */
		for (TestDescriptor child : engineDescriptor.getChildren()) {
			if (child instanceof RunnerTestDescriptor) {
				RunnerTestDescriptor runnerTestDescriptor = (RunnerTestDescriptor) child;
				postProcessor.applyFiltersAndCreateDescendants(runnerTestDescriptor);
			}
		}

		// @formatter:on
		return engineDescriptor;
	}

}
