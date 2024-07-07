package com.src.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Getter
@Setter
@NoArgsConstructor
@ToString
class Result {
    private Integer input;
    private Integer output;
}

@AllArgsConstructor
@Data
@NoArgsConstructor
class NewTask implements Callable<Result> {

    private Integer input;

    @Override
    public Result call() throws Exception {
        Result result = new Result();
        result.setInput(input);
        Integer output = input * 2;
        result.setOutput(output);
        return result;
    }

}

public class ExecutorFrameworkEx {

    public static void main(String[] args) {

        NewTask newTask1 = new NewTask();
        newTask1.setInput(3);
        NewTask newTask2 = new NewTask();
        newTask2.setInput(3);
        NewTask newTask3 = new NewTask();
        newTask3.setInput(3);
        NewTask newTask4 = new NewTask();
        newTask4.setInput(3);
        NewTask newTask5 = new NewTask();
        newTask5.setInput(3);

        try {
            List<Callable<Result>> taskList = Arrays.asList(newTask1, newTask2, newTask3, newTask4, newTask5);

            //ExecutorService in Thread-pool
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            //Execute all tasks and get reference to Future objects
            List<Future<Result>> listFutureResult = executorService.invokeAll(taskList);

            executorService.shutdown();

            for (Future<Result> resultFuture : listFutureResult) {
                try {
                    Result result = resultFuture.get();
                    System.out.println(result.getInput() + ": " + result.getOutput());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

		/*
		3: 6
		3: 6
		3: 6
		3: 6
		3: 6
		*/
    }
}
