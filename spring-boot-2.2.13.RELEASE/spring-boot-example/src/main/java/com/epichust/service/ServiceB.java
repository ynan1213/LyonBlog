package com.epichust.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @program: spring-boot-build
 * @description:
 * @author: yn
 * @create: 2021-06-24 11:55
 */

@Service("xxxxx")
public class ServiceB
{

	@PostConstruct
	public void print()
	{
		System.out.println("Service B.....");
	}

}
