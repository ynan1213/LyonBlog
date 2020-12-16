package com.epichust.main;

import com.epichust.entity.Student;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.omg.Messaging.SyncScopeHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JacksonTest
{

    /**
     * 序列化
     */

    @Test
    public void test1() throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Student stu = new Student("张三", 23, 2020051101, new Date());
        String string = mapper.writeValueAsString(stu);
        System.out.println(string);
    }


    /**
     * 序列化：JSON串不带双引号
     */

    @Test
    public void test2() throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        Student stu = new Student("张三", 23, 2020051101, new Date());
        String string = mapper.writeValueAsString(stu);
        System.out.println(string);
    }



    /**
     * List序列化
     */

    @Test
    public void test3() throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Student stu1 = new Student("张三", 23, 2020051101, new Date());
        Student stu2 = new Student("李四", 24, 2020051102, new Date());
        Student stu3 = new Student("王五", 25, 2020051103, new Date());
        List<Student> list = new ArrayList<>();
        list.add(stu1);
        list.add(stu2);
        list.add(stu3);

        String s = mapper.writeValueAsString(list);
        System.out.println(s);
    }


    /**
     * 数组序列化：结果和list一样
     */

    @Test
    public void test4() throws JsonProcessingException
    {
        new Student("name", 23, 2222, new Date());
        Student[] stuArr = new Student[]{
                new Student("张三", 23, 2020051101, new Date()),
                new Student("李四", 24, 2020051102, new Date()),
                new Student("王五", 25, 2020051103, new Date())};
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(stuArr);
        System.out.println(str);
    }

    @Test
    public void test5() throws IOException
    {
        String json = "{\"xxx\":\"yyy\",\"name\":\"张三\",\"age\":23,\"num\":2020051101,\"birthday\":\"2020-05-14 02:17:43\"}";
        ObjectMapper mapper = new ObjectMapper();
        Student o = mapper.readValue(json, Student.class);
        System.out.println(o);
    }

    @Test
    public void test6() throws IOException
    {
        String json = "[{\"name\":\"张三\",\"age\":23,\"num\":2020051101,\"birthday\":\"2020-05-14 02:36:04\"},{\"name\":\"李四\",\"age\":24,\"num\":2020051102,\"birthday\":\"2020-05-14 02:36:04\"},{\"name\":\"王五\",\"age\":25,\"num\":2020051103,\"birthday\":\"2020-05-14 02:36:04\"}]";
        ObjectMapper mapper = new ObjectMapper();
        Student[] o = mapper.readValue(json, Student[].class);
        System.out.println(o);
    }

    @Test
    public void test7() throws IOException
    {
        String json = "[{\"name\":\"张三\",\"age\":23,\"num\":2020051101,\"birthday\":\"2020-05-14 02:36:04\"},{\"name\":\"李四\",\"age\":24,\"num\":2020051102,\"birthday\":\"2020-05-14 02:36:04\"},{\"name\":\"王五\",\"age\":25,\"num\":2020051103,\"birthday\":\"2020-05-14 02:36:04\"}]";
        ObjectMapper mapper = new ObjectMapper();

        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Student.class);
        List o = mapper.readValue(json, type);
        System.out.println(o);
    }
    @Test
    public void test8() throws IOException
    {
        String json = "[{\"name\":\"张三\",\"age\":23,\"num\":2020051101,\"birthday\":\"2020-05-14 02:36:04\"},{\"name\":\"李四\",\"age\":24,\"num\":2020051102,\"birthday\":\"2020-05-14 02:36:04\"},{\"name\":\"王五\",\"age\":25,\"num\":2020051103,\"birthday\":\"2020-05-14 02:36:04\"}]";
        ObjectMapper mapper = new ObjectMapper();

        List o = mapper.readValue(json, new TypeReference<List<Student>>(){});
        System.out.println(o);
    }

    @Test
    public void test9() throws IOException
    {
        //String json = "[{\"name\":\"张三\",\"age\":23,\"num\":2020051101,\"birthday\":\"2020-05-14 02:36:04\"},{\"name\":\"李四\",\"age\":24,\"num\":2020051102,\"birthday\":\"2020-05-14 02:36:04\"},{\"name\":\"王五\",\"age\":25,\"num\":2020051103,\"birthday\":\"2020-05-14 02:36:04\"}]";
        ObjectMapper mapper = new ObjectMapper();

        Student stu = new Student("自来也", 40, 001, new Date());
        String str = mapper.writeValueAsString(stu);
        System.out.println(str);
    }
}
