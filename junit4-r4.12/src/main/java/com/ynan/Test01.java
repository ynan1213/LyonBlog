package com.ynan;

import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.validator.ValidateWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 * JUnitCore   JUnit4最开始启动的地方
 * RunnerBuilder  描述如何构建一组Runner
 * BlockJUnit4ClassRunner JUnit4的默认Runner，继承自ParentRunner
 * Statement 描述一个JUnit4单元测试具体要做的事情，是JUnit4拓展的核心，它只有一个方法evaluate()
 */
@ValidateWith(MyAnnotationValidator.class)
public class Test01 {

    public static void main(String[] args) {

        Request request = Request.aClass(Test01.class);


        Runner runner = request.getRunner();

        JUnitCore jUnitCore = new JUnitCore();
        Result result = jUnitCore.run(runner);

        System.out.println(" -------------------- ");
        System.out.println(result);

        Assert.fail();
        Assert.assertThat("xxxx", is(equalTo("xxx")));
    }

    /**
     * 有且只能有一个无参构造
     * 每调用一次@Test都会构建一个新的实例
     */
    public Test01() {
        System.out.println(" Constructor ............. ");
    }

    /**
     * @BeforeClass必须static
     */
    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeClass");
    }

    /**
     * @AfterClass必须static
     */
    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass");
    }

    @Before
    public void before() {
        System.out.println("before");
    }

    @After
    public void after() {
        System.out.println("after");
    }

    @Test(expected = RuntimeException.class)
    public void test1() {
        System.out.println("test1");
        // 因为方法上的@Test注解的expected属性和该类型匹配，方法执行完的异常并不会往外抛
        throw new RuntimeException("xxx");
    }

    @Test
    @SuppressWarnings("xxx")
    public void test2() {
        System.out.println("test2");
        throw new RuntimeException("yyy");
    }

    @Test
    public void test3() {
        System.out.println("test3");
    }

}
