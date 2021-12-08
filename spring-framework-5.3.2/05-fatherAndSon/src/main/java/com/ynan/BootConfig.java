package com.ynan;

import com.ynan.fatherAndSon.Father;
import com.ynan.fatherAndSon.Son;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @Author yuannan
 * @Date 2021/12/8 22:07
 */
@Configuration
public class BootConfig {

	@Bean
	@ConditionalOnMissingBean
	public Father father() {
		return new Father();
	}

	@Bean
	@ConditionalOnMissingBean
	public Son son(){
		return new Son();
	}

}
