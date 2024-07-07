package com.src.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinEx {

    /*
    ForkJoinPool is a type of ExecutorService in Java that is designed to handle a large number of tasks efficiently

    fork - breaks the task into smaller tasks to run asynchronously
    join - joins the subtasks

    ForkJoinTask is the base class for tasks executed inside ForkJoinPool.
        - RecursiveAction for void tasks (compute())
        - RecursiveTask<V> for tasks that return a value (compute())

        forkJoinPool.invoke(customRecursiveTask);

            left.fork();
            String rightResult = right.compute();
            String leftResult = left.join();
            return leftResult.concat(rightResult);
     */

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        String input = "This is the test string to change. Lets see.We will check if the string is turned to upper case.";
        CustomRecursiveTask customRecursiveTask = new CustomRecursiveTask(input);
        String result = (String) forkJoinPool.invoke(customRecursiveTask);

        System.out.println(result);
        //THIS IS THE TEST STRING TO CHANGE. LETS SEE.WE WILL CHECK IF THE STRING IS TURNED TO UPPER CASE.

    }

}

class CustomRecursiveTask extends RecursiveTask<String> {

    private final String input;

    public CustomRecursiveTask(String input) {
        this.input = input;
    }

    @Override
    protected String compute() {
        if(input.length()<10) {
            return input.toUpperCase();
        }else {
            String part1 = input.substring(0, input.length() / 2);
            String part2 = input.substring(input.length() / 2);

            CustomRecursiveTask left = new CustomRecursiveTask(part1);
            CustomRecursiveTask right = new CustomRecursiveTask(part2);

            left.fork();
            String rightResult = right.compute();
            String leftResult = left.join();
            return leftResult.concat(rightResult);
        }
    }
}