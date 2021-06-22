package com.ynan._03.jstack;

import java.util.concurrent.TimeUnit;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        new Thread(() -> {
            try
            {
                while (true)
                {
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }).start();

        while (true)
        {
            TimeUnit.MILLISECONDS.sleep(10);
        }

    }
}
