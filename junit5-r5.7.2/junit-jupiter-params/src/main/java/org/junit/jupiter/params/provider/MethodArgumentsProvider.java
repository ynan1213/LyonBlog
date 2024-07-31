/*
 * Copyright 2015-2020 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.jupiter.params.provider;

import static java.lang.String.format;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.util.CollectionUtils;
import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.platform.commons.util.StringUtils;

/**
 * @since 5.0
 */
class MethodArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<MethodSource> {

	private String[] methodNames;

	@Override
	public void accept(MethodSource annotation) {
		this.methodNames = annotation.value();
	}

	@Override
	public Stream<Arguments> provideArguments(ExtensionContext context) {
		// 默认情况下返回null，说明@MethodSource指定的方法必须是static
		// 但是当指定了 @TestInstance(TestInstance.Lifecycle.PER_CLASS)，也是可以不为static的
		Object testInstance = context.getTestInstance().orElse(null);
		// @formatter:off
		return Arrays.stream(this.methodNames)
				.map(factoryMethodName -> getMethod(context, factoryMethodName))
				.map(method -> ReflectionUtils.invokeMethod(method, testInstance))
				// 方法返回类型必须是 Stream 、Collection、Iterator、数组类型类型，详情见 MethodArgumentsProvider
				.flatMap(CollectionUtils::toStream)
				.map(MethodArgumentsProvider::toArguments);
		// @formatter:on
	}

	private Method getMethod(ExtensionContext context, String factoryMethodName) {
		if (StringUtils.isNotBlank(factoryMethodName)) {
			if (factoryMethodName.contains("#")) {
				return getMethodByFullyQualifiedName(factoryMethodName);
			}
			else {
				return ReflectionUtils.getRequiredMethod(context.getRequiredTestClass(), factoryMethodName);
			}
		}
		return ReflectionUtils.getRequiredMethod(context.getRequiredTestClass(),
			context.getRequiredTestMethod().getName());
	}

	private Method getMethodByFullyQualifiedName(String fullyQualifiedMethodName) {
		String[] methodParts = ReflectionUtils.parseFullyQualifiedMethodName(fullyQualifiedMethodName);
		String className = methodParts[0];
		String methodName = methodParts[1];
		String methodParameters = methodParts[2];

		Preconditions.condition(StringUtils.isBlank(methodParameters),
			() -> format("factory method [%s] must not declare formal parameters", fullyQualifiedMethodName));

		return ReflectionUtils.getRequiredMethod(loadRequiredClass(className), methodName);
	}

	private Class<?> loadRequiredClass(String className) {
		return ReflectionUtils.tryToLoadClass(className).getOrThrow(
			cause -> new JUnitException(format("Could not load class [%s]", className), cause));
	}

	private static Arguments toArguments(Object item) {

		// Nothing to do except cast.
		if (item instanceof Arguments) {
			return (Arguments) item;
		}

		// Pass all multidimensional arrays "as is", in contrast to Object[].
		// See https://github.com/junit-team/junit5/issues/1665
		if (ReflectionUtils.isMultidimensionalArray(item)) {
			return arguments(item);
		}

		// Special treatment for one-dimensional reference arrays.
		// See https://github.com/junit-team/junit5/issues/1665
		if (item instanceof Object[]) {
			return arguments((Object[]) item);
		}

		// Pass everything else "as is".
		return arguments(item);
	}

}
