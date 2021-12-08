package com.ynan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @program: spring-boot-build
 * @description:
 * @author: yn
 * @create: 2021-07-01 22:41
 */
@RestController
public class RedisController
{
	/**
	 * 注意：
	 * 1. RedisAutoConfiguration向容器中注入了一个 StringRedisTemplate 和 RedisTemplate；
	 * 2. 如果自定义了一个@Bean RedisTemplate类型，一般是不会对1中的有影响，因为1中有 @ConditionalOnMissingBean(name = "redisTemplate")
	 * 3. 也就是说这里会有 3个类型匹配，但是为什么没有报错呢？ 注入的又是哪一个呢？
	 * 4. 其实，在Spring高版本（具体哪个版本未知）开始，@Autowired如果有多个类型匹配，会取字段名再次匹配，匹配不到再抛异常；
	 * 5. 也就是说先发现了3个类型匹配的，再根据name=redisTemplate去找，这样就找打了
	 * 6. 如果将字段改成 xxTemplate，会立即抛异常：有三个匹配的bean
	 */
	@Autowired
	private RedisTemplate redisTemplate;

	@RequestMapping("/hello")
	public String hello()
	{
		redisTemplate.opsForValue().setIfAbsent("k1", "v1", 10, TimeUnit.SECONDS);

		redisTemplate.opsForValue().set("k1", "v1");
		redisTemplate.opsForList().leftPush("l1", "v1");
		redisTemplate.opsForHash().put("h1", "hh1", "vv1");
		redisTemplate.opsForZSet().add("z1", "v1", 2);

		redisTemplate.opsForValue().set("k2", "v2");
		System.out.println(redisTemplate.opsForValue().get("k1"));
		return redisTemplate.opsForValue().get("k1").toString();
	}

}
