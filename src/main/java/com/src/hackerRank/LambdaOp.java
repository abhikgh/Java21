package com.src.hackerRank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

@FunctionalInterface
interface PerformOperation {
	public boolean performOperation(int a); 
}

public class LambdaOp {
	
	private static PerformOperation isOdd() {
		return (a) -> a%2==1;
	}

	private static PerformOperation isPrime() {
		return (a) -> {
		if (a <= 1)
			return false;
		else
			return !IntStream.rangeClosed(2, a / 2).anyMatch(i -> a % i == 0);
		};
	}
	
	private static PerformOperation isPalindrome() {
	   return (a) -> {
		String num = String.valueOf(a);
		String reverseNum = new StringBuilder(num).reverse().toString();
	    return num.equals(reverseNum);
	   };
	}

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int count = Integer.parseInt(bufferedReader.readLine());
		while(count-->0) {
			String line = bufferedReader.readLine();
			StringTokenizer stringTokenizer = new StringTokenizer(line);
			Integer condition = Integer.parseInt(stringTokenizer.nextToken());
			Integer numToCheck = Integer.parseInt(stringTokenizer.nextToken());
			switch (condition) {
			case 1:
				boolean b = isOdd().performOperation(numToCheck);
				String s = b==true?"ODD":"EVEN";
				System.out.println(s);
				break;
			case 2:
				boolean b2 = isPrime().performOperation(numToCheck);
				String s2 = b2==true?"PRIME":"COMPOSITE";
				System.out.println(s2);
				break;
			case 3:
				boolean b3 = isPalindrome().performOperation(numToCheck);
				String s3 = b3==true?"PALINDROME":"NOT PALINDROME";
				System.out.println(s3);
				break;
			default:
				break;
			}
		}
		
	}
	
}
