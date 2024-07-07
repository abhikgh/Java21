package com.src.stream;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Data
@NoArgsConstructor
class Result3 implements Callable<Result3> {

    private BigDecimal input;
    private BigDecimal output;

    private Result3 result;

    Result3(BigDecimal input, BigDecimal output){
        this.result = new Result3();
        this.result.setInput(input);
        this.result.setOutput(output);
    }

    @Override
    public Result3 call() throws Exception {
        BigDecimal input = this.result.getInput().setScale(2, RoundingMode.HALF_UP);
        BigDecimal output = input.multiply(new BigDecimal("23.45")).setScale(2, RoundingMode.HALF_UP);
        result.setOutput(output);
        result.setInput(input);
        return result;
    }
}

public class ExecutorFrameworkEx3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
       Result3 result1 = new Result3(new BigDecimal("34223.45"),null);
       Result3 result2 = new Result3(new BigDecimal("134223.45"),null);
       Result3 result3 = new Result3(new BigDecimal("3455.45"),null);
       Result3 result4 = new Result3(new BigDecimal("67327.45"),null);
       Result3 result5 = new Result3(new BigDecimal("97833.45"),null);

        //List of Callable
        List<Result3> list = Arrays.asList(result1,result2,result3,result4,result5);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //List of Future
        List<Future<Result3>> futureList = executorService.invokeAll(list);

        for (Future<Result3> future : futureList) {

            if(future.isDone()) {              //non-blocking
                Result3 result = future.get();  //blocking

                System.out.println("Result :: " + result.getInput() + "-" + result.getOutput());
            }
        }


    }
}
