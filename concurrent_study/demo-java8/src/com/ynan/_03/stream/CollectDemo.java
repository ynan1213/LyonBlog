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
        Stream<String> stream1 = Arrays.stream(new String[]{"hello1", "world1", "nihao1"});
        // --------- 1: 原始写法 ---------
        ArrayList list1 = stream1.collect(
            () -> new ArrayList(),
            (theList, item) -> theList.add(item),
            (theList, theList2) -> theList.addAll(theList2)
        );
        System.out.println(list1);
        System.out.println("------------------------------------");
        // --------- 2: 简洁写法 ---------
        Stream<String> stream2 = Arrays.stream(new String[]{"hello2", "world2", "nihao2"});
        ArrayList list2 = stream2.collect(
            ArrayList::new,
            ArrayList::add,
            ArrayList::addAll
        );
        System.out.println(list2);
        System.out.println("------------------------------------");
        // --------- 3: 内部详细过程 ---------
        Stream<String> stream3 = Arrays.stream(new String[]{"hello3", "world3", "nihao3"});
        List<String> list3 = stream3.collect(
            () -> {
                ArrayList<String> arrayList = new ArrayList<>();
                System.out.println("第一个list诞生, size: " + arrayList.size());
                return arrayList;
            },
            (theList, item) -> {
                System.out.println("第二个list的size: " + theList.size());
                theList.add(item);
            },
            (l1, l2) -> {
                System.out.println("第三个list1的size: " + l1.size());
                System.out.println("第四个list2的size: " + l2.size());
                l1.addAll(l2);
            }
        );
        System.out.println(list3);
    }

}
