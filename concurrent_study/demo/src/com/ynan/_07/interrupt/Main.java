package com.ynan._07.interrupt;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println( Integer.toBinaryString( -1 ));
        Thread thread = Thread.currentThread();

        // 设置interrupt标志
        thread.interrupt();

        // 返回interrupt标志，但不清除标志
        boolean interrupted = thread.isInterrupted();

        // 返回interrupt标志，清除标志
        boolean interrupted1 = Thread.interrupted();
    }
}
