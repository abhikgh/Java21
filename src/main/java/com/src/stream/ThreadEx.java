package com.src.stream;

import java.io.File;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//Thread implements Runnable
//Runnable is a FunctionalInterface - run()
//ThreadLocal :: variables which are local to a thread, child class cannot access
//InheritableThreadLocal :: child class can access

/*

                Thread                vs             Process
                ---------------------------------------------------
                Lightweight
                Resides within process
                Shares memory
                Context-switching is easy
                Inter-thread communication is easy
                    (wait, notify)

                 User Thread     vs             Daemon Thread   
                 ---------------------------------------------------
                 Runs application logic      Background processes - like Garbage Collection, Timer
                 JVM waits to finish         JVM doesn't wait to finish

                 Thread Pool   vs     Creating New Threads
                 --------------------------------------------
                Reused thread            New threads
                Resource usage: low      Resource usage: high (expensive context switching)

                ReentrantLock
                --------------------

                  /*
                      ReentrantLock allows a thread to acquire an object lock multiple times
                      Fair locking - new ReentrantLock(true);	// the thread that has waited the maximum time for the object lock gets the lock
                      reentrantLock.lock()
                      reentrantLock.tryLock(10,TimeUnit.SECONDS)
                      reentrantLock.lockInterruptibly()   //current thread is waiting for lock but some other thread requests the lock, then the current thread will be interrupted and return                                        immediately
                      reentrantLock.unlock()
                  
                      synchronized                        vs                              ReentrantLock
                      --------------------------------------------------------------------------------------
                      less control                                          more control - when to lock , when to unlock
                      lock can be held across methods

  public class ThreadRunByReentrantLock implements Runnable{

    ReentrantLock reentrantLock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {

        ThreadRunByReentrantLock client = new ThreadRunByReentrantLock();

        Thread thread1 = new Thread(client, "thread1");
        Thread thread2 = new Thread(client, "thread2");
        Thread thread3 = new Thread(client, "thread3");
        Thread thread4 = new Thread(client, "thread4");

        thread1.start();Thread.sleep(1);
        thread2.start();Thread.sleep(1);
        thread3.start();Thread.sleep(1);
        thread4.start();Thread.sleep(1);

    }

    @Override
    public void run() {

        try{

            reentrantLock.lock();

            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " running now");

            System.out.println(threadName + " exiting ");

        }finally{
            reentrantLock.unlock();
        }
    }

}


    thread1 running now
    thread1 exiting
    thread2 running now
    thread2 exiting
    thread3 running now
    thread3 exiting
    thread4 running now
    thread4 exiting



wait()              vs           sleep()                      vs           yield()
--------------------------------------------------------------------------------------------------------  
t.wait() - t releases lock, waits to get it back by notify()/notifyAll()
              
                              Thread.sleep() /t.sleep() - t doesn't release lock

                                                      Thread.yield() - static method that hints to the thread scheduler that the current thread is willing to pause execution
                and allow threads with the same priority to execute, though it does not guarantee a pause.

                synchronized (obj) {
                    obj.wait();  // Releases lock, waits for notify()
                    obj.notify();  // Wakes up waiting thread
                }
                //if obj not synchronized - IllegalMonitorStateException


    Deadlock             vs         Livelock                vs           Starvation            
    ------------------------------------------------------------------------------------------------
    2 or more threads waiting for each other
                              Thread keeps changing state but no work done
                                                                      Thread does nt get to run as higher priority thread runs
                
*/


class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println("Run in myThread...");
    }
}

class MyRunnbale implements Runnable{

    @Override
    public void run() {
        System.out.println("Run in myRunnable...");
    }
}

public class ThreadEx {


    public static final ThreadLocal<Book> threadLocalBook = new ThreadLocal<>();
    private static String message = "a";

    public static void main(String[] args) throws InterruptedException {
        
        //1. extends Thread
        Thread extendsThread = new MyThread();
        extendsThread.start(); //Run in myThread...

        //2. implements Runnable
        MyRunnbale myRunnbale = new MyRunnbale();
        Thread threadAsRunnable = new Thread(myRunnbale);
        threadAsRunnable.start();

        //3. implements Runnable as lambda
        Thread thread = new Thread(() -> {
            System.out.println("Running thread as lambda..");
        });
        thread.start();

        //4. Thread as Lambda
        Runnable threadAsLambda = () -> {
            System.out.println("Running thread as lambda..");
        };
        threadAsLambda.run();

        //5. Thread as Anonymous Inner class
        Thread threadAnonymous = new Thread(){
            @Override
            public void run() {
                System.out.println("Running thread as Anonymous Inner class...");
            }
        };
        threadAnonymous.start();

        //make a thread a Daemon thread
        //t.setDaemon(true);

        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);


