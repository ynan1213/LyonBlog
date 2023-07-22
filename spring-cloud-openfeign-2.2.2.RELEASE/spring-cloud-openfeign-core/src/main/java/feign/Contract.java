/**
 * Copyright 2012-2020 The Feign Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package feign;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;

import feign.Request.HttpMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Defines what annotations and values are valid on interfaces.
 */
public interface Contract {

	/**
	 * Called to parse the methods in the class that are linked to HTTP requests.
	 *
	 * @param targetType {@link Target#type() type} of the Feign interface.
	 */
	List<MethodMetadata> parseAndValidateMetadata(Class<?> targetType);

	abstract class BaseContract implements Contract {

		/**
		 * @param targetType {@link Target#type() type} of the Feign interface.
		 * @see #parseAndValidateMetadata(Class)
		 */
		@Override
		public List<MethodMetadata> parseAndValidateMetadata(Class<?> targetType) {
			// 1、类上不能存在任何一个泛型变量
			checkState(targetType.getTypeParameters().length == 0, "Parameterized types unsupported: %s",
				targetType.getSimpleName());
			// 2、接口最多只能有一个父接口
			checkState(targetType.getInterfaces().length <= 1, "Only single inheritance supported: %s",
				targetType.getSimpleName());
			if (targetType.getInterfaces().length == 1) {
				checkState(targetType.getInterfaces()[0].getInterfaces().length == 0,
					"Only single-level inheritance supported: %s",
					targetType.getSimpleName());
			}

			// 对该类所有的方法进行解析：包装成一个MethodMetadata
			// getMethods表示本类 + 父类的public方法，因为是接口，所有肯定都是public的（当然Java8支持private、default、static等）
			Map<String, MethodMetadata> result = new LinkedHashMap<String, MethodMetadata>();
			for (Method method : targetType.getMethods()) {
				// 排除掉Object的方法、static方法、default方法等
				if (method.getDeclaringClass() == Object.class || (method.getModifiers() & Modifier.STATIC) != 0 || Util.isDefault(method)) {
					continue;
				}
				MethodMetadata metadata = parseAndValidateMetadata(targetType, method);
				checkState(!result.containsKey(metadata.configKey()), "Overrides unsupported: %s", metadata.configKey());
				// 请注意这个key是：metadata.configKey()
				result.put(metadata.configKey(), metadata);
			}
			// 注意：这里并没有把result直接返回回去，而是返回一个快照版本
			return new ArrayList<>(result.values());
		}

		/**
		 * @deprecated use {@link #parseAndValidateMetadata(Class, Method)} instead.
		 */
		@Deprecated
		public MethodMetadata parseAndValidateMetadata(Method method) {
			return parseAndValidateMetadata(method.getDeclaringClass(), method);
		}

