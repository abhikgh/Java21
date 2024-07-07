package com.src.stream;

import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;


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
	private CountDownLatch countdownLatch;

	@Override
	public synchronized void run() {
		try {
			Thread.sleep(1000);
			System.out.println("Running thread :: " + Thread.currentThread().getName());
			System.out.println("Completed thread :: " + Thread.currentThread().getName());
			countdownLatch.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class CountDownLatchEx {

	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(4);
		Worker worker = new Worker("worker", countDownLatch);
		Thread worker1 = new Thread(worker, "ws-1");
		Thread worker2 = new Thread(worker, "ws-2");
		Thread worker3 = new Thread(worker, "ws-3");
		Thread worker4 = new Thread(worker, "ws-4");

		worker1.start();
		worker2.start();
		worker3.start();
		worker4.start();

		try {
			countDownLatch.await(); //Causes the current thread to wait until the latch has counted down to zero
			System.out.println("main thread starts");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
