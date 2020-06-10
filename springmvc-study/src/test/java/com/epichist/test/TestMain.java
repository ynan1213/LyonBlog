package com.epichist.test;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.WebDataBinder;

public class TestMain
{
    public static void main(String[] args)
    {
        Person person = new Person();
        WebDataBinder binder = new WebDataBinder(person, "person");

        // 设置属性（此处演示一下默认值）
        MutablePropertyValues pvs = new MutablePropertyValues();
        // 使用!来模拟各个字段手动指定默认值
//        pvs.add("name", "fsx");
        pvs.add("!name", "不知火舞");
        pvs.add("age", 18);
        pvs.add("!age", 10); // 上面有确切的值了，默认值不会再生效

        binder.bind(pvs);
        System.out.println(person);

    }
}