		/**
		 * Called indirectly by {@link #parseAndValidateMetadata(Class)}.
		 */
		protected MethodMetadata parseAndValidateMetadata(Class<?> targetType, Method method) {
			MethodMetadata data = new MethodMetadata();
			data.targetType(targetType);
			data.method(method);
			// 方法返回类型是支持泛型的
			data.returnType(Types.resolve(targetType, targetType, method.getGenericReturnType()));
			// 这里使用了Feign的一个工具方法，来生成configKey，就是尽量唯一
			data.configKey(Feign.configKey(targetType, method));

			// 处理接口上的注解。并且处理了父接口哦
			// 这就是为何你父接口上的注解，子接口里也生效的原因哦~~~
			// processAnnotationOnClass()是个abstract方法，交给子类去实现（毕竟注解是可以扩展的嘛）
			if (targetType.getInterfaces().length == 1) {
				processAnnotationOnClass(data, targetType.getInterfaces()[0]);
			}
			processAnnotationOnClass(data, targetType);

			// 处理标注在方法上的所有注解
			// 若子接口override了父接口的方法，注解请以子接口的为主，忽略父接口方法
			for (Annotation methodAnnotation : method.getAnnotations()) {
				processAnnotationOnMethod(data, methodAnnotation, method);
			}
			if (data.isIgnored()) {
				return data;
			}

			// 简单的说：处理完方法上的注解后，必须已经知道到底是GET or POST 或者其它了
			checkState(data.template().method() != null, "Method %s not annotated with HTTP method type (ex. GET, POST)", data.configKey());

			// 方法参数，支持泛型类型的。如List<String>这种...
			Class<?>[] parameterTypes = method.getParameterTypes();
			Type[] genericParameterTypes = method.getGenericParameterTypes();
			Annotation[][] parameterAnnotations = method.getParameterAnnotations();
			int count = parameterAnnotations.length;

			// 一个注解一个注解的处理
			for (int i = 0; i < count; i++) {
				boolean isHttpAnnotation = false;
				if (parameterAnnotations[i] != null) {
					isHttpAnnotation = processAnnotationsOnParameter(data, parameterAnnotations[i], i);
				}

				if (isHttpAnnotation) {
					data.ignoreParamater(i);
				}

				// 方法参数若存在URI类型的参数，那url就以它为准，并不使用全局的了
				if (parameterTypes[i] == URI.class) {
					data.urlIndex(i);
				} else if (!isHttpAnnotation && parameterTypes[i] != Request.Options.class) {
					if (data.isAlreadyProcessed(i)) {
						checkState(data.formParams().isEmpty() || data.bodyIndex() == null,
							"Body parameters cannot be used with form parameters.");
					} else {
						checkState(data.formParams().isEmpty(),
							"Body parameters cannot be used with form parameters.");
						checkState(data.bodyIndex() == null, "Method has too many Body parameters: %s", method);
						data.bodyIndex(i);
						data.bodyType(Types.resolve(targetType, targetType, genericParameterTypes[i]));
					}
				}
			}

			if (data.headerMapIndex() != null) {
				checkMapString("HeaderMap", parameterTypes[data.headerMapIndex()],
					genericParameterTypes[data.headerMapIndex()]);
			}

			if (data.queryMapIndex() != null) {
				if (Map.class.isAssignableFrom(parameterTypes[data.queryMapIndex()])) {
					checkMapKeys("QueryMap", genericParameterTypes[data.queryMapIndex()]);
				}
			}

			return data;
		}

		private static void checkMapString(String name, Class<?> type, Type genericType) {
			checkState(Map.class.isAssignableFrom(type),
				"%s parameter must be a Map: %s", name, type);
			checkMapKeys(name, genericType);
		}

		private static void checkMapKeys(String name, Type genericType) {
			Class<?> keyClass = null;

			// assume our type parameterized
			if (ParameterizedType.class.isAssignableFrom(genericType.getClass())) {
				Type[] parameterTypes = ((ParameterizedType) genericType).getActualTypeArguments();
				keyClass = (Class<?>) parameterTypes[0];
			} else if (genericType instanceof Class<?>) {
				// raw class, type parameters cannot be inferred directly, but we can scan any extended
				// interfaces looking for any explict types
				Type[] interfaces = ((Class) genericType).getGenericInterfaces();
				if (interfaces != null) {
					for (Type extended : interfaces) {
						if (ParameterizedType.class.isAssignableFrom(extended.getClass())) {
							// use the first extended interface we find.
							Type[] parameterTypes = ((ParameterizedType) extended).getActualTypeArguments();
							keyClass = (Class<?>) parameterTypes[0];
							break;
						}
					}
				}
			}

			if (keyClass != null) {
				checkState(String.class.equals(keyClass),
					"%s key must be a String: %s", name, keyClass.getSimpleName());
			}
		}

		/**
		 * Called by parseAndValidateMetadata twice, first on the declaring class, then on the target
		 * type (unless they are the same).
		 *
		 * @param data metadata collected so far relating to the current java method.
		 * @param clz the class to process
		 */
		protected abstract void processAnnotationOnClass(MethodMetadata data, Class<?> clz);

		/**
		 * @param data metadata collected so far relating to the current java method.
		 * @param annotation annotations present on the current method annotation.
		 * @param method method currently being processed.
		 */
		protected abstract void processAnnotationOnMethod(MethodMetadata data,
			Annotation annotation,
			Method method);

		/**
		 * @param data metadata collected so far relating to the current java method.
		 * @param annotations annotations present on the current parameter annotation.
		 * @param paramIndex if you find a name in {@code annotations}, call
		 * {@link #nameParam(MethodMetadata, String, int)} with this as the last parameter.
		 * @return true if you called {@link #nameParam(MethodMetadata, String, int)} after finding an
		 * http-relevant annotation.
		 */
		protected abstract boolean processAnnotationsOnParameter(MethodMetadata data,
			Annotation[] annotations,
			int paramIndex);

