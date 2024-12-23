package com.src.stream;

import java.util.concurrent.locks.ReentrantLock;


/*
    ReentrantLock allows a thread to acquire an object lock multiple times
    Fair locking - new ReentrantLock(true);	// the thread that has waited the maximum time for the object lock gets the lock
    reentrantLock.lock()
    reentrantLock.tryLock(10,TimeUnit.SECONDS)
    reentrantLock.lockInterruptibly()   //current thread is waiting for lock but some other thread requests the lock, then the current thread will be interrupted and return                                        immediately
    reentrantLock.unlock()

    synchronized                        vs                              ReentrantLock
    --------------------------------------------------------------------------------------
    unstructured - do not need a block structure
    lock can be held across methods
 */

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

/*
    thread1 running now
    thread1 exiting
    thread2 running now
    thread2 exiting
    thread3 running now
    thread3 exiting
    thread4 running now
    thread4 exiting

 */