package com.ynan.entity;

import java.util.List;

/**
 * @program: mybatis-parent
 * @description:
 * @author: yn
 * @create: 2021-06-25 18:36
 */
public class User
{
    private int uid;
    private String uname;
    private int uage;
    private String addr;
    private String school;

    private List<Book> bookist;

    public User()
    {
    }

    public User(String uname, int uage, String addr, String school)
    {
        this.uname = uname;
        this.uage = uage;
        this.addr = addr;
        this.school = school;
    }

    public List<Book> getBookist()
    {
        return bookist;
    }

    public void setBookist(List<Book> bookist)
    {
        this.bookist = bookist;
    }

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public String getUname()
    {
        return uname;
    }

    public void setUname(String uname)
    {
        this.uname = uname;
    }

    public int getUage()
    {
        return uage;
    }

    public void setUage(int uage)
    {
        this.uage = uage;
    }

    public String getAddr()
    {
        return addr;
    }

    public void setAddr(String addr)
    {
        this.addr = addr;
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", uage=" + uage +
                ", addr='" + addr + '\'' +
                ", school='" + school + '\'' +
                ", bookist=" + bookist +
                '}';
    }
}