		/**
		 * links a parameter name to its index in the method signature.
		 */
		protected void nameParam(MethodMetadata data, String name, int i) {
			Collection<String> names =
				data.indexToName().containsKey(i) ? data.indexToName().get(i) : new ArrayList<String>();
			names.add(name);
			data.indexToName().put(i, names);
		}
	}

	class Default extends DeclarativeContract {

		static final Pattern REQUEST_LINE_PATTERN = Pattern.compile("^([A-Z]+)[ ]*(.*)$");

		public Default() {
			super.registerClassAnnotation(Headers.class, (header, data) -> {
				String[] headersOnType = header.value();
				checkState(headersOnType.length > 0, "Headers annotation was empty on type %s.",
					data.configKey());
				Map<String, Collection<String>> headers = toMap(headersOnType);
				headers.putAll(data.template().headers());
				data.template().headers(null); // to clear
				data.template().headers(headers);
			});
			super.registerMethodAnnotation(RequestLine.class, (ann, data) -> {
				String requestLine = ann.value();
				checkState(emptyToNull(requestLine) != null,
					"RequestLine annotation was empty on method %s.", data.configKey());

				Matcher requestLineMatcher = REQUEST_LINE_PATTERN.matcher(requestLine);
				if (!requestLineMatcher.find()) {
					throw new IllegalStateException(String.format(
						"RequestLine annotation didn't start with an HTTP verb on method %s",
						data.configKey()));
				} else {
					data.template().method(HttpMethod.valueOf(requestLineMatcher.group(1)));
					data.template().uri(requestLineMatcher.group(2));
				}
				data.template().decodeSlash(ann.decodeSlash());
				data.template()
					.collectionFormat(ann.collectionFormat());
			});
			super.registerMethodAnnotation(Body.class, (ann, data) -> {
				String body = ann.value();
				checkState(emptyToNull(body) != null, "Body annotation was empty on method %s.",
					data.configKey());
				if (body.indexOf('{') == -1) {
					data.template().body(body);
				} else {
					data.template().bodyTemplate(body);
				}
			});
			super.registerMethodAnnotation(Headers.class, (header, data) -> {
				String[] headersOnMethod = header.value();
				checkState(headersOnMethod.length > 0, "Headers annotation was empty on method %s.",
					data.configKey());
				data.template().headers(toMap(headersOnMethod));
			});
			super.registerParameterAnnotation(Param.class, (paramAnnotation, data, paramIndex) -> {
				String name = paramAnnotation.value();
				checkState(emptyToNull(name) != null, "Param annotation was empty on param %s.",
					paramIndex);
				nameParam(data, name, paramIndex);
				Class<? extends Param.Expander> expander = paramAnnotation.expander();
				if (expander != Param.ToStringExpander.class) {
					data.indexToExpanderClass().put(paramIndex, expander);
				}
				if (!data.template().hasRequestVariable(name)) {
					data.formParams().add(name);
				}
			});
			super.registerParameterAnnotation(QueryMap.class, (queryMap, data, paramIndex) -> {
				checkState(data.queryMapIndex() == null,
					"QueryMap annotation was present on multiple parameters.");
				data.queryMapIndex(paramIndex);
				data.queryMapEncoded(queryMap.encoded());
			});
			super.registerParameterAnnotation(HeaderMap.class, (queryMap, data, paramIndex) -> {
				checkState(data.headerMapIndex() == null,
					"HeaderMap annotation was present on multiple parameters.");
				data.headerMapIndex(paramIndex);
			});
		}

		private static Map<String, Collection<String>> toMap(String[] input) {
			Map<String, Collection<String>> result =
				new LinkedHashMap<String, Collection<String>>(input.length);
			for (String header : input) {
				int colon = header.indexOf(':');
				String name = header.substring(0, colon);
				if (!result.containsKey(name)) {
					result.put(name, new ArrayList<String>(1));
				}
				result.get(name).add(header.substring(colon + 1).trim());
			}
			return result;
		}

	}
}
