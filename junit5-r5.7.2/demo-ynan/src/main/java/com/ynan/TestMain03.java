package com.ynan;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

//@ExtendWith(MyParameterResolver.class)
public class TestMain03 {

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

    @TestTemplate
    @ExtendWith(MyExtendWithForTestTemplate.class)
    public void test03(Integer xxx) {
        System.out.println("--------------- test03 ---------------- :" + xxx);
    }

    @TestTemplate
    @ExtendWith(MyExtendWithForTestTemplateInLoop.class)
    public void test04() {
        System.out.println("--------------- test04 ---------------- ");
    }

    public static void main(String[] args) {

        ClassSelector classSelector = DiscoverySelectors.selectClass(TestMain03.class);

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
            .request()
            .selectors(classSelector)
            .build();

        Launcher launcher = LauncherFactory.create();
        launcher.execute(request);

        System.out.println("hello world!");
    }
}
