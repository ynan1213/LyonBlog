/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.openfeign.ribbon;

import java.io.IOException;
import java.net.URI;

import com.netflix.client.ClientException;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import feign.Client;
import feign.Request;
import feign.Response;

import org.springframework.cloud.netflix.ribbon.SpringClientFactory;

/**
 * @author Dave Syer
 *
 */
public class LoadBalancerFeignClient implements Client {

	// 默认的选项：connectTimeout=10，readTimeout=60
	static final Request.Options DEFAULT_OPTIONS = new Request.Options();

	private final Client delegate;

	private CachingSpringLoadBalancerFactory lbClientFactory;

	private SpringClientFactory clientFactory;

	public LoadBalancerFeignClient(Client delegate, CachingSpringLoadBalancerFactory lbClientFactory, SpringClientFactory clientFactory) {
		this.delegate = delegate;
		this.lbClientFactory = lbClientFactory;
		this.clientFactory = clientFactory;
	}

	static URI cleanUrl(String originalUrl, String host) {
		String newUrl = originalUrl;
		if (originalUrl.startsWith("https://")) {
			newUrl = originalUrl.substring(0, 8) + originalUrl.substring(8 + host.length());
		}
		else if (originalUrl.startsWith("http")) {
			newUrl = originalUrl.substring(0, 7) + originalUrl.substring(7 + host.length());
		}
		StringBuffer buffer = new StringBuffer(newUrl);
		if ((newUrl.startsWith("https://") && newUrl.length() == 8) || (newUrl.startsWith("http://") && newUrl.length() == 7)) {
			buffer.append("/");
		}
		return URI.create(buffer.toString());
	}

	@Override
	public Response execute(Request request, Request.Options options) throws IOException {
		try {
			URI asUri = URI.create(request.url());
			String clientName = asUri.getHost();
			URI uriWithoutHost = cleanUrl(request.url(), clientName);
			FeignLoadBalancer.RibbonRequest ribbonRequest = new FeignLoadBalancer.RibbonRequest(this.delegate, request, uriWithoutHost);
			// 关注点：如果未配置超时时间，这里并不是默认的60s，而是ribbon的默认配置
			IClientConfig requestConfig = getClientConfig(options, clientName);
			FeignLoadBalancer feignLoadBalancer = lbClient(clientName);
			return feignLoadBalancer.executeWithLoadBalancer(ribbonRequest, requestConfig).toResponse();
		}
		catch (ClientException e) {
			IOException io = findIOException(e);
			if (io != null) {
				throw io;
			}
			throw new RuntimeException(e);
		}
	}

	IClientConfig getClientConfig(Request.Options options, String clientName) {
		IClientConfig requestConfig;
		if (options == DEFAULT_OPTIONS) {
			// 如果是默认的，并未取默认的数值，通过debug，里面取的是ribbon的默认值（有个父子容器），连接和读取均是 1s，具体要看ribbon的源码
			// clientFactory 是一个 NamedContextFactory
			// 这里取的是ribbon内部的配置bean，详情见ribbon文档
			// 配置方式是
			//  	全局生效：ribbon.ConnectTimeout=2222
			// 		xxx服务生效：xxx.ribbon.ConnectTimeout=3333
			requestConfig = this.clientFactory.getClientConfig(clientName);
		}
		else {
			// 自定义在这里生效
			// 没有交给spring管理，直接new的，貌似就只有ConnectTimeout和ReadTimeout两个配置
			requestConfig = new FeignOptionsClientConfig(options);
		}
		return requestConfig;
	}

	protected IOException findIOException(Throwable t) {
		if (t == null) {
			return null;
		}
		if (t instanceof IOException) {
			return (IOException) t;
		}
		return findIOException(t.getCause());
	}

	public Client getDelegate() {
		return this.delegate;
	}

	private FeignLoadBalancer lbClient(String clientName) {
		return this.lbClientFactory.create(clientName);
	}

	static class FeignOptionsClientConfig extends DefaultClientConfigImpl {

		FeignOptionsClientConfig(Request.Options options) {
			setProperty(CommonClientConfigKey.ConnectTimeout, options.connectTimeoutMillis());
			setProperty(CommonClientConfigKey.ReadTimeout, options.readTimeoutMillis());
		}

		@Override
		public void loadProperties(String clientName) {

		}

		@Override
		public void loadDefaultValues() {

		}

	}

}
