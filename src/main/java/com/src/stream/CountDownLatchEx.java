package com.src.stream;

import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;


/*
	CountDownLatch is for threads to run 1 after another 

	synchronized run
 
 	CountDownLatch countDownLatch = new CountDownLatch(4);
  	countdownLatch.countDown(); //on all 4 threads then main thread will continue
   	countDownLatch.await(); //Causes the current thread to wait until the latch has counted down to zero
	
*/

@AllArgsConstructor
class Worker extends Thread {
	private String name;
	private Semaphore semaphore;

	@Override
	public synchronized void run() {
		try {
			semaphore.acquire();
			Thread.sleep(1000);
			System.out.println("Running thread :: " + name);
			System.out.println("Completed thread :: " + name);
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class CountDownLatchEx {

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(1);
		Worker worker1 = new Worker("worker1", semaphore);
		Worker worker2 = new Worker("worker2", semaphore);
		Worker worker3 = new Worker("worker3", semaphore);
		Worker worker4 = new Worker("worker4", semaphore);

		//4 threads will complete in any order, but when 1 starts it will complete first
		worker1.start();
		worker2.start();
		worker3.start();
		worker4.start();

		try {
			worker1.join();
			worker2.join();
			worker3.join();
			worker4.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
