package com.src.stream;

/*

        public class CompletableFuture<T> implements Future<T>, CompletionStage<T> {

        Future                                              CompletableFuture
        --------------                                  ---------------------------------
        Interface - Java 5                                 Class - Java 8
        cannot complete manually                           can complete manually
                                                                - completableFuture.complete
                                                           Callable methods
                                                              - methods which can be called after the completableFuture is complete
                                                              - runAsync()
                                                              - supplyAsync()
                                                              - thenApplyAsync()
                                                              - thenCombine()
                                                              - thenCompose()
                                                              - thenAcceptAsync()


 */


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureEx {

    public static void main(String[] args) {

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        boolean isComplete = completableFuture.complete("Hello Java"); //completes the future with the passed value if the future has not been completed already
        System.out.println("CompletableFuture isComplete :: " + isComplete);

        //----------------------------------------------------------------------------------------//

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            return "Hello";
        });
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            return "Beautiful";
        });
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            return "World";
        });

        String completedResult = Stream.of(cf1, cf2, cf3).map(CompletableFuture::join).collect(Collectors.joining(" "));
        System.out.println("CompletableFuture join :: " + completedResult); // Hello Beautiful World

        //----------------------------------------------------------------------------------------//

        CompletableFuture<String> welcomeText = CompletableFuture.supplyAsync(() -> {
            return "Hello";
        }).thenApplyAsync(x -> { // callback
            return x.concat(" World");
        }).thenApplyAsync(y -> { // callback
            return y.toUpperCase();
        });

        try {
            if (welcomeText.isDone()) {  // non-blocking
                System.out.println("CompletableFuture thenApplyAsync :: " + welcomeText.get()); // blocking //HELLO WORLD
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //----------------------------------------------------------------------------------------//

        //thenCombine :: combining 2 independent futures together and do something with the result after both of them are complete
        CompletableFuture<Double> bmi = CompletableFuture.supplyAsync(() -> {
            return 175.0;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {    //
            return 65.0;
        }), (height, weight) -> { // independent
            Double heightInMeter = height / 100;
            return weight / (heightInMeter * heightInMeter);
        });

        try {
            System.out.println("CompletableFuture thenCombine :: " + bmi.get());   //blocking   //21.224489795918366
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //----------------------------------------------------------------------------------------//

        //thenCompose :: combining 2 dependent futures together
        String userName = "abc";
        CompletableFuture<String> composed = CompletableFuture.supplyAsync(() -> {
            return userName.toUpperCase();
        }).thenCompose(x -> {
            return CompletableFuture.supplyAsync(() -> {
                return x.concat("def");
            });
        });

        try {
            System.out.println("CompletableFuture thenCompose :: " + composed.get());  // ABCdef
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //----------------------------------------------------------------------------------------//

        //thenAcceptBoth :: working with 2 independent futures together but dont need to pass the result to a Future
        CompletableFuture<Void> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            return 175.0;
        }).thenAcceptAsync(x -> CompletableFuture.runAsync(() -> {
            System.out.println(x);  //175
        }));

        try {
            System.out.println("CompletableFuture thenAcceptAsync :: " + completableFuture1.get());  //blocking   //null
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }
}
