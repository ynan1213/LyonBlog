package com.ynan._03.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author yuannan
 * @Date 2021/9/25 10:31 下午
 */
public class CollectDemo {

    public static void main(String[] args) {
        Stream<String> stream = Arrays.stream(new String[]{"hello", "world", "nihao"});

//        ArrayList list1 = stream.collect(
//            () -> new ArrayList(),
//            (theList, item) -> theList.add(item),
//            (theList, theList2) -> theList.addAll(theList2)
//        );
//        System.out.println(list1);
        System.out.println("------------------");
        ArrayList list2 = stream.collect(
            ArrayList::new,
            ArrayList::add,
            ArrayList::addAll
        );
        System.out.println(list2);

    }

}
