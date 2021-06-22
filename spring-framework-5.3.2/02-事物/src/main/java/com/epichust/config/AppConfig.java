package com.epichust.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epichust")
@ImportResource("classpath:spring-mybatis.xml")
@EnableTransactionManagement
public class AppConfig
{
	@Bean("transactionManager")
	public TransactionManager getTransactionManager(DataSource dataSource)
	{
		return new DataSourceTransactionManager(dataSource);
	}

}
