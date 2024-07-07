package com.src.stream;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class PrintOddEvenByCFES {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2); //2 different threads

        IntStream.rangeClosed(1,50)
                        .forEach(num -> {
                            CompletableFuture<Integer> oddCompletableFuture = CompletableFuture.supplyAsync(() -> {
                                if (num % 2 != 0) {
                                    System.out.println("Thread :: " + Thread.currentThread().getName() + ", Value :: " + num);
                                }
                                return num;
                            }, executorService);

                            CompletableFuture<Integer> evenCompletableFuture = CompletableFuture.supplyAsync(() -> {
                                if (num % 2 == 0) {
                                    System.out.println("Thread :: " + Thread.currentThread().getName() + ", Value :: " + num);
                                }
                                return num;
                            }, executorService);

                        });


        // completableFuture.join() : returns the value when the future is complete / throws an unchecked exception if exception
    }
}
