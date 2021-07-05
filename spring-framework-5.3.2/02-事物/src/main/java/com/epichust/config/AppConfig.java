package com.epichust.config;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.ProxyConfig;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epichust")
@ImportResource("classpath:spring-mybatis.xml")
@EnableTransactionManagement
public class AppConfig
{
	@Autowired
	private BeanFactory beanFactory;


	@Bean("transactionManager")
	public TransactionManager getTransactionManager(DataSource dataSource)
	{
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
		dataSourceTransactionManager.setFailEarlyOnGlobalRollbackOnly(true);
		return dataSourceTransactionManager;
	}


	@PostConstruct
	public void init()
	{
		ProxyConfig bean = (ProxyConfig) beanFactory.getBean(AopConfigUtils.AUTO_PROXY_CREATOR_BEAN_NAME);
		if (bean != null)
		{
			bean.setExposeProxy(true);
		}
	}


}
