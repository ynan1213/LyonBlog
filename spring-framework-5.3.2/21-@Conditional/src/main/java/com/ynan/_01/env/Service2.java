package com.ynan._01.env;

import com.ynan._01.env.EnvProfile.Env;
import org.springframework.stereotype.Component;

/**
 * @author yuannan
 * @date 2022/11/16 09:41
 */
@EnvProfile(Env.DEV)
@Component
public class Service2 {

	public Service2() {
		System.out.println("---------- service2 -----------");
	}
}
