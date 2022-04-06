package com.ynan;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2022/4/1 14:49
 */
@Component
public class CommandLineRunnerDemo implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("sssssssssssssss");
	}
}
