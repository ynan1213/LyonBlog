package com.ynan;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

/**
 * 参数化测试
 */
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestMain05 {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("----------- beforeAll ----------------");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("------------- beforeEach ----------------");
    }

    /**
     * @ValueSource 测试方法只能有一个入参
     */
    @ParameterizedTest
    @ValueSource(ints = {111, 222, 333})
    public void test01(Integer xxx) {
        System.out.println("--------------- test01 ---------------- : " + xxx);
    }

    @ParameterizedTest
    @MethodSource("integerProvider")
    public void test02(Integer xxx) {
        System.out.println("--------------- test02 ---------------- : " + xxx);
    }

    // 方法返回类型必须是 Stream 、Collection、Iterator、数组类型类型，详情见 MethodArgumentsProvider
    public static int[] integerProvider() {
        return new int[]{1111, 2222, 3333};
    }

    public static void main(String[] args) {

        ClassSelector classSelector = DiscoverySelectors.selectClass(TestMain05.class);

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
            .request()
            .selectors(classSelector)
            .build();

        Launcher launcher = LauncherFactory.create();
        launcher.execute(request);

        System.out.println("hello world!");
    }
}
