package com.epichist.test;

import java.util.List;
import java.util.Map;

public class Person
{
    public String name;
    public Integer age;

    public Boolean flag;
    public int index;
    public List<String> list;
    public Map<String, String> map;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Boolean getFlag()
    {
        return flag;
    }

    public void setFlag(Boolean flag)
    {
        this.flag = flag;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public List<String> getList()
    {
        return list;
    }

    public void setList(List<String> list)
    {
        this.list = list;
    }

    public Map<String, String> getMap()
    {
        return map;
    }

    public void setMap(Map<String, String> map)
    {
        this.map = map;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", flag=" + flag +
                ", index=" + index +
                ", list=" + list +
                ", map=" + map +
                '}';
    }
}
