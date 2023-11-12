package com.ynan.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@Configuration
@ComponentScan("com.ynan")
@EnableAspectJAutoProxy(exposeProxy = true)
//@EnableTransactionManagement
public class RootConfig extends FatherClass
{

}

class FatherClass {
	public void sayXxx() {

	}
}
