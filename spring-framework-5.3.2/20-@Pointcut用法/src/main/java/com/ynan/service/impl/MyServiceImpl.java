package com.ynan.service.impl;

import com.ynan.service.MyService;
import org.springframework.stereotype.Service;

/**
 * @Author yuannan
 * @Date 2022/5/30 17:59
 */
@Service
public class MyServiceImpl extends MyService {

	@Override
	public void print() {
		System.out.println(" ======================== MyServiceImpl ========================");
	}
}
