package com.src.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Task1 implements Callable<Object> {

    private String input;

    @Override
    public Result1 call() throws Exception {
        Result1 result1 = new Result1();
        result1.setInput(input);
        result1.setOutput(input.toUpperCase(Locale.ENGLISH).concat("-abcde"));
        return result1;
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Result1 {
    private String input;
    private String output;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Task2 implements Callable<Object> {

    private Integer inputInt;

    @Override
    public Result2 call() throws Exception {
        Result2 result2 = new Result2();
        result2.setInputInt(inputInt);
        result2.setOutputInt(inputInt * 2 + 100);
        return result2;
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Result2 {
    private Integer inputInt;
    private Integer outputInt;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class FinalResult {
    private String input;
    private String output;
    private Integer inputInt;
    private Integer outputInt;
}

//ExecutorService is an interface that does not mandate a particular way how the submitted tasks (not threads) are executed
// tasks may implement Runnable or Callable

public class ExecutorFrameworkMultipleServiceEx {

    public static void main(String[] args) {

        FinalResult finalResult = new FinalResult();

        Task1 task1 = new Task1();
        task1.setInput("erty");

        Task2 task2 = new Task2();
        task2.setInputInt(10);

        try {

            List<Callable<Object>> taskList = List.of(task1, task2);

            ExecutorService executorService = Executors.newFixedThreadPool(10);

            List<Future<Object>> futureList = executorService.invokeAll(taskList);

            executorService.shutdown();

            for (Future<Object> future : futureList) {

                Object object = future.get();
                if (object instanceof Result1 futureResult1) {
                    finalResult.setInput(futureResult1.getInput());
                    finalResult.setOutput(futureResult1.getOutput());
                }

                if (object instanceof Result2 futureResult2) {
                    finalResult.setInputInt(futureResult2.getInputInt());
                    finalResult.setOutputInt(futureResult2.getOutputInt());
                }
            }

            System.out.println("finalResult :: " + finalResult.getInput() + "-" + finalResult.getOutput() + "-" + finalResult.getInputInt() + "-" + finalResult.getOutputInt());

            //finalResult :: erty-ERTY-abcde-10-120

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
