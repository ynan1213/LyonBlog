package com.ynan.config;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.reactive.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.reactive.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.reactive.Request;
import org.springframework.cloud.client.loadbalancer.reactive.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

/**
 * @Author yuannan
 * @Date 2022/7/16 21:20
 */
public class MyCustomeLoadBalancer implements ReactorServiceInstanceLoadBalancer {

	private final AtomicInteger position;

	private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

	private final String serviceId;

	public MyCustomeLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
			String serviceId) {
		this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
	}

	public MyCustomeLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
			String serviceId, int seedPosition) {
		this.serviceId = serviceId;
		this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
		this.position = new AtomicInteger(seedPosition);
	}

	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
				.getIfAvailable(NoopServiceInstanceListSupplier::new);
		return supplier.get().next().map(this::getInstanceResponse);
	}

	private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
		if (instances.isEmpty()) {
			return new EmptyResponse();
		}
		int pos = Math.abs(this.position.incrementAndGet());
		if((pos % 3) == 0){
			pos = 2;
		}
		System.out.println("========= " + pos);
		ServiceInstance instance = instances.get(pos % instances.size());

		return new DefaultResponse(instance);
	}
}
