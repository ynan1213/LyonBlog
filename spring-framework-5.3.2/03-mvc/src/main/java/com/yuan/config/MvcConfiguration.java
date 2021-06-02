package com.epichust.config;

import com.epichust.interceptor.MyInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class MvcConfiguration implements WebMvcConfigurer
{
	/**
	 * 设置是否匹配后缀模式
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer)
	{
		//configurer.setUseSuffixPatternMatch(true);
		//configurer.setUseRegisteredSuffixPatternMatch(true);

		configurer.setUseTrailingSlashMatch(true);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(new MyInterceptor());
	}
}
