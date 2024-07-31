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

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.IntFunction;

import org.junit.platform.engine.UniqueId;
import org.junit.runner.Description;
import org.junit.vintage.engine.descriptor.RunnerTestDescriptor;
import org.junit.vintage.engine.descriptor.TestSourceProvider;
import org.junit.vintage.engine.descriptor.VintageTestDescriptor;
import org.junit.vintage.engine.support.UniqueIdReader;
import org.junit.vintage.engine.support.UniqueIdStringifier;

/**
 * @since 5.5
 */
class RunnerTestDescriptorPostProcessor {

	// 主要用于读取 Description 的 fUniqueId 字段
	private final UniqueIdReader uniqueIdReader = new UniqueIdReader();
	private final UniqueIdStringifier uniqueIdStringifier = new UniqueIdStringifier();
	private final TestSourceProvider testSourceProvider;

	public RunnerTestDescriptorPostProcessor(TestSourceProvider testSourceProvider) {
		this.testSourceProvider = testSourceProvider;
	}

	void applyFiltersAndCreateDescendants(RunnerTestDescriptor runnerTestDescriptor) {
		addChildrenRecursively(runnerTestDescriptor);
		runnerTestDescriptor.applyFilters(this::addChildrenRecursively);
	}

	private void addChildrenRecursively(VintageTestDescriptor parent) {
		// parent.getDescription()返回的 Description 可以理解为代表一个测试类
		// 它的children可以理解为@Test方法，有多个
		List<Description> children = parent.getDescription().getChildren();

		// Use LinkedHashMap to preserve order, ArrayList for fast access by index
		/**
		 * uniqueIdReader: 读取Description对象的fUniqueId 字段值
		 * uniqueIdStringifier: 将值进行序列化并进行encodeBase64编码，目的是什么？ 这里的应用场景是分组，为什么不直接用equals
		 *
		 * children中的一个元素代表一个@Test方法，按理说转换成Map后是一对一的关系，而不是一对多的关系
		 */
		Map<String, List<Description>> childrenByUniqueId = children.stream().collect(
			groupingBy(uniqueIdReader.andThen(uniqueIdStringifier), LinkedHashMap::new, toCollection(ArrayList::new)));

		for (Entry<String, List<Description>> entry : childrenByUniqueId.entrySet()) {
			String uniqueId = entry.getKey();
			List<Description> childrenWithSameUniqueId = entry.getValue();
			IntFunction<String> uniqueIdGenerator = determineUniqueIdGenerator(uniqueId, childrenWithSameUniqueId);
			for (int index = 0; index < childrenWithSameUniqueId.size(); index++) {
				String reallyUniqueId = uniqueIdGenerator.apply(index);
				Description description = childrenWithSameUniqueId.get(index);
				UniqueId id = parent.getUniqueId().append(VintageTestDescriptor.SEGMENT_TYPE_TEST, reallyUniqueId);
				VintageTestDescriptor child = new VintageTestDescriptor(id, description, testSourceProvider.findTestSource(description));
				parent.addChild(child);
				addChildrenRecursively(child);
			}
		}
	}

	private IntFunction<String> determineUniqueIdGenerator(String uniqueId, List<Description> childrenWithSameUniqueId) {
		if (childrenWithSameUniqueId.size() == 1) {
			return index -> uniqueId;
		}
		return index -> uniqueId + "[" + index + "]";
	}
}
