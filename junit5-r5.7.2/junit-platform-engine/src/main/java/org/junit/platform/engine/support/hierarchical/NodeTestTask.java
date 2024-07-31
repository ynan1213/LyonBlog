/*
 * Copyright 2015-2020 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.platform.engine.support.hierarchical;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toCollection;
import static org.junit.platform.engine.TestExecutionResult.failed;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.platform.commons.util.ExceptionUtils;
import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.commons.util.UnrecoverableExceptions;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutorService.TestTask;
import org.junit.platform.engine.support.hierarchical.Node.DynamicTestExecutor;
import org.junit.platform.engine.support.hierarchical.Node.ExecutionMode;
import org.junit.platform.engine.support.hierarchical.Node.SkipResult;

/**
 * @since 1.3
 */
class NodeTestTask<C extends EngineExecutionContext> implements TestTask {

	private static final Logger logger = LoggerFactory.getLogger(NodeTestTask.class);

	private final NodeTestTaskContext taskContext;
	private final TestDescriptor testDescriptor;
	private final Node<C> node;

	private C parentContext;
	private C context;

	private SkipResult skipResult;
	private boolean started;
	private ThrowableCollector throwableCollector;

	NodeTestTask(NodeTestTaskContext taskContext, TestDescriptor testDescriptor) {
		this.taskContext = taskContext;
		this.testDescriptor = testDescriptor;
		this.node = NodeUtils.asNode(testDescriptor);
	}

	@Override
	public ResourceLock getResourceLock() {
		return taskContext.getExecutionAdvisor().getResourceLock(testDescriptor);
	}

	@Override
	public ExecutionMode getExecutionMode() {
		return taskContext.getExecutionAdvisor().getForcedExecutionMode(testDescriptor).orElse(node.getExecutionMode());
	}

	void setParentContext(C parentContext) {
		this.parentContext = parentContext;
	}

	@Override
	public void execute() {
		try {
			throwableCollector = taskContext.getThrowableCollectorFactory().create();
			/**
			 * 一、对于Root JupiterEngineDescriptor 包装成的 NodeTestTask 的逻辑：
			 *
			 * 二、对于测试类对应的 ClassTestDescriptor 包装成的 NodeTestTask 的逻辑：
			 *  1.读取类上的 @ExtendWith 注解;
			 *  2.读取带有 @RegisterExtension 注解的字段，字段类型必须是Extension类型
			 *  3.检索并包装@BeforeAll、@AfterAll、@BeforeEach、@AfterEach方法
			 *
			 * 三、对于@Test方法对应的 TestMethodTestDescriptor 包装成的 NodeTestTask 的逻辑：
			 *  1.读取方法上 @ExtendWith 注解；
			 *  2.实例化测试类对象，和@TestInstance有关；
			 *
			 * 四、对于@TestTemplate方法对应的 TestTemplateTestDescriptor 包装成的 NodeTestTask 的逻辑：
			 *  1.读取方法上 @ExtendWith 注解；
			 */
			prepare();
			if (throwableCollector.isEmpty()) {
				checkWhetherSkipped();
			}
			if (throwableCollector.isEmpty() && !skipResult.isSkipped()) {
				executeRecursively();
			}
			if (context != null) {
				cleanUp();
			}
			reportCompletion();
		}
		finally {
			// Ensure that the 'interrupted status' flag for the current thread
			// is cleared for reuse of the thread in subsequent task executions.
			// See https://github.com/junit-team/junit5/issues/1688
			if (Thread.interrupted()) {
				logger.debug(() -> String.format(
					"Execution of TestDescriptor with display name [%s] "
							+ "and unique ID [%s] failed to clear the 'interrupted status' flag for the "
							+ "current thread. JUnit has cleared the flag, but you may wish to investigate "
							+ "why the flag was not cleared by user code.",
					this.testDescriptor.getDisplayName(), this.testDescriptor.getUniqueId()));
			}
		}

		// Clear reference to context to allow it to be garbage collected.
		// See https://github.com/junit-team/junit5/issues/1578
		context = null;
	}

	private void prepare() {
		throwableCollector.execute(() -> context = node.prepare(parentContext));

		// Clear reference to parent context to allow it to be garbage collected.
		// See https://github.com/junit-team/junit5/issues/1578
		parentContext = null;
	}

	private void checkWhetherSkipped() {
		throwableCollector.execute(() -> skipResult = node.shouldBeSkipped(context));
	}

