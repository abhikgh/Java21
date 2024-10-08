package com.src.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntStreamEx {

	public static void main(String[] args) {

		// from 1 to 100, print prime numbers
		IntStream.rangeClosed(1, 100)
				 .filter(i -> checkPrime(i))
				 .forEach(System.out::println);

		// from 1-20 get all even numbers in a list
		List<Integer> listEven1To20 = new ArrayList<Integer>();
		IntStream.rangeClosed(1, 20)
				.filter(i -> i % 2 == 0)
				.forEach(i -> listEven1To20.add(i));
		System.out.println(listEven1To20);
		
		//Put 5 random numbers in a sorted list
		List<Integer> listS = new ArrayList<Integer>();
		IntStream.rangeClosed(1, 5)
		         .map(i -> new Random().nextInt())
		         .forEach(i -> listS.add(i));
		List<Integer> listS2 = listS.stream().sorted().collect(Collectors.toList());
		System.out.println(listS2);
		
		AtomicInteger x = new AtomicInteger(5);
		IntStream.rangeClosed(1, 360)
		         .forEach(i->x.getAndIncrement());
		System.out.println(x); //365
				 
		         
		

	}

	private static boolean checkPrime(Integer number) {
		if (number <= 1)
			return false;
		else
			return !IntStream.rangeClosed(2, number / 2).anyMatch(x -> number % x == 0);
	}

}
