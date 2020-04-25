/*
package com.epichust.main;

import com.epichust.entity.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.IOException;
import java.util.*;

public class JacksonTest
{
    public static void main(String[] args) throws IOException
    {
        String json = "{\"san\":{\"name\":\"张三\",\"age\":23,\"num\":1,\"birthday\":\"2020-04-25 03-06-52\"},\"si\":{\"name\":\"李四\",\"age\":24,\"num\":2,\"birthday\":\"2020-04-25 03-06-52\"},\"wu\":{\"name\":\"王五\",\"age\":25,\"num\":3,\"birthday\":\"2020-04-25 03-06-52\"}}";
        ObjectMapper mapper = new ObjectMapper();
        MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Student.class);
        Object o = mapper.readValue(json, mapType);
        System.out.println(o);
    }
}*/
