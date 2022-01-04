package com.ynan.config;

import feign.Logger;
import feign.Logger.Level;
import feign.Request.Options;
import feign.Retryer;
import java.util.concurrent.TimeUnit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @Author yuannan
 * @Date 2021/11/22 19:26
 */
//@Configuration(proxyBeanMethods = false)
public class XxxDefaultConfiguration {

	public XxxDefaultConfiguration(ApplicationContext context) {
		System.out.println("全局配置 XxxDefaultConfiguration 初始化中 ..... 会被每一个client子容器初始化，当前容器：" + context.getDisplayName());
	}

	@Bean
	public Level getLoggerLevel() {
		return Level.FULL;
	}

	@Bean
	public Logger getLogger() {
		return new MyCustomFeignLogger();
	}

//	@Bean
//	public Options getOptions() {
//		return new Options(5, TimeUnit.SECONDS, 5, TimeUnit.SECONDS, true);
//	}

	/**
	 * openfeign默认是不重试的
	 */
	@Bean
	public Retryer feignRetryer() {
		// period=100 发起当前请求的时间间隔,单位毫秒
		// maxPeriod=1000 发起当前请求的最大时间间隔,单位毫秒
		// maxAttempts=2 重试次数是1，因为包括第一次，所以我们如果想要重试2次，就需要设置为3
		Retryer retryer = new Retryer.Default(100, 1000, 3);
		return retryer;
	}

	@Bean
	public XxxFallback xxx() {
		return new XxxFallback();
	}


}
