package com.ynan._12.raw;

/**
 * @Author yuannan
 * @Date 2022/1/13 16:36
 */
public class RawMain<T> {

    T test(){
        return null;
    }

    T test1(T t) {
        return t;
    }

    <T> void test2(T t) {

    }

    <T> T test3(T t) {
        return null;
    }

    <T> T test4() {
        return null;
    }

    public static void main(String[] args) {
        Fruit f = new Fruit();
        Apple a = new Apple();
        Person p = new Person();

        RawMain<Fruit> rawMain = new RawMain();
        Fruit test = rawMain.test();
        Fruit fruit = rawMain.test1(f);
        Fruit fruit1 = rawMain.test1(a);
//        Fruit fruit2 = rawMain.test1(p);
        rawMain.test2(f);
        rawMain.test2(a);
        rawMain.test2(p);

        Fruit fruit2 = rawMain.test3(f);
        Apple apple = rawMain.test3(a);
        Person person = rawMain.test3(p);

        Fruit o1 = rawMain.test4();
        Apple o2 = rawMain.test4();
        Person o3 = rawMain.test4();

        RawMain<Apple> rawMain1 = new RawMain();
        Apple test1 = rawMain1.test();
//        Apple apple = rawMain1.test1(f);

        //        rawMain.test2(f);
        //        rawMain.test2(a);
        //        rawMain.test2(p);
    }


}

class Fruit {

}

class Apple extends Fruit {

}

class Person {

}