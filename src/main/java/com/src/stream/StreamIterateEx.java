package com.src.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamIterateEx {
	private static List<Integer> list = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		
		//Print 1 to 10
		Stream.iterate(1, n->n+1)
			  .limit(10)
			  .forEach(System.out::println); // 1 2 3 4 5 6 7 8 9 10
		
		System.out.println("**********************");
		
		//From 1 to 10, if even then print "even", if odd then print "odd"
		Stream.iterate(1, n->n+1)
		      .map(i -> i%2==0?"even":"odd")
		      .limit(10)
		      .forEach(System.out::println);
		
		System.out.println("**********************");
		
		//Print 10 random numbers between 1 and 100
		Stream.iterate(1, n->n+1)
			  .map(i -> (int)(Math.random()*100))
			  .limit(10)
			  .forEach(System.out::println);
		
		System.out.println("**********************");
		
		//Print 10 random numbers between 200 and 400
		Stream.iterate(1, n->n+1)
			  .map(i -> (int)(Math.random()*200 + 200))
			  .limit(10)
			  .forEach(System.out::println);
		
		System.out.println("**********************");
		
		//Print 10 random strings of length 6
		Stream.iterate(1, n->n+1)
		  .map(i -> getRandomString())
		  .limit(10)
		  .forEach(System.out::println);
		
		System.out.println("**********************");
		System.out.println("Print 20 fibonacci numbers");
		//Print 20 fibonacci numbers
		Stream.iterate(0, n->n+1)
		      .map(StreamIterateEx::fibonacci)
		      .limit(20)
		      .forEach(System.out::println);

		System.out.println("**********************");
		System.out.println("Print 20 fibonacci numbers Dynamic Programming");
		//Print 20 fibonacci numbers Dynamic Programming
		Stream.iterate(0, n->n+1)
				.map(StreamIterateEx::fibonacciDP)
				.limit(20)
				.forEach(System.out::println);
		
		System.out.println("**********************");
		Stream.iterate("", str-> str+"x")
		      .limit(3)
		      .map(str -> str+"y")
		      .forEach(System.out::println); //y xy xxy
		
		System.out.println("**********************");
		//Print 30 prime numbers
		Stream.iterate(0, n->n+1)
		.filter(i -> checkPrime(i))
		.limit(30)
		.forEach(System.out::println);
		
		//Print 5 random numbers
		Stream.iterate(1, n->n+1)
		      .map(i -> new Random().nextInt())
		      .limit(5)
		      .forEach(System.out::println);
				
		
	}
	
	
	private static boolean checkPrime(Integer number) {
		if(number<=1)
			return false;
		else
			return !IntStream.rangeClosed(2, number/2).anyMatch(x -> number%x==0);
	}


	private static String getRandomString() {
		int n = 6;
		String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < n; i++) {
			stringBuffer.append(allowedChars.charAt((int)(Math.random()*allowedChars.length())));
		}
		return stringBuffer.toString();
	}
	
	
	private static Integer fibonacci(int n) {
		if(n<=1) {
			return n;
		}
		else {
			return fibonacci(n-1) + fibonacci(n-2);
		}
	}

	private static Integer fibonacciDP(int n) {
		int[] arr = new int[n+1];
		Arrays.fill(arr, -1);
		return helper(n, arr);
	}

	private static Integer helper(int n, int[] arr) {
		if(n<=1) {
			return n;
		}
		if(arr[n] != -1){
			return arr[n];
		}
		arr[n] = helper(n-1, arr) + helper(n-2, arr);
		return arr[n];
	}

}
