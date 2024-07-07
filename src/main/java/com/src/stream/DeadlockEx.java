package com.src.stream;

public class DeadlockEx {

	public static void main(String[] args) {

		String resource1 = "resource1";
		String resource2 = "resource2";

		Thread t1 = new Thread() {
			@Override
			public void run() {
				synchronized (resource1) {
					System.out.println(Thread.currentThread().getName() + resource1);
				}
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
				synchronized (resource2) {
					System.out.println(Thread.currentThread().getName() + resource2);
				}

			}
		};

		Thread t2 = new Thread() {
			@Override
			public void run() {
				synchronized (resource2) {
					System.out.println(Thread.currentThread().getName() + resource2);
				}

				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
				synchronized (resource1) {
					System.out.println(Thread.currentThread().getName() + resource1);
				}
			}

		};

		t1.start();
		try {
			t1.join(); //Solution to deadlock :: t1 to complete before main thread can run
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
	}

	/*
		Thread-0resource1
		Thread-0resource2
		Thread-1resource2
		Thread-1resource1


	 */

}
