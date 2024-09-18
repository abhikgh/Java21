package com.src.stream;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public static void main(String[] args) throws InterruptedException {
        
        //Runnable is a FunctionalInterface - run()
        //Thread implements Runnable

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

        //1. Run Thread as Lambda
        Runnable threadLambda = () -> {
            System.out.println("Running thread as lambda..");
        };
        threadLambda.run();

        //2. Run Thread as new Thread(runnable)
        Thread thread = new Thread(() -> {
            System.out.println("Running thread as lambda..");
        });
        thread.start();
        
        //3. Run Thread as Anonymous Inner class
        Thread threadAnonymous = new Thread(){
            @Override
            public void run() {
                System.out.println("Running thread as Anonymous Inner class...");
            }
        };
        threadAnonymous.start();

        //4. Run thread as Object
        Thread myThread = new MyThread();
        myThread.start(); //Run in myThread...

        //Threads searching in parallel
        AtomicBoolean isFound = new AtomicBoolean(false);
        Thread t1 = new Thread() {

            @Override
            public void run() {
                try (Scanner scanner = new Scanner(new File("src/main/resources/file1.csv"))) {
                    scanner.useDelimiter(",");
                    while (!isFound.get() && scanner.hasNext()) {
                        String elem = scanner.next();
                        if (elem.contains("ip")) {
                            isFound.set(true);
                            System.out.println(elem.split(":")[1]);
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
                while (!isFound.get() && scanner.hasNext()) {
                    String elem = scanner.next();
                    if (elem.contains("ip")) {
                        isFound.set(true);
                        System.out.println(elem.split(":")[1]);
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
                    while (!isFound.get() && scanner.hasNext()) {
                        String elem = scanner.next();
                        if (elem.contains("ip")) {
                            isFound.set(true);
                            System.out.println(elem.split(":")[1]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

        t1.start();
        t2.start();
        t3.start();

        Thread virtualThread = Thread.startVirtualThread(() -> {
            System.out.println("Running task in a virtual thread: "
                    + Thread.currentThread().getName());
        });
        try {
            virtualThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runnable r = () -> System.out.println("New virtual thread");
        Thread.ofVirtual().start(r);
        Thread.sleep(1000);

        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()){
            Future<?> future = executorService.submit( () -> System.out.println("aaa"));
            future.get();
        }catch (Exception e ){
            e.printStackTrace();
        }


    }

}
