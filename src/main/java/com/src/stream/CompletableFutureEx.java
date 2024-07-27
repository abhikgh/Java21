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


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureEx {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //--- CFs having SAME return type ---
        
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        completableFutures.add(CompletableFuture.supplyAsync( () -> {
            return "aaa";
        }));
        completableFutures.add(CompletableFuture.supplyAsync( () -> {
            return "bbb";
        }));
        completableFutures.add(CompletableFuture.supplyAsync( () -> {
            return "ccc";
        }));

        //wait for all CFs to complete
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();

        //get each CF
        List<String> stringList = new ArrayList<>();
        for (CompletableFuture<String> cf:  completableFutures) {
            stringList.add(cf.get());
        }
        System.out.println("stringList");
        System.out.println(stringList); //[aaa, bbb, ccc]     

        //----------------------------------------------------------------------------------------//

        CompletableFuture<Double> height = CompletableFuture.supplyAsync(() -> {
            return 175.0;
        });
        CompletableFuture<Double> weight = CompletableFuture.supplyAsync(() -> {
            return 65.0;
        });
        //wait for all CFs to complete
        CompletableFuture<Double> bmi = CompletableFuture.allOf(height, weight)
                .thenApplyAsync(value -> {
                    Double h = height.join();
                    Double w = weight.join();
                    Double heightInMeter = h / 100;
                    return w / (heightInMeter * heightInMeter);
                });
        System.out.println("BMI :: " + bmi.get());

        //----------------------------------------------------------------------------------------//

        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();
        completableFutureList.add(CompletableFuture.supplyAsync(() -> {
            return "Hello";
        }));
        completableFutureList.add(CompletableFuture.supplyAsync(() -> {
            return "Beautiful";
        }));
        completableFutureList.add(CompletableFuture.supplyAsync(() -> {
            return "World";
        }));
        //wait for all CFs to complete
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).join();
        //get each CF
        String result = "";
        for(CompletableFuture<String> completableFuture : completableFutureList){
           result = result.concat(completableFuture.get()+" ");
        }
        System.out.println("result : " + result);

        //----------------------------------------------------------------------------------------//

    }
}
