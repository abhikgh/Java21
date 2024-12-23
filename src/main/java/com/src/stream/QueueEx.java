package com.src.stream;

import com.src.model.Student;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class QueueEx {

    public static void main(String[] args) {

        //PriorityQueue
		/*
			Each element has a "priority" associated with it.(Natural ordering or Comparator)
			In a PriorityQueue, an element with high priority is served before an element with low priority.
			Since PriorityQueue is not thread-safe, so java provides PriorityBlockingQueue class that implements the BlockingQueue interface to use in java multithreading environment.
			No null value
			O(log(n))
		 */

        Queue<Integer> priorityQueue = new PriorityQueue<>();

        priorityQueue.add(12);
        priorityQueue.add(120);
        priorityQueue.add(22);
        priorityQueue.add(5);
        priorityQueue.add(60);
        System.out.println(priorityQueue);    // 5 12 22 60 120

        //get the first element
        Integer firstElem = priorityQueue.peek();
        System.out.println(firstElem); //5
        System.out.println(priorityQueue); // [5, 12, 22, 120, 60]

        //get the first element and remove it
        Integer firstElmRemovedByPoll = priorityQueue.poll();
        System.out.println(firstElmRemovedByPoll); // 5
        System.out.println(priorityQueue); // [12, 22, 120, 60]

        //removes first element
        Integer firstElemRemoved = priorityQueue.remove();
        System.out.println(firstElemRemoved); //12
        System.out.println(priorityQueue); // [22, 120, 60]

        //Difference between poll() and remove() : When queue is empty - poll() returns null, but remove() throws Exception

        //Scan each student and get the highest marks
        Queue<Student> studentPriorityQueue = new PriorityQueue<>(new StudentComparator());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("END")) {
                break;
            }
            Student student = new Student();
            String[] strArr = input.split(" ");
            student.setFullName(strArr[0]);
            student.setTotalMarks(Integer.parseInt(strArr[1]));
            studentPriorityQueue.add(student);
        }
        System.out.println("Student with highest marks :: " + studentPriorityQueue.poll());
        //PriorityBlockingQueue
		/*
			BlockingQueue (Interface) - Thread-safe queue for Producer/Consumer problem
			Blocking if either queue is full and thread wants to put in it OR queue is empty and thread wants to remove from it
            implementations : PriorityBlockingQueue, ArrayBlockingQueue, LinkedBlockingQueue

            Blocking methods:: methods which perform the task without relinquishing the Object lock
            sleep(),wait(), join(), BlockingQueue.put(), BlockingQueue.take()
		 */

        BlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        final int[] count = {10};
        Thread producer = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    while(count[0]-- >0) {
                        int produced = (int) (Math.random() * 100);
                        priorityBlockingQueue.put(produced);
                        System.out.println("Produced :: " + produced);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread consumer = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    while(count[0]-- >0) {
                        int consumed = priorityBlockingQueue.take();
                        System.out.println("Consumed :: " + consumed);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        producer.start();
        consumer.start();


    }

}
