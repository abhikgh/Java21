package com.src.hackerRank;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ScannerEx {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		Map<Integer, String> map = new HashMap<Integer, String>();
		int n =0;
		String line = "";
		while(scanner.hasNext()) {
			n++;
			map.put(n, scanner.nextLine());
		}
		
		map.forEach( (k,v) -> {
			System.out.println(k + " " + v);
		});
	}

}
