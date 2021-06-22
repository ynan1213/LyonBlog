package com.ynan._02.string;

public class StringMain
{
    public static void main(String[] args)
    {
        String s2 = new String("hello");
        String s1 = "hello";
        s2.intern();
        System.out.println(s1 == s2);

        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);

        String s5 = new String("1") + new String("1");
        System.out.println(s5 == s5.intern());

        System.gc();
        Runtime.getRuntime().gc();

    }
}
