/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.binding;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.BinderFactory;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.binder.ExtendedPropertiesBinder;
import org.springframework.cloud.stream.binder.PollableConsumerBinder;
import org.springframework.cloud.stream.binder.PollableSource;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

/**
 * Handles binding of input/output targets by delegating to an underlying {@link Binder}.
 *
 * @author Mark Fisher
 * @author Dave Syer
 * @author Marius Bogoevici
 * @author Ilayaperumal Gopinathan
 * @author Gary Russell
 * @author Janne Valkealahti
 * @author Soby Chacko
 */
public class BindingService {

	private final CustomValidatorBean validator;

	private final Log log = LogFactory.getLog(BindingService.class);

	private final BindingServiceProperties bindingServiceProperties;

	private final Map<String, Binding<?>> producerBindings = new HashMap<>();

	private final Map<String, List<Binding<?>>> consumerBindings = new HashMap<>();

	private final TaskScheduler taskScheduler;

	private final BinderFactory binderFactory;

	public BindingService(BindingServiceProperties bindingServiceProperties, BinderFactory binderFactory) {
		this(bindingServiceProperties, binderFactory, null);
	}

	public BindingService(BindingServiceProperties bindingServiceProperties, BinderFactory binderFactory, TaskScheduler taskScheduler) {
		this.bindingServiceProperties = bindingServiceProperties;
		this.binderFactory = binderFactory;
		this.validator = new CustomValidatorBean();
		this.validator.afterPropertiesSet();
		this.taskScheduler = taskScheduler;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Collection<Binding<T>> bindConsumer(T input, String inputName) {
		Collection<Binding<T>> bindings = new ArrayList<>();
		Binder<T, ConsumerProperties, ?> binder = (Binder<T, ConsumerProperties, ?>) getBinder(inputName, input.getClass());
		ConsumerProperties consumerProperties = this.bindingServiceProperties.getConsumerProperties(inputName);
		if (binder instanceof ExtendedPropertiesBinder) {
			Object extension = ((ExtendedPropertiesBinder) binder).getExtendedConsumerProperties(inputName);
			ExtendedConsumerProperties extendedConsumerProperties = new ExtendedConsumerProperties(extension);
			BeanUtils.copyProperties(consumerProperties, extendedConsumerProperties);
			consumerProperties = extendedConsumerProperties;
		}

		validate(consumerProperties);

		String destination = this.bindingServiceProperties.getBindingDestination(inputName);

		if (consumerProperties.isMultiplex()) {
			bindings.add(doBindConsumer(input, inputName, binder, consumerProperties, destination));
		}
		else {
			String[] destinations = StringUtils.commaDelimitedListToStringArray(destination);
			for (String target : destinations) {
				Binding<T> binding = input instanceof PollableSource
						? doBindPollableConsumer(input, inputName, binder, consumerProperties, target)
						: doBindConsumer(input, inputName, binder, consumerProperties, target);
				bindings.add(binding);
			}
		}
		bindings = Collections.unmodifiableCollection(bindings);
		this.consumerBindings.put(inputName, new ArrayList<>(bindings));
		return bindings;
	}

	public <T> Binding<T> doBindConsumer(T input, String inputName, Binder<T, ConsumerProperties, ?> binder, ConsumerProperties consumerProperties, String target) {
		if (this.taskScheduler == null || this.bindingServiceProperties.getBindingRetryInterval() <= 0) {
			return binder.bindConsumer(target, this.bindingServiceProperties.getGroup(inputName), input, consumerProperties);
		}
		else {
			try {
				return binder.bindConsumer(target, this.bindingServiceProperties.getGroup(inputName), input, consumerProperties);
			}
			catch (RuntimeException e) {
				LateBinding<T> late = new LateBinding<T>();
				rescheduleConsumerBinding(input, inputName, binder, consumerProperties, target, late, e);
				return late;
			}
		}
	}

	public <T> void rescheduleConsumerBinding(final T input, final String inputName,
			final Binder<T, ConsumerProperties, ?> binder, final ConsumerProperties consumerProperties,
			final String target, final LateBinding<T> late, RuntimeException exception) {
		assertNotIllegalException(exception);
		this.log.error("Failed to create consumer binding; retrying in " + this.bindingServiceProperties.getBindingRetryInterval() + " seconds", exception);
		this.scheduleTask(() -> {
			try {
				late.setDelegate(binder.bindConsumer(target, this.bindingServiceProperties.getGroup(inputName), input, consumerProperties));
			}
			catch (RuntimeException e) {
				rescheduleConsumerBinding(input, inputName, binder, consumerProperties, target, late, e);
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> Binding<T> doBindPollableConsumer(T input, String inputName, Binder<T, ConsumerProperties, ?> binder, ConsumerProperties consumerProperties, String target) {
		if (this.taskScheduler == null || this.bindingServiceProperties.getBindingRetryInterval() <= 0) {
			return ((PollableConsumerBinder) binder).bindPollableConsumer(target, this.bindingServiceProperties.getGroup(inputName), (PollableSource) input, consumerProperties);
		}
		else {
			try {
				return ((PollableConsumerBinder) binder).bindPollableConsumer(target, this.bindingServiceProperties.getGroup(inputName), (PollableSource) input, consumerProperties);
			}
			catch (RuntimeException e) {
				LateBinding<T> late = new LateBinding<T>();
				reschedulePollableConsumerBinding(input, inputName, binder, consumerProperties, target, late, e);
				return late;
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> void reschedulePollableConsumerBinding(final T input, final String inputName,
			final Binder<T, ConsumerProperties, ?> binder, final ConsumerProperties consumerProperties,
			final String target, final LateBinding<T> late, RuntimeException exception) {
		assertNotIllegalException(exception);
		this.log.error("Failed to create consumer binding; retrying in " + this.bindingServiceProperties.getBindingRetryInterval() + " seconds", exception);
		this.scheduleTask(() -> {
			try {
				late.setDelegate(((PollableConsumerBinder) binder)
					.bindPollableConsumer(target, this.bindingServiceProperties.getGroup(inputName), (PollableSource) input, consumerProperties));
			}
			catch (RuntimeException e) {
				reschedulePollableConsumerBinding(input, inputName, binder, consumerProperties, target, late, e);
			}
		});
	}

	/**
	 * 绑定@Output注解的方法
	 * @param output 为方法的返回值，MessageChannel类型
	 * @param outputName 为@Output注解的value值
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Binding<T> bindProducer(T output, String outputName) {
		// 获取spring.cloud.stream.bindings.xxx.destination 配置的值，如果没有配置，默认就是outputName
		String destination = this.bindingServiceProperties.getBindingDestination(outputName);
		// 获取Binder，通常由第三方提供
		Binder<T, ?, ProducerProperties> binder = (Binder<T, ?, ProducerProperties>) getBinder(outputName, output.getClass());
		// 获取 spring.cloud.stream.bindings.xxx-output.producer下面一系列的值
		ProducerProperties producerProperties = this.bindingServiceProperties.getProducerProperties(outputName);
		if (binder instanceof ExtendedPropertiesBinder) {
			/**
			 * 这里获取到的是这种类型，以rabbitmq为例：
			 * 	spring.cloud.stream.rabbit.bindings.xxx-output.producer.batchSize=123
			 * 	spring.cloud.stream.rabbit.bindings.xxx-output.producer.batchTimeout=4444
			 * 	spring.cloud.stream.rabbit.bindings.xxx-output.producer.transacted=false
			 */
			Object extension = ((ExtendedPropertiesBinder) binder).getExtendedProducerProperties(outputName);
			ExtendedProducerProperties extendedProducerProperties = new ExtendedProducerProperties<>(extension);
			BeanUtils.copyProperties(producerProperties, extendedProducerProperties);
			producerProperties = extendedProducerProperties;
		}
		// 校验配置的参数是否合法
		validate(producerProperties);
		Binding<T> binding = doBindProducer(output, destination, binder, producerProperties);
		this.producerBindings.put(outputName, binding);
		return binding;
	}

	@SuppressWarnings("rawtypes")
	public Object getExtendedProducerProperties(Object output, String outputName) {
		Binder binder =  getBinder(outputName, output.getClass());
		if (binder instanceof ExtendedPropertiesBinder) {
			return ((ExtendedPropertiesBinder) binder).getExtendedProducerProperties(outputName);
		}
		return null;
	}

	public <T> Binding<T> doBindProducer(T output, String destination, Binder<T, ?, ProducerProperties> binder, ProducerProperties producerProperties) {
		if (this.taskScheduler == null || this.bindingServiceProperties.getBindingRetryInterval() <= 0) {
			return binder.bindProducer(destination, output, producerProperties);
		} else {
			try {
				return binder.bindProducer(destination, output, producerProperties);
			} catch (RuntimeException e) {
				LateBinding<T> late = new LateBinding<T>();
				// 抛异常可以稍后重试
				rescheduleProducerBinding(output, destination, binder, producerProperties, late, e);
				return late;
			}
		}
	}

	public <T> void rescheduleProducerBinding(
		final T output,
		final String bindingTarget,
		final Binder<T, ?, ProducerProperties> binder,
		final ProducerProperties producerProperties,
		final LateBinding<T> late,
		final RuntimeException exception) {
		assertNotIllegalException(exception);
		this.log.error("Failed to create producer binding; retrying in "
					+ this.bindingServiceProperties.getBindingRetryInterval() + " seconds", exception);
		this.scheduleTask(() -> {
			try {
				late.setDelegate(binder.bindProducer(bindingTarget, output, producerProperties));
			} catch (RuntimeException e) {
				rescheduleProducerBinding(output, bindingTarget, binder, producerProperties, late, e);
			}
		});
	}

	public void unbindConsumers(String inputName) {
		List<Binding<?>> bindings = this.consumerBindings.remove(inputName);
		if (bindings != null && !CollectionUtils.isEmpty(bindings)) {
			for (Binding<?> binding : bindings) {
				binding.unbind();
			}
		}
		else if (log.isWarnEnabled()) {
			log.warn("Trying to unbind '" + inputName + "', but no binding found.");
		}
	}

	public void unbindProducers(String outputName) {
		Binding<?> binding = this.producerBindings.remove(outputName);
		if (binding != null) {
			binding.unbind();
		}
		else if (log.isWarnEnabled()) {
			log.warn("Trying to unbind '" + outputName + "', but no binding found.");
		}
	}

	/**
	 * Provided for backwards compatibility. Will be removed in a future version.
	 *
	 * @return {@link BindingServiceProperties}
	 */
	@Deprecated
	public BindingServiceProperties getChannelBindingServiceProperties() {
		return this.bindingServiceProperties;
	}

	public BindingServiceProperties getBindingServiceProperties() {
		return this.bindingServiceProperties;
	}

	protected <T> Binder<T, ?, ?> getBinder(String channelName, Class<T> bindableType) {
	    // 获取spring.cloud.stream.bindings.xxx.binder配置的值，如果未配置，这里就为null
		String binderConfigurationName = this.bindingServiceProperties.getBinder(channelName);
		return binderFactory.getBinder(binderConfigurationName, bindableType);
	}

	// 校验配置参数的合法性
	private void validate(Object properties) {
		DataBinder dataBinder = new DataBinder(properties);
		dataBinder.setValidator(validator);
		dataBinder.validate();
		if (dataBinder.getBindingResult().hasErrors()) {
			throw new IllegalStateException(dataBinder.getBindingResult().toString());
		}
	}

	private void scheduleTask(Runnable task) {
		this.taskScheduler.schedule(task, new Date(System.currentTimeMillis() + this.bindingServiceProperties.getBindingRetryInterval() * 1_000));
	}

	private void assertNotIllegalException(RuntimeException exception) throws RuntimeException {
		if (exception instanceof IllegalStateException || exception instanceof IllegalArgumentException) {
			throw exception;
		}
	}

	private static class LateBinding<T> implements Binding<T> {

		private volatile Binding<T> delegate;

		private volatile boolean unbound;

		LateBinding() {
			super();
		}

		public synchronized void setDelegate(Binding<T> delegate) {
			if (this.unbound) {
				delegate.unbind();
			}
			else {
				this.delegate = delegate;
			}
		}

		@Override
		public synchronized void unbind() {
			this.unbound = true;
			if (this.delegate != null) {
				this.delegate.unbind();
			}
		}

		@Override
		public String toString() {
			return "LateBinding [delegate=" + this.delegate + "]";
		}

	}

}
