package com.ynan._12.raw;

/**
 * @Author yuannan
 * @Date 2022/1/13 16:36
 */
public class RawMain<T> {

    void test1(T t){

    }

    /**
     * 方法返回值前的泛型有什么用？？？
     */
    <T> void test2(T e){

    }

    public static void main(String[] args) {
        RawMain<Fruit> rawMain = new RawMain();

        Fruit f = new Fruit();
        Apple a = new Apple();
        Person p = new Person();

        rawMain.test2(f);
        rawMain.test2(a);
        rawMain.test2(p);
    }


}

class Fruit {

}

class Apple extends Fruit {

}

class Person {

}