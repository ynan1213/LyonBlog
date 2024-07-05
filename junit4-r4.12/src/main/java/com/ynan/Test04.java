package com.ynan;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 * JUnitCore#runClasses方法可以接收多个classes类，内部默认会对每个classes创建一个Runner，还会创建一个Suite对象，持有Runner集合
 * Suite也是一个Runner，内部持有的是一个Runner集合
 * Suite: 一套;套
 */
public class Test04 {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestA.class, TestB.class);
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