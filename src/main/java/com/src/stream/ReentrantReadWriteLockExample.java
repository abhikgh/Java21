package com.src.stream;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample {

    private static final ReentrantReadWriteLock reEntrantRWLock = new ReentrantReadWriteLock(true);

    private static String message = "a";

    // Main driver method
    public static void main(String[] args)
            throws InterruptedException
    {

        Thread t1 = new Thread(){
            @Override
            public void run() {


                reEntrantRWLock.writeLock().lock();
                message = message.concat("a");
                reEntrantRWLock.writeLock().unlock();

                reEntrantRWLock.readLock().lock();
                System.out.println("Message is " + message);
                reEntrantRWLock.readLock().unlock();
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {

                reEntrantRWLock.writeLock().lock(); // no other threads can enter
                message = message.concat("b");
                reEntrantRWLock.writeLock().unlock();

                reEntrantRWLock.readLock().lock();  //other threads can get readLock provided they dont have writeLock
                System.out.println("Message is " + message);
                reEntrantRWLock.readLock().unlock();
            }
        };

        // Starting threads with help of start() method
        t1.start();
        t2.start();

    }


}