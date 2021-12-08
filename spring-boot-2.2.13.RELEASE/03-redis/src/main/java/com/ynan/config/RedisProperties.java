package com.ynan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuannan
 * @Date 2021/12/8 22:44
 */
@ConfigurationProperties(prefix = "spring.redis")
@Configuration
@Data
public class RedisProperties {

	private Integer database;
	private String host;
	private Integer port;
	private Boolean ssl;
	private String password;
	private Long connectionTimeout;
	private Integer maxActive;
	private Integer maxIdle;
	private Integer minIdle;
	private Integer maxWaitMillis;
}
