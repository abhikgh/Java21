package com.src.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

import static com.src.stream.JointAccount.BALANCE;

//Race Condition :: 2/more threads competing to update the same resource
//Semaphore to prevent Race Condition

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

        // If any ordering of jointAccountA, jointAccountB to run b ut whichever runs wil complete and then the other will run

        jointAccountA.start();
        jointAccountB.start();

        System.out.println("Balance :: " + BALANCE);

        // If jointAccountA to run before jointAccountB

        jointAccountA.start();
        jointAccountA.join();

        jointAccountB.start();
        jointAccountB.join();

        System.out.println("Balance :: " + BALANCE);

      

        /*
                Thread :: tA gets object lock
                Thread :: tA releases object lock
                Thread :: tB gets object lock
                Thread :: tB releases object lock
                Balance :: 3100

         */
    }
}