        AtomicBoolean isFound = new AtomicBoolean(false);
        Thread t1 = new Thread() {

            @Override
            public void run() {
                try (Scanner scanner = new Scanner(new File("src/main/resources/file1.csv"))) {
                    scanner.useDelimiter(",");
                    Thread.currentThread().setName("t1");
                    while (scanner.hasNext()) {
                        String elem = scanner.next();
                        if (elem.contains("ip")) {
                            isFound.set(true);
                            System.out.println("Thread " + Thread.currentThread().getName() +" found ip " + elem.split(":")[1]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

        Thread t2 = new Thread(() -> {
            try (Scanner scanner = new Scanner(new File("src/main/resources/file2.csv"))) {
                scanner.useDelimiter(",");
                Thread.currentThread().setName("t2");
                while (scanner.hasNext()) {
                    String elem = scanner.next();
                    if (elem.contains("ip")) {
                        isFound.set(true);
                        System.out.println("Thread " + Thread.currentThread().getName() +" found ip " + elem.split(":")[1]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread() {

            @Override
            public void run() {
                try (Scanner scanner = new Scanner(new File("src/main/resources/file3.csv"))) {
                    scanner.useDelimiter(",");
                    Thread.currentThread().setName("t3");
                    while (scanner.hasNext()) {
                        String elem = scanner.next();
                        if (elem.contains("ip")) {
                            isFound.set(true);
                            System.out.println("Thread " + Thread.currentThread().getName() +" found ip " + elem.split(":")[1]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

        Thread thread1 = new Thread(){
           @Override
            public void run() {
               for(int i =0;i<3;i++) {
                   System.out.println("AAA");
               }
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                for(int i =0;i<3;i++) {
                    System.out.println("BBB");
                }
            }
        };

        Thread thread3 = new Thread(){
            @Override
            public void run() {
                for(int i =0;i<3;i++) {
                System.out.println("CCC");
                }
            }
        };

        //AtomicInteger replaces synchronized
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 1000; i++) {
           count.incrementAndGet();
        }
        System.out.println("Final count (with AtomicInteger): " + count.get());


        //Threads running in parallel
        /*t1.start();
        t2.start();
        t3.start();*/

        //Threads running one after another
        t1.start();
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        t3.join();

        //run one thread after another, thread1, thread2, thread3
        thread1.start();
        thread1.join();

        thread2.start();
        thread2.join();

        thread3.start();
        thread3.join();

        System.out.println("***Done***");

        //no guarantee of order
        /*thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();*/



        //Virtual Threads
        Runnable r = () -> System.out.println("New virtual thread");
        Thread.ofVirtual().start(r);
        Thread.sleep(1000);

        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()){
            Future<?> future = executorService.submit( () -> System.out.println("aaa"));
            future.get();
        }catch (Exception e ){
            e.printStackTrace();
        }

        Thread virtualThread = Thread.startVirtualThread(() -> {
            System.out.println("Running task in a virtual thread: "
                    + Thread.currentThread().getName());
        });
        try {
            virtualThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Traditional Thread vs Virtual Thread

        //--Traditional Thread---

        AtomicInteger atomicInteger = new AtomicInteger(1);
        Runnable traditionalTask = () -> {

            atomicInteger.getAndIncrement();
            try {
                Thread.sleep(Duration.ofMillis(10));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

        long start = System.currentTimeMillis();
        try(ExecutorService executorService = Executors.newFixedThreadPool(500)){
            for(int i =0;i<10000; i++) {
                executorService.submit(traditionalTask);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Response time Traditional Thread :: " + (System.currentTimeMillis() - start) + " ms");

        //--Virtual Thread---

        AtomicInteger atomicIntegerVirtual = new AtomicInteger(1);
        Runnable virtualThreadTask = () -> {

            atomicIntegerVirtual.getAndIncrement();
            try {
                Thread.sleep(Duration.ofMillis(10));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

        long startV = System.currentTimeMillis();
        //executor service that creates a new Virtual Thread for each task submitted to it
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()){
            for(int i =0;i<10000; i++) {
                executorService.submit(virtualThreadTask);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Response time Virtual Thread :: " + (System.currentTimeMillis() - startV) + " ms");


        threadLocalBook.set(new Book("My First Book", "First Author", 1984, "0395489318", 230));
        Book book = threadLocalBook.get();


    }

}
