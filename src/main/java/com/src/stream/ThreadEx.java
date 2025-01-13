package com.src.stream;

import java.io.File;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
