package com.src.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
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

		//happy numbers
		Scanner scanner = new Scanner(System.in);
       int startElement = scanner.nextInt();
       int endElement = scanner.nextInt();

		List<Integer> happys = new ArrayList<>();
       IntStream.rangeClosed(startElement, endElement)
                .filter(i -> checkIsHappy(i))
                .forEach(happys::add);
		System.out.println(happys);



		         
		

		System.out.println("------------Check happ nos --------------");
		Scanner scanner = new Scanner(System.in);
		int start = scanner.nextInt();
		int end = scanner.nextInt();
		List<Integer> happyList = new ArrayList<>();
		IntStream.rangeClosed(start, end)
				.filter(IntStreamEx::checkIsHappy)
				.forEach(happyList::add);
		System.out.println(happyList);

	}

	private static boolean checkIsHappy(int i) {
		while(i!=1 && i!=4){
			i = checkHappy(i);
		}
		return  i==1?true:false;
	}

	private static int checkHappy(int i) {
		int sum =0, rem = 0;
		while(i != 0){
			rem = i % 10;
			sum += rem*rem;
			i = i / 10;
		}
		return sum;


	private static boolean checkPrime(Integer number) {
		if (number <= 1)
			return false;
		else
			return !IntStream.rangeClosed(2, number / 2).anyMatch(x -> number % x == 0);
	}

}
