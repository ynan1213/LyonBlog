package com.ynan;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 对于Test04，需要在JUnitCore.runClasses方法中传入所有的Test才行
 * 也可以利用@RunWith(Suite.class)和@SuiteClasses批量运行
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({Test05.TestB.class, Test05.TestC.class})
public class Test05 {

    public static void main(String[] args) {
        // 只需要传入Test05即可
        Result result = JUnitCore.runClasses(Test05.class);
    }

    /**
     * Suite上的类的@Test会被忽略
     */
    @Test
    public void test() {
        System.out.println(" Test05 test ...............");
    }


    public static class TestA {
        @Test
        public void test() {
            System.out.println(" TestA test ...............");
        }
    }

    public static class TestB {
        @Test
        public void test() {
            System.out.println(" TestB test ...............");
        }
    }

    public static class TestC {
        @Test
        public void test() {
            System.out.println(" TestC test ...............");
        }
    }
}