package com.src.stream;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class PrintStringOrNumByCFES {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        IntStream.rangeClosed(1, 20)
                .forEach(num -> {

                    CompletableFuture.supplyAsync(() -> {
                        if (num % 2 != 0) {
                            System.out.println(randomString(5));
                        }
                        return num;
                    }, executorService);

                    CompletableFuture.supplyAsync(() -> {
                        if (num % 2 == 0) {
                            System.out.println(randomInt(5));
                        }
                        return num;
                    }, executorService);


                });

    }

    private static String randomString(int len) {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            stringBuffer.append(allowedChars.charAt((int) (Math.random() * allowedChars.length())));
        }
        return stringBuffer.toString();
    }

    static int randomInt(int len) {
        return (int) (Math.random() * 10000 + 90000); //5 digit random number
    }

}
