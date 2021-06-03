package com.epichust.service.main;

import com.epichust.controller.UserController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest1
{
    /**
     *  ClassPathXmlApplicationContext继承自AbstractRefreshableApplicationContext，
     *  该类型和GenericApplicationContext最大的区别就是：
     *  1.GenericApplicationContext 在构造方法内就创建了`beanfactory`，并且后面不能再刷新，刷新就抛异常
     *  2.AbstractRefreshableApplicationContext 构造方法内均不会创建`BeanFactory`，在`refreshBeanFactory()`方法内
     *    先判断`BeanFactory`是否已创建，一般情况下没有创建，如果有创建，关闭它，然后创建一个新的。
     *  3.GenericApplicationContext需要手动刷新，AbstractRefreshableApplicationContext会自动刷新
     */
    public static void main(String[] args)
    {
        ApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
        UserController controller = ac.getBean("userController", UserController.class);
        controller.test();
    }
}
