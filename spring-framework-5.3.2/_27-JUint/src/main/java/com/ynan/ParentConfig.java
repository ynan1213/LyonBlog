package com.ynan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParentConfig {

	@Bean
	public PrintService createPrintService() {
		return new PrintService("parentConfig");
	}

}
