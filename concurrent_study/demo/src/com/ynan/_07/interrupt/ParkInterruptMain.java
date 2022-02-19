package com.ynan._07.interrupt;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author yuannan
 * @Date 2022/1/14 10:59
 */
public class ParkInterruptMain {

    public static void main(String[] args) {
        Thread.currentThread().interrupt();

        LockSupport.park();
        LockSupport.park();
        LockSupport.park();
        LockSupport.park();

        System.out.println("====== over ======");

    }
}
