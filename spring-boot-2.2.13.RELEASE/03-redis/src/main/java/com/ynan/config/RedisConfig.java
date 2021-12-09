package com.ynan.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import javax.annotation.Resource;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @program: redis-study
 * @description:
 * @author: yn
 * @create: 2021-07-01 21:42
 */
@Configuration
public class RedisConfig {

	/**
	 * spring.redis.host =
	 * spring.redis.port = 6379
	 * spring.redis.password =
	 * spring.redis.database = 3
	 * spring.redis.timeout = 2000
	 * spring.redis.maxAttempts = 6
	 * spring.redis.soTimeout = 10000
	 * spring.redis.connectionTimeout = 2000
	 * spring.redis.maxActive = 5
	 * spring.redis.maxIdle = 5
	 * spring.redis.minIdle = 1
	 * spring.redis.maxWaitMillis = 10000
	 */
	@Resource
	private RedisProperties redisProperties;

	/**
	 * RedisTemplate 有4个序列化工具：
	 * RedisSerializer keySerializer
	 * RedisSerializer valueSerializer
	 * RedisSerializer hashKeySerializer
	 * RedisSerializer hashValueSerializer
	 * 如果不自定的话默认使用的是 JdkSerializationRedisSerializer
	 */
	@Bean
	public RedisTemplate redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

		// 使用Jackson2JsonRedisSerialize 替换默认的Java序列化机制
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		jackson2JsonRedisSerializer.setObjectMapper(jacksonObjectMapper());

		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	private ObjectMapper jacksonObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// 反序列化的时候忽略不认识的属性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 所有属性和方法都可见，无论是private还是public
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		return objectMapper;
	}

	@Bean
	@Primary
	public LettuceConnectionFactory lettuceConnectionFactory(GenericObjectPoolConfig genericObjectPoolConfig) {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase() == null ? 0 : redisProperties.getDatabase());
		redisStandaloneConfiguration.setHostName(redisProperties.getHost());
		redisStandaloneConfiguration.setPort(redisProperties.getPort());
		redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));

		LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
				.commandTimeout(Duration.ofMillis(redisProperties.getConnectionTimeout()))
				.poolConfig(genericObjectPoolConfig)
				.build();
		if (Boolean.valueOf(redisProperties.getSsl())) {
			clientConfig.isUseSsl();
		}
		return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
	}

	@Bean
	public GenericObjectPoolConfig genericObjectPoolConfig() {
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
		genericObjectPoolConfig.setMinIdle(redisProperties.getMinIdle());
		genericObjectPoolConfig.setMaxTotal(redisProperties.getMaxActive());
		genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
		return genericObjectPoolConfig;
	}
}
