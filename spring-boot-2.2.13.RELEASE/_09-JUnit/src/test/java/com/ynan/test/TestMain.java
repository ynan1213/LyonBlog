package com.ynan.test;

import com.ynan.service.HelloService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest(classes = RootMain.class)
@SpringBootTest
public class TestMain {

	@Autowired
	private HelloService helloService;

	@BeforeAll
	public static void beforeAll() {
		System.out.println("----------- beforeAll ----------------");
	}

	@BeforeEach
	public void beforeEach() {
		System.out.println("------------- beforeEach ----------------");
	}

	@Test
	public void test01() {
		helloService.say(" test01 ");
	}

	public static void main(String[] args) {
		ClassSelector classSelector = DiscoverySelectors.selectClass(TestMain01.class);

		LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
				.request()
				.selectors(classSelector)
				.build();

		Launcher launcher = LauncherFactory.create();
		launcher.execute(request);

		System.out.println("hello world!");

	}
}