	private void executeRecursively() {
		taskContext.getListener().executionStarted(testDescriptor);
		started = true;

		throwableCollector.execute(() -> {
			node.around(context, ctx -> {
				context = ctx;
				throwableCollector.execute(() -> {
					// @formatter:off
					List<NodeTestTask<C>> children = testDescriptor.getChildren().stream()
							.map(descriptor -> new NodeTestTask<C>(taskContext, descriptor))
							.collect(toCollection(ArrayList::new));
					// @formatter:on
					/**
					 * 一、对于Root JupiterEngineDescriptor 包装成的 NodeTestTask 的逻辑：
					 *
					 * 二、对于测试类对应的 ClassTestDescriptor 包装成的 NodeTestTask 的逻辑：
					 *  1.如果测试类是 @TestInstance(Lifecycle.PER_CLASS)，也就是每个测试类只实例化一次，则执行实例化
					 *  2.执行BeforeAllCallback类型的Extension，注意不是@BeforeAll方法，系统预设的有 @TempDir 和 @Timeout 注解；
					 *  3.执行@BeforeAll注解的方法；
					 *
					 * 三、对于@Test方法对应的 TestMethodTestDescriptor 包装成的 NodeTestTask 的逻辑：
					 *
					 * 四、对于@TestTemplate方法对应的 TestTemplateTestDescriptor 包装成的 NodeTestTask 的逻辑：
					 */
					context = node.before(context);

					final DynamicTestExecutor dynamicTestExecutor = new DefaultDynamicTestExecutor();
					/**
					 * 一、对于Root JupiterEngineDescriptor 包装成的 NodeTestTask 的逻辑：
					 *
					 * 二、对于测试类对应的 ClassTestDescriptor 包装成的 NodeTestTask 的逻辑：
					 *
					 * 三、对于@Test方法对应的 TestMethodTestDescriptor 包装成的 NodeTestTask 的逻辑：
					 *  1.执行 BeforeEachCallback 类型的Extension，注意不是@BeforeEach方法，系统预设的有 @TempDir 和 @Timeout 注解；
					 *  2.执行 @BeforeEach 注解的方法；
					 *  3.执行 BeforeTestExecutionCallback 类型的Extension，系统没有预设；
					 *  4.执行 @Test 方法；
					 *  5.执行 AfterTestExecutionCallback 类型的Extension，系统没有预设；
					 *  6.执行 @AfterEach 注解的方法；
					 *  7.执行 AfterEachCallback 类型的Extension，注意不是@BeforeEach方法，系统预设的有 @TempDir 和 @Timeout 注解；
					 *
					 * 四、对于@TestTemplate方法对应的 TestTemplateTestDescriptor 包装成的 NodeTestTask 的逻辑：
					 *  1.
					 */
					context = node.execute(context, dynamicTestExecutor);

					if (!children.isEmpty()) {
						children.forEach(child -> child.setParentContext(context));
						taskContext.getExecutorService().invokeAll(children);
					}

					throwableCollector.execute(dynamicTestExecutor::awaitFinished);
				});

				throwableCollector.execute(() -> node.after(context));
			});
		});
	}

	private void cleanUp() {
		throwableCollector.execute(() -> node.cleanUp(context));
	}

	private void reportCompletion() {
		if (throwableCollector.isEmpty() && skipResult.isSkipped()) {
			try {
				node.nodeSkipped(context, testDescriptor, skipResult);
			}
			catch (Throwable throwable) {
				UnrecoverableExceptions.rethrowIfUnrecoverable(throwable);
				logger.debug(throwable,
					() -> String.format("Failed to invoke nodeSkipped() on Node %s", testDescriptor.getUniqueId()));
			}
			taskContext.getListener().executionSkipped(testDescriptor, skipResult.getReason().orElse("<unknown>"));
			return;
		}
		if (!started) {
			// Call executionStarted first to comply with the contract of EngineExecutionListener.
			taskContext.getListener().executionStarted(testDescriptor);
		}
		try {
			node.nodeFinished(context, testDescriptor, throwableCollector.toTestExecutionResult());
		}
		catch (Throwable throwable) {
			UnrecoverableExceptions.rethrowIfUnrecoverable(throwable);
			logger.debug(throwable,
				() -> String.format("Failed to invoke nodeFinished() on Node %s", testDescriptor.getUniqueId()));
		}
		taskContext.getListener().executionFinished(testDescriptor, throwableCollector.toTestExecutionResult());
		throwableCollector = null;
	}

	private class DefaultDynamicTestExecutor implements DynamicTestExecutor {
		private final List<Future<?>> futures = new ArrayList<>();

		@Override
		public void execute(TestDescriptor testDescriptor) {
			execute(testDescriptor, taskContext.getListener());
		}

		@Override
		public Future<?> execute(TestDescriptor testDescriptor, EngineExecutionListener executionListener) {
			Preconditions.notNull(testDescriptor, "testDescriptor must not be null");
			Preconditions.notNull(executionListener, "executionListener must not be null");

			executionListener.dynamicTestRegistered(testDescriptor);
			Set<ExclusiveResource> exclusiveResources = NodeUtils.asNode(testDescriptor).getExclusiveResources();
			if (!exclusiveResources.isEmpty()) {
				executionListener.executionStarted(testDescriptor);
				String message = "Dynamic test descriptors must not declare exclusive resources: " + exclusiveResources;
				executionListener.executionFinished(testDescriptor, failed(new JUnitException(message)));
				return completedFuture(null);
			}
			else {
				NodeTestTask<C> nodeTestTask = new NodeTestTask<>(taskContext.withListener(executionListener),
					testDescriptor);
				nodeTestTask.setParentContext(context);
				Future<?> future = taskContext.getExecutorService().submit(nodeTestTask);
				futures.add(future);
				return future;
			}
		}

		@Override
		public void awaitFinished() throws InterruptedException {
			for (Future<?> future : futures) {
				try {
					future.get();
				}
				catch (CancellationException ignore) {
					// Futures returned by execute() may have been cancelled
				}
				catch (ExecutionException e) {
					ExceptionUtils.throwAsUncheckedException(e.getCause());
				}
			}
		}
	}

}
