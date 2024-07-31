package com.ynan;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MyExtendWith.class)
public class TestMain02 {

    public TestMain02() {
        System.out.println("------ 构造函数 ------");
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("----------- beforeAll ----------------");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("----------- afterAll ----------------");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("------------- beforeEach ----------------");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("------------- afterEach ----------------");
    }

    @Test
    public void test01() {
        System.out.println("--------------- test01 ----------------");
    }

    @Test
    public void test02() {
        System.out.println("--------------- test02 ----------------");
    }

    public static void main(String[] args) {

        ClassSelector classSelector = DiscoverySelectors.selectClass(TestMain02.class);

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
            .request()
            .selectors(classSelector)
            .build();

        Launcher launcher = LauncherFactory.create();
        launcher.execute(request);

        System.out.println("hello world!");
    }

}
