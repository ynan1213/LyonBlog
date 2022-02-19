package com.ynan._04.completableFuture;

import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {

        CompletableFuture.runAsync(() -> {
            System.out.println("11111" + " " + Thread.currentThread().getName());
        }).thenRun(() -> {
            System.out.println("22222" + " " + Thread.currentThread().getName());
        });

        CompletableFuture future = new CompletableFuture();

    }
}
