/*
 * Copyright 2015-2020 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.platform.launcher.core;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;

import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.engine.TestEngine;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.PostDiscoveryFilter;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

/**
 * Default implementation of the {@link Launcher} API.
 *
 * <p>External clients can obtain an instance by invoking
 * {@link LauncherFactory#create()}.
 *
 * @since 1.0
 * @see Launcher
 * @see LauncherFactory
 */
class DefaultLauncher implements Launcher {

	private final TestExecutionListenerRegistry listenerRegistry = new TestExecutionListenerRegistry();

	/**
	 * orchestrator: 编排器
	 */
	private final EngineExecutionOrchestrator executionOrchestrator = new EngineExecutionOrchestrator(listenerRegistry);
	private final EngineDiscoveryOrchestrator discoveryOrchestrator;

	/**
	 * Construct a new {@code DefaultLauncher} with the supplied test engines.
	 *
	 * @param testEngines the test engines to delegate to; never {@code null} or empty
	 * @param filters the additional post discovery filters for discovery requests; never {@code null}
	 */
	DefaultLauncher(Iterable<TestEngine> testEngines, Collection<PostDiscoveryFilter> filters) {
		Preconditions.condition(testEngines != null && testEngines.iterator().hasNext(),
			() -> "Cannot create Launcher without at least one TestEngine; "
					+ "consider adding an engine implementation JAR to the classpath");
		Preconditions.notNull(filters, "PostDiscoveryFilter array must not be null");
		Preconditions.containsNoNullElements(filters, "PostDiscoveryFilter array must not contain null elements");
		this.discoveryOrchestrator = new EngineDiscoveryOrchestrator(EngineIdValidator.validate(testEngines),
			unmodifiableCollection(filters));
	}

	@Override
	public void registerTestExecutionListeners(TestExecutionListener... listeners) {
		Preconditions.notEmpty(listeners, "listeners array must not be null or empty");
		Preconditions.containsNoNullElements(listeners, "individual listeners must not be null");
		this.listenerRegistry.registerListeners(listeners);
	}

	@Override
	public TestPlan discover(LauncherDiscoveryRequest discoveryRequest) {
		Preconditions.notNull(discoveryRequest, "LauncherDiscoveryRequest must not be null");
		LauncherDiscoveryResult discoveryResult = discover(discoveryRequest, "discovery");
		return InternalTestPlan.from(discoveryResult);
	}

	@Override
	public void execute(LauncherDiscoveryRequest discoveryRequest, TestExecutionListener... listeners) {
		Preconditions.notNull(discoveryRequest, "LauncherDiscoveryRequest must not be null");
		Preconditions.notNull(listeners, "TestExecutionListener array must not be null");
		Preconditions.containsNoNullElements(listeners, "individual listeners must not be null");
		LauncherDiscoveryResult discoveryResult = discover(discoveryRequest, "execution");
		/**
		 * 一个测试类的解析和包装：
		 *  1.JUnit4是将测试类包装成Description，Description有一个children集合，一个children对应一个@Test方法；
		 *  2.VintageDiscoverer是包装成 RunnerTestDescriptor(对应一个测试类)和VintageTestDescriptor(对应一个@Test方法)）；
		 *  3.到这里，是将TestDescriptor解析成TestIdentifier，以层级关系存储在InternalTestPlan中
		 */
		InternalTestPlan testPlan = InternalTestPlan.from(discoveryResult);
		execute(testPlan, listeners);
	}

	@Override
	public void execute(TestPlan testPlan, TestExecutionListener... listeners) {
		Preconditions.notNull(testPlan, "TestPlan must not be null");
		Preconditions.condition(testPlan instanceof InternalTestPlan, "TestPlan was not returned by this Launcher");
		Preconditions.notNull(listeners, "TestExecutionListener array must not be null");
		Preconditions.containsNoNullElements(listeners, "individual listeners must not be null");
		execute((InternalTestPlan) testPlan, listeners);
	}

	TestExecutionListenerRegistry getTestExecutionListenerRegistry() {
		return listenerRegistry;
	}

	private LauncherDiscoveryResult discover(LauncherDiscoveryRequest discoveryRequest, String phase) {
		return discoveryOrchestrator.discover(discoveryRequest, phase);
	}

	private void execute(InternalTestPlan internalTestPlan, TestExecutionListener[] listeners) {
		executionOrchestrator.execute(internalTestPlan, listeners);
	}

}
