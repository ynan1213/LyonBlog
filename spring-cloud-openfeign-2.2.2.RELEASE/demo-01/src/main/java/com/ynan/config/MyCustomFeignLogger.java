package com.ynan.config;

import com.alibaba.fastjson.JSON;
import feign.Logger;
import feign.Request;
import feign.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;

/**
 * @Author yuannan
 * @Date 2022/1/1 20:22
 */
public class MyCustomFeignLogger extends Logger {

	/**
	 * 日志对象缓存，用Feign生成的configKey做为日志的name
	 */
	private static final Map<String, org.slf4j.Logger> loggerHolder = new HashMap<>();

	/**
	 * 出现异常时，通过此ThreadLocal获取请求的Request
	 */
	private static final ThreadLocal<Request> REQUEST_HOLDER = new NamedThreadLocal<>("Feign Request Holder");

	@Override
	protected void log(String configKey, String format, Object... args) {
		System.out.println("configKey: " + configKey + "，format:" + format + "， args:" + args);
		// nothing to do
	}

	@Override
	protected void logRequest(String configKey, Level logLevel, Request request) {
		setContexts(configKey, request);
	}

	@Override
	protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
		String responseText = getResponseText(response);
		onResponse(configKey, response, responseText, elapsedTime);
		clearContexts();
		return response.toBuilder()
			.body(responseText.getBytes())
			.build();
	}

	@Override
	protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {
		onRequestError(configKey, ioe, elapsedTime);
		clearContexts();
		return ioe;
	}

	private void setContexts(String configKey, Request request) {
		REQUEST_HOLDER.set(request);
	}

	private void clearContexts() {
		REQUEST_HOLDER.remove();
	}

	private void onRequestError(String configKey, IOException ioe, long elapsedTime) {
		Request request = REQUEST_HOLDER.get();
		if (request != null) {
			getLogger(configKey).error("\n请求异常！！！: {} {}\n请求头: {}\n请求参数: {}\n异常: {}\n耗时: {} ms",
				request.httpMethod(),
				request.url(),
				JSON.toJSONString(request.headers()),
				request.requestTemplate().queries().toString(),
				ioe.getMessage(),
				elapsedTime
			);
		}
	}

	private void onResponse(String configKey, Response response, String responseText, long elapsedTime) {
		int status = response.status();
		if (status >= 200 && status < 300) {
			onResponseSuccess(configKey, response, responseText, elapsedTime);
		} else {
			onResponseError(configKey, response, responseText, elapsedTime);
		}
	}

	private void onResponseError(String configKey, Response response, String responseText, long elapsedTime) {
		log4Error(configKey, response, responseText, elapsedTime);
	}

	private void onResponseSuccess(String configKey, Response response, String responseText, long elapsedTime) {
		log4Success(configKey, response, responseText, elapsedTime);
	}

	private void log4Success(String configKey, Response response, String responseText, long elapsedTime) {
		Request request = response.request();
		getLogger(configKey).info("\n请求: {} {}\n请求头: {}\n请求参数: {}\n返回码: {}\n返回头: {}\n返回内容: {}\n耗时: {} ms",
			request.httpMethod(),
			request.url(),
			JSON.toJSONString(request.headers()),
			getRequestText(request),
			response.status(),
			JSON.toJSONString(response.headers()),
			responseText,
			elapsedTime
		);
	}

	private org.slf4j.Logger getLogger(String configKey) {
		org.slf4j.Logger logger = loggerHolder.get(configKey);
		if (logger != null) {
			return logger;
		}
		synchronized (MyCustomFeignLogger.class) {
			loggerHolder.putIfAbsent(configKey, LoggerFactory.getLogger("================== com.ynan." + configKey));
		}
		return loggerHolder.get(configKey);
	}

	private String getRequestText(Request request) {
		Request.Body body = Request.Body.create(request.body());
		if (body == null) {
			return null;
		}
		if (body.length() <= 0) {
			return null;
		}
		return body.asString();
	}

	private String getResponseText(Response response) throws IOException {
		Response.Body body = response.body();
		return IOUtils.toString(body.asReader());
	}

	private void log4Error(String configKey, Response response, String responseText, long elapsedTime) {
		Request request = response.request();
		getLogger(configKey).error("\n请求异常！！！: {} {}\n请求头: {}\n请求参数: {}\n返回码: {}\n返回头: {}\n返回内容: {}\n耗时: {} ms",
			request.httpMethod(),
			request.url(),
			JSON.toJSONString(request.headers()),
			getRequestText(request),
			response.status(),
			JSON.toJSONString(response.headers()),
			responseText,
			elapsedTime
		);
	}

}
