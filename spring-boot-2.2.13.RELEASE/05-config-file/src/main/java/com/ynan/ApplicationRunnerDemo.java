package com.ynan;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2022/4/1 14:49
 */
@Component
public class ApplicationRunnerDemo implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("xxxxxx");
	}
}
