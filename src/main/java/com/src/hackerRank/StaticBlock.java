package com.src.hackerRank;

import java.util.Scanner;

public class StaticBlock {
	
	static int B = 0;
	static int H = 0;
	static boolean flag = false;
	
	static {
	
		Scanner scanner = new Scanner(System.in);
		B = scanner.nextInt();
		H = scanner.nextInt();
		flag = B<=0||H<=0?false:true;
		if(!flag) {
			System.out.println("java.lang.Exception: Breadth and height must be positive");
		}
	}
	
	public static void main(String[] args){
		if(flag){
			int area=B*H;
			System.out.print(area);
		}
		
	}//end of main

}


