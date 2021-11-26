package com.ynan;

import com.ynan.bean.ConditionImport11;
import com.ynan.bean.ConditionImport22;
import com.ynan.bean.ConditionImport33;
import com.ynan.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * @Author yuannan
 * @Date 2021/11/26 09:51
 */
/**
 * 排在前面的会先被导入，又因为@Bean方法上有@ConditionalOnMissingBean，所以后面的均不会生效了
 */
@Import({ConditionImport22.class, ConditionImport11.class,  ConditionImport33.class})
@SpringBootApplication
public class ImportStartMain {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ImportStartMain.class, args);
		HelloService helloService = context.getBean(HelloService.class);
		helloService.xxx();
	}
}
