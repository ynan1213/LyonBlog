package com.epichust.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.epichust.entity.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FastJsonTest
{
    @Test
    public void test()
    {
        JSONObject object = new JSONObject();
        object.put("张三", "23");
        object.put("李四", "24");
        object.put("王五", "25");
        System.out.println(object.toJSONString());
        System.out.println(object.toString());
    }

    /**
     * 忽略属性 和 日期格式化
     */
    @Test
    public void test1()
    {
        Student stu = new Student("张三", 23, 20200514, new Date());
        String str = JSON.toJSONString(stu);
        System.out.println(str);
        new Student();
    }

    @Test
    public void test3()
    {
        Student stu1 = new Student("张三", 23, 2020051101, new Date());
        Student stu2 = new Student("李四", 24, 2020051102, new Date());
        Student stu3 = new Student("王五", 25, 2020051103, new Date());
        List<Student> list = new ArrayList<>();
        list.add(stu1);
        list.add(stu2);
        list.add(stu3);

        String s = JSON.toJSONString(list);
        System.out.println(s);
    }

    @Test
    public void test4()
    {
        Student[] stuArr = new Student[]{
                new Student("张三", 23, 2020051101, new Date()),
                new Student("李四", 24, 2020051102, new Date()),
                new Student("王五", 25, 2020051103, new Date())};
        String s = JSON.toJSONString(stuArr);
        System.out.println(s);
    }

    @Test
    public void test5()
    {
        String json = "{\"age\":23,\"birthday\":1589436451294,\"name\":\"张三\",\"num\":20200514}";
        JSONObject o = (JSONObject)JSON.parse(json);
        System.out.println(o);
    }

    @Test
    public void test6()
    {
        String json = "[{\"age\":23,\"birthday\":1589436871209,\"name\":\"张三\",\"num\":2020051101},{\"age\":24,\"birthday\":1589436871210,\"name\":\"李四\",\"num\":2020051102},{\"age\":25,\"birthday\":1589436871210,\"name\":\"王五\",\"num\":2020051103}]";
        JSONArray o = (JSONArray)JSON.parse(json);
        System.out.println(o);
    }

    @Test
    public void test7()
    {
        String json = "{\"age\":23,\"birthday\":1589436451294,\"name\":\"张三\",\"num\":20200514}";
        JSONObject o = JSON.parseObject(json);
//        JSONArray o = JSON.parseArray(json);
        System.out.println(o);
    }

    @Test
    public void test8()
    {
        String json = "{\"xxxx\":\"yyyy\",\"age\":23,\"birthday\":1589436451294,\"name\":\"张三\",\"num\":20200514}";
        Student o = JSON.parseObject(json, Student.class);
        System.out.println(o);
    }

    @Test
    public void test9()
    {
        String json = "{\"xxxx\":\"yyyy\",\"age\":23,\"birthday\":1589436451294,\"name\":\"张三\",\"num\":20200514}";
        Map o = JSON.parseObject(json, Map.class);
        System.out.println(o);
    }

    @Test
    public void test10()
    {
        String json = "{\"xxxx\":\"yyyy\",\"age\":23,\"birthday\":1589436451294,\"name\":\"张三\",\"num\":20200514}";
        Student[] o = JSON.parseObject(json, Student[].class);
        System.out.println(o);
    }

}
