package com.ynan;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

/**
 * 重复执行 @RepeatedTest
 */
public class TestMain04 {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("----------- beforeAll ----------------");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("------------- beforeEach ----------------");
    }

    @RepeatedTest(3)
    public void test01() {
        System.out.println("--------------- test01 ----------------");
    }

    public static void main(String[] args) {

        ClassSelector classSelector = DiscoverySelectors.selectClass(TestMain04.class);

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
            .request()
            .selectors(classSelector)
            .build();

        Launcher launcher = LauncherFactory.create();
        launcher.execute(request);

        System.out.println("hello world!");
    }
}
