package com.src.stream;

import lombok.AllArgsConstructor;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/*
   CyclicBarrier is when different threads wait for each other and when all have finished their execution, the result needs to be combined in the parent thread.
   CyclicBarrierEx.newBarrier.await() - on each of the threads and caller thread
*/
@AllArgsConstructor
class Computation extends Thread {
    public static int sum = 0;
    public static int product = 0;

    private String name;
    private Semaphore semaphore;

    public void run() {
        try {
            semaphore.acquire();
            System.out.println("Thread :: " + name + " acquires lock.");
            if (name.equals("sum")) {
                System.out.println("Sum running...");
                sum = 10 + 20;
            } else if (name.equals("product")) {
                System.out.println("Product running...");
                product = 2 * 3;
            }
            System.out.println("Sum of product and sum = " + (Computation.sum + Computation.product));
            semaphore.release();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}

  
public class CyclicBarrierEx {
	
	public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
		Computation sum = new Computation("sum", semaphore);
        Computation product = new Computation("product", semaphore);

        //first sum then product
        sum.start();
        sum.join();

        product.start();
        product.join();

		System.out.println("Sum of product and sum final = " + (Computation.sum + Computation.product));
		
	}

}
