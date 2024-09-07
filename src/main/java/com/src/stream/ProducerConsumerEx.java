package com.src.stream;

// Thread thread = new Thread(x);
//  x -> extends Thread / implements Runnable

//Semaphore - restricts how many threads that can acquire a Resource

//Here we will use a Semaphore of 1 that will allow only 1 thread (odd/even thread)
// to access a shared resource(sharedNum) and print and increment it

//Producer-Consumer -> Semaphore + join

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

import static com.src.stream.PrintStringOrNumByCFES.randomInt;

// Class -> ThreadWithSempahore
@AllArgsConstructor
class ThreadWithSempahore extends Thread{

    private Semaphore semaphore;
    private String threadName;

    @SneakyThrows
    @Override
    public void run() {
        if(threadName.equalsIgnoreCase("update")){
            semaphore.acquire();
            System.out.println(threadName + " acquired Semaphore");
            for(int i=0;i<5;i++){
                ProducerConsumerEx.COUNTER += randomInt(3);
            }
            semaphore.release();
            System.out.println(threadName + " releases Semaphore");
        }
        if(threadName.equalsIgnoreCase("retrieve")){
            semaphore.acquire();
            System.out.println(threadName + " acquired Semaphore");
            System.out.println("COUNTER :: " + ProducerConsumerEx.COUNTER);
            semaphore.release();
            System.out.println(threadName + " releases Semaphore");
        }
    }
}

public class ProducerConsumerEx {

    public static int COUNTER = 1;

    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(1);

        ThreadWithSempahore update = new ThreadWithSempahore(semaphore, "update");
        ThreadWithSempahore retrieve = new ThreadWithSempahore(semaphore, "retrieve");

        update.start();
        update.join(); //update will complete then main thread can proceed

        retrieve.start();
    }

}
