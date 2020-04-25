package com.epichust.entity;

import java.util.Date;

public class Student
{
    private String name;
    private Integer age;
    private int num;
    private Date birthday;

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

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
