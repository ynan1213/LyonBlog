package com.ynan._01.env;

import com.ynan._01.env.EnvProfile.Env;
import org.springframework.stereotype.Component;

/**
 * @author yuannan
 * @date 2022/11/16 09:40
 */
@EnvProfile(Env.PROD)
@Component
public class Service1 {

	public Service1() {
		System.out.println("---------- service1 -----------");
	}
}
