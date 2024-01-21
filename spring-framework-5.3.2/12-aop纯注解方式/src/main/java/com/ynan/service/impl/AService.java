package com.ynan.service.impl;

import com.ynan.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AService implements Service {

	@Autowired
	private Service bService;

	@Override
	public void xxx() {

	}
}
