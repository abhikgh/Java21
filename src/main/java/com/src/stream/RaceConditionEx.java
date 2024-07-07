package com.src.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

import static com.src.stream.JointAccount.BALANCE;

//Race Condition :: 2/more threads competing to update the same resource
//Semaphore to prevent Race Condition
//Semaphore - restricts how many threads that can acquire a Resource
//Semaphore semaphore = new Semaphore(1);
//semaphore.acquire();
//semaphore.release();

@AllArgsConstructor
@Getter
@Setter
class JointAccount extends Thread {
    public static int BALANCE = 100;
    private Semaphore semaphore;
    private String threadName;

    public void increment(Integer sum) {
        BALANCE = BALANCE + sum;
    }


    @SneakyThrows
    @Override
    public void run() {
        semaphore.acquire();
        System.out.println("Thread :: " + threadName + " gets object lock");
        if (threadName.equals("tA")) {
            increment(2000);
        } else if (threadName.equals("tB"))
            increment(1000);
        System.out.println("Thread :: " + threadName + " releases object lock");
        semaphore.release();

    }

}

public class RaceConditionEx {


    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(1);

        JointAccount jointAccountA = new JointAccount(semaphore, "tA");
        JointAccount jointAccountB = new JointAccount(semaphore, "tB");

        jointAccountA.start();
        jointAccountB.start();

        jointAccountA.join(); //jointAccountA to complete before main thread can proceed
        jointAccountB.join(); //jointAccountB to complete before main thread can proceed

        System.out.println("Balance :: " + BALANCE);

        /*
            Thread :: tA gets object lock
            Thread :: tA releases object lock
            Thread :: tB gets object lock
            Thread :: tB releases object lock
            Balance :: 3100

            Thread :: tB gets object lock
            Thread :: tB releases object lock
            Thread :: tA gets object lock
            Thread :: tA releases object lock
            Balance :: 3100

         */


    }
}
