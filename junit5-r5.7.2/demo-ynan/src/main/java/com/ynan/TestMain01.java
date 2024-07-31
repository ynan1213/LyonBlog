package com.ynan;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

public class TestMain01 {

    @BeforeClass
    public static void beforeClass() {
        System.out.println("--------------- beforeClass ----------------");
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("--------------- beforeAll ----------------");
    }

    @Before
    public void before() {
        System.out.println("--------------- before ----------------");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("--------------- beforeEach ----------------");
    }

    @Test
    @org.junit.Test
    public void test01() {
        System.out.println("--------------- test01 ----------------");
    }

    @Test
    @org.junit.Test
    public void test02() {
        System.out.println("--------------- test02 ----------------");
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
