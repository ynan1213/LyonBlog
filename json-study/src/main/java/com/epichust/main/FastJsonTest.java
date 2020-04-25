package com.epichust.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.epichust.entity.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FastJsonTest
{
    public static void main(String[] args)
    {
        String json1 = "{\"age\":23,\"birthday\":1587785248243,\"name\":\"张三\",\"num\":1}";
        String json2 = "[{\"age\":23,\"birthday\":1587785348632,\"name\":\"张三\",\"num\":1},{\"age\":24,\"birthday\":1587785348632,\"name\":\"李四\",\"num\":2}]";

        List<Student> stu = JSON.parseArray(json2, Student.class);
        System.out.println(stu);


    }
}
