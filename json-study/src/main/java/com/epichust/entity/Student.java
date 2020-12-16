package com.epichust.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties({"age","num"})
public class Student
{
    private String name;
    private Integer age;
    private int num;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date birthday;

    public Student()
    {
        super();
    }

    public Student(String name)
    {
        super();
        this.name = name;
    }

    public Student(Integer age)
    {
        super();
        this.age = age;
    }

    public Student(String name, Integer age, int num, Date birthday)
    {
        this.name = name;
        this.age = age;
        this.num = num;
        this.birthday = birthday;
    }
    public Student(String name, Integer age, int num)
    {
        this.name = name;
        this.age = age;
        this.num = num;
    }

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

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }
    @Override
    public String toString()
    {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", num=" + num +
                ", birthday=" + birthday +
                '}';
    }
}
