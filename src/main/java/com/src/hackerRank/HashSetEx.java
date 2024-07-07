package com.src.hackerRank;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class HashSetEx {

 public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int t = s.nextInt();
        String [] pair_left = new String[t];
        String [] pair_right = new String[t];
        
        for (int i = 0; i < t; i++) {
            pair_left[i] = s.next();
            pair_right[i] = s.next();
        }
        
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < t; i++) {
        	String input = pair_left[i].concat(" ").concat(pair_right[i]);
        	hashSet.add(input);
        	System.out.println(hashSet.size());
        }

//Write your code here

   }
}