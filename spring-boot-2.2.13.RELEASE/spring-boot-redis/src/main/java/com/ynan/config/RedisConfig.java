package com.ynan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @program: redis-study
 * @description:
 * @author: yn
 * @create: 2021-07-01 21:42
 */
@Configuration
public class RedisConfig
{
	/**
	 * RedisTemplate 有4个序列化工具：
	 * 		RedisSerializer keySerializer
	 * 		RedisSerializer valueSerializer
	 * 		RedisSerializer hashKeySerializer
	 * 		RedisSerializer hashValueSerializer
	 * 如果不自定的话默认使用的是 JdkSerializationRedisSerializer
	 */
	@Bean
	public RedisTemplate getRedisTemplate(RedisConnectionFactory redisConnectionFactory)
	{
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		// 开启事务
		redisTemplate.setEnableTransactionSupport(true);
		// 使用string序列化缓存键
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		return redisTemplate;
	}
}
