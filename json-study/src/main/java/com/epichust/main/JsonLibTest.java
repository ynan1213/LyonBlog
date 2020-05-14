/*
package com.epichust.main;


import com.epichust.entity.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonLibTest
{
    @Test
    public void test1()
    {
        JSONObject object = new JSONObject();
        object.put("yuan", "nan");
        object.put("hu", "rui");
        object.put("lv", "kang");

        object.element("yuan", "chao");
        System.out.println(object.toString());
    }

    @Test
    public void test2()
    {
        String[] strArr = {"aaa", "bbb", "yuan", "nan"};
        //JSONArray jsonArray = JSONArray.fromObject(strArr);
        JSONObject object = JSONObject.fromObject(strArr);
        System.out.println(object);
    }

    @Test
    public void test3()
    {
        String json = "[\"aaa\",\"bbb\",\"yuan\",\"nan\"]";
        JSONArray jsonArray = JSONArray.fromObject(json);
        Object[] objects = jsonArray.toArray();
        System.out.println(Arrays.toString(objects));

    }

    @Test
    public void test4()
    {
        Student stu1 = new Student("张三", 23, 2020051101);
        Student stu2 = new Student("张三", 23, 2020051101);
        Student stu3 = new Student("张三", 23, 2020051101);
        List<Student> list = new ArrayList<>();
        list.add(stu1);
        list.add(stu2);
        list.add(stu3);
        JSONArray jsonArray = JSONArray.fromObject(list);
        //JSONObject jsonObject = JSONObject.fromObject(list); //会抛异常
        System.out.println(jsonArray.toString());
    }

    @Test
    public void test5()
    {
        String json = "[{\"age\":23,\"birthday\":null,\"name\":\"张三\",\"num\":2020051101},{\"age\":23,\"birthday\":null,\"name\":\"张三\",\"num\":2020051101},{\"age\":23,\"birthday\":null,\"name\":\"张三\",\"num\":2020051101}]";
        JSONArray jsonArray = JSONArray.fromObject(json);
        //JSONObject[] objects = (JSONObject[])jsonArray.toArray();
        //System.out.println(objects);

        Object toArray = JSONArray.toArray(jsonArray);
        System.out.println(toArray);

    }

    */
/**
     * 如果json串有多余的属性，测试是否会报错
     *//*

    @Test
    public void test6()
    {
        String json = "{\"xxx\":\"yyy\",\"age\":23,\"birthday\":null,\"name\":\"张三\",\"num\":2020051101,\"hello\":20200513}";
        JSONObject object = JSONObject.fromObject(json);
        Student bean = (Student) JSONObject.toBean(object, Student.class);
        System.out.println(bean);
    }

}
*/
