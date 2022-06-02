package com.ynan.main;

import com.ynan.config.RootConfig;
import com.ynan.service.MyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author yuannan
 * @Date 2022/5/30 17:38
 */
public class PointCutMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(RootConfig.class);
		ac.refresh();

		MyService service = ac.getBean(MyService.class);
		service.print();
	}
}
