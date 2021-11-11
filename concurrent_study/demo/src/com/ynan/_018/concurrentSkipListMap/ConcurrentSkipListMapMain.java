package com.ynan._018.concurrentSkipListMap;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Pattern;
import sun.misc.Regexp;

/**
 * @program: concurrent_study
 * @author: yn
 * @create: 2021-07-19 18:37
 * @description:
 */
public class ConcurrentSkipListMapMain
{
    public static void main(String[] args)
    {
        boolean matches = Pattern.matches("^\\d\\d(,\\d\\d)*$", "123");
        System.out.println(matches);
    }
}
