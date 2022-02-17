package com.ynan._02.command;

/**
 * @Author yuannan
 * @Date 2022/2/12 11:28
 */
public class CommandMain {

    public static void main(String[] args) {

        HystrixCommandDemo commandDemo = new HystrixCommandDemo(2);
        System.out.println(commandDemo.execute());
    }
}
