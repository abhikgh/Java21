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

        List<String> inputList = List.of("aaa", "bbb", "ccc");
        String outputList = String.join(",", inputList.stream().map(String::toUpperCase).toList());
        System.out.println(outputList);

        String outputList2 = "";
        List<CompletableFuture<String>> completableFutureList = inputList.stream().map(CompletableFutureEx::convertToUpper).toList();
        //wait for all CFs to complete
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).join();
        //get each CF
        for(CompletableFuture<String> cf: completableFutureList){
            if(cf.isDone()){
                outputList2 +=cf.get();
            }
        }
        System.out.println(outputList2);

        //----------------------------------------------------------------------------------------//

        List<CompletableFuture<String>> completableFutureList2 = new ArrayList<>();
        completableFutureList2.add(CompletableFuture.supplyAsync(() -> {
            return "Hello";
        }));
        completableFutureList2.add(CompletableFuture.supplyAsync(() -> {
            return "Beautiful";
        }));
        completableFutureList2.add(CompletableFuture.supplyAsync(() -> {
            return "World";
        }));
        //wait for all CFs to complete
        CompletableFuture.allOf(completableFutureList2.toArray(new CompletableFuture[0])).join();
        //get each CF
        String result = "";
        for(CompletableFuture<String> completableFuture : completableFutureList2){
           result = result.concat(completableFuture.get()+" ");
        }
        System.out.println("result : " + result);

        //----------------------------------------------------------------------------------------//

    }


    private static CompletableFuture<String> convertToUpper(String inputString) {
        String output = inputString.toUpperCase();
        return CompletableFuture.completedFuture(output);
    }
}
