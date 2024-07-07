package com.src.hackerRank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class IsPrime {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		String n = bufferedReader.readLine();

		bufferedReader.close();

		BigInteger bigInteger = new BigInteger(n);
		boolean isPP = bigInteger.isProbablePrime(100);
		String s = isPP?"prime":"not prime";
		System.out.println(s);
	}
}
