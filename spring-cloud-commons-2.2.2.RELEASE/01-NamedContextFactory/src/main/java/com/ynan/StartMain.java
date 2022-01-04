package com.ynan;

import com.sun.tools.javac.util.List;
import com.ynan.base.BaseBean;
import com.ynan.base.BaseConfig;
import com.ynan.client.ClientCommonBean;
import com.ynan.client.ClientCommonConfig;
import com.ynan.service1.Service1Config1;
import com.ynan.service1.Service1Config2;
import com.ynan.service2.Service2Config;
import com.ynan.test.TestClient;
import com.ynan.test.TestSpec;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author yuannan
 * @Date 2021/11/21 22:04
 */
public class StartMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext parent = new AnnotationConfigApplicationContext();
		// 父Context 的配置类 BaseBean
		parent.register(BaseConfig.class);
		parent.refresh();

		// defaultConfigType 会指向 ClientCommonConfig，这样所有的子Context都会使用
		TestClient testClient1 = new TestClient(ClientCommonConfig.class);
		testClient1.setApplicationContext(parent);

		TestSpec testSpec1 = new TestSpec("service1", new Class[]{Service1Config1.class, Service1Config2.class});
		TestSpec testSpec2 = new TestSpec("service2", new Class[]{Service2Config.class});

		// 将 service1 与 service2 的配置加入 testClient1
		testClient1.setConfigurations(List.of(testSpec1, testSpec2));

		// BaseBean 在父Context中
		BaseBean baseBean1 = testClient1.getInstance("service1", BaseBean.class);
		BaseBean baseBean2 = testClient1.getInstance("service2", BaseBean.class);
		System.out.println(baseBean1 == baseBean2);

		// ClientCommonConfig 被保存在 defaultConfigType中，会被每一个子context注入，所以这里不相同
		ClientCommonBean commonBean1 = testClient1.getInstance("service1", ClientCommonBean.class);
		ClientCommonBean commonBean2 = testClient1.getInstance("service2", ClientCommonBean.class);
		System.out.println(commonBean1 == commonBean2);

	}

}
