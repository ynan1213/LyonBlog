package com.ynan.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @Author yuannan
 * @Date 2022/7/16 21:28
 */
public class XxxConfig {

	@Bean
	private MyCustomeLoadBalancer myCustomeLoadBalancer(Environment environment,
			LoadBalancerClientFactory loadBalancerClientFactory) {
		String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
		ObjectProvider<ServiceInstanceListSupplier> lazyProvider = loadBalancerClientFactory
				.getLazyProvider(name, ServiceInstanceListSupplier.class);
		return new MyCustomeLoadBalancer(lazyProvider, name);
	}
}
