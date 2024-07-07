package com.src.stream;

import java.io.File;
import java.util.Scanner;
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

    public static void main(String[] args) {
        
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

        Thread thread1 = new Thread(){
            @Override
            public void run() {
                System.out.println("In thread1...");
            }
        };
        thread1.start();

        Thread thread11 = new Thread(
                () -> System.out.println("In thread11...")
        );
        thread11.start();

        Thread myThread = new MyThread();
        myThread.start(); //Run in myThread...

        MyRunnbale myRunnbale = new MyRunnbale();
        Thread t = new Thread(myRunnbale);
        t.start(); //Run in myRunnable...


        //3 threads searching in parallel
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

        Thread t2 = new Thread() {

            @Override
            public void run() {
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
            }

        };

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


       /* Runnable r = () -> System.out.println("New virtual thread");
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
        }*/
    }

}
