package com.src.stream;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
   CyclicBarrier is when different threads wait for each other and when all have finished their execution, the result needs to be combined in the parent thread.
   CyclicBarrierEx.newBarrier.await() - on each of the threads and caller thread
*/

class Computation1 extends Thread {
    public static int product = 0;
    public void run()
    {
        product = 2 * 3;
        try {
        	CyclicBarrierEx.newBarrier.await();
        }
        catch (InterruptedException 
               | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class Computation2 extends Thread {
    public static int sum = 0;
    public void run()
    {
        sum = 10 + 20;
        try {
        	CyclicBarrierEx.newBarrier.await();
        }
        catch (InterruptedException
               | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
  
public class CyclicBarrierEx {
	
	public static CyclicBarrier newBarrier = new CyclicBarrier(3);
	
	public static void main(String[] args) {
		Computation1 client1 = new Computation1();
		Computation2 client2 = new Computation2();
		
		Thread t1 = new Thread(client1, "t1"); //Thread(Runnable target, String name)
		Thread t2 = new Thread(client2, "t2");
		
		t1.start();
		t2.start();
		
		try {
			CyclicBarrierEx.newBarrier.await(); //Waits until all parties have invoked await on this barrier.
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		System.out.println("Sum of product and sum = " + (Computation1.product + Computation2.sum));
		
	}

}
