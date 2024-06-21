package com.ynan;

import org.junit.runner.*;
import org.junit.runner.notification.RunNotifier;

/**
 * 以@RunWith指定的class作为Runner
 *
 * 注意：@RunWith指定的Runner构造函数要求：
 * 1.必须要有一个class作为参数的构造函数，初始化时AnnotatedBuilder会传入测试类
 * 2.如果1不满足，则会找两个参数的构造函数，第二个参数类型是RunnerBuilder
 * 详情见 org.junit.internal.builders.AnnotatedBuilder#buildRunner(java.lang.Class, java.lang.Class)
 */
@RunWith(Test02.MyRunWithRunner.class)
public class Test02 {

    public static void main(String[] args) {
        Request request = Request.aClass(Test02.class);
        Runner runner = request.getRunner();

        JUnitCore jUnitCore = new JUnitCore();
        jUnitCore.run(runner);

    }


    public static class MyRunWithRunner extends Runner {

        private Class tClass;
        public MyRunWithRunner(Class<?> klass) {
            this.tClass = klass;
            System.out.println(" MyRunWithRunner construct ............... ");
        }

        @Override
        public void run(RunNotifier notifier) {
            System.out.println(" MyRunWithRunner run ............... ");
        }

        @Override
        public int testCount() {
            System.out.println(" MyRunWithRunner testCount ............... ");
            return 0;
        }

        @Override
        public Description getDescription() {
            System.out.println(" MyRunWithRunner getDescription ............... ");
            return Description.createSuiteDescription("example");
        }

    }
}