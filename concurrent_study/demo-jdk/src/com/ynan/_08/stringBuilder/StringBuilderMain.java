package com.ynan._08.stringBuilder;

/**
 * @program: concurrent_study
 * @description: demo
 * @author: yn
 * @create: 2021-06-18 21:40
 */
public class StringBuilderMain
{
    public static void main(String[] args)
    {
        StringBuilder builder = new StringBuilder();
        builder.append((String) null);
        System.out.println(builder.toString());

        builder.append(0);


    }
}
