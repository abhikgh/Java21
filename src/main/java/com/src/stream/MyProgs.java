package com.src.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class MyProgs {

    public static void main(String[] args) {

        //Shift an array to the right by k places
        int[] arr = new int[]{1,2,3,4,5,6};
        int k = 3;
        int[] xarr = reverseArr(arr, 0, arr.length-1);  //654321
        xarr = reverseArr(xarr, 0,k-1);  //456321
        xarr = reverseArr(xarr, k, arr.length-1); //456123
        System.out.println("Shifted array right by " + k + " places");
        for(int i=0;i< xarr.length;i++){
            System.out.print(xarr[i]); //456123
        }

        System.out.println();

        //check if a String has proper braces
        String input = "jdjjdjd{djdjdjjd[dhdhdhhdhdhd]djdjjss()djdjdjjsjsjsjdhd(Djdjsjjsjsnxn)}jdjxuudbxb{{jdjxbdbs}}djdjx(aa)";
        Stack<Character> characterStack = new Stack<>();
        int flag = 0;
        for (char c : input.toCharArray()) {
            if (List.of('(', '{', '[').contains(c)) {
                characterStack.push(c);
            } else if (List.of(')', '}', ']').contains(c)) {
                if (c == ')' && characterStack.pop() != '(') {
                    flag = 1;
                    break;
                } else if (c == '}' && characterStack.pop() != '{') {
                    flag = 1;
                    break;
                } else if (c == ']' && characterStack.pop() != '[') {
                    flag = 1;
                    break;
                }
            }
        }
        if(flag==0)
            System.out.println("formatted string");
        else
            System.out.println("unformatted string");

        //fibonacci till n
        int n = 10;
        // 0   1    2   3   4   5   6   7   8   9
        // 0   1    1   2   3   5   8   13  21  34
        for(int i=0;i<n;i++){
            System.out.print(fibonacci(i)+ " ");
        }
        System.out.println();

        //frequency map -sort by values descending
        arr = new int[]{23,993,993, 92, 19,23,774, 773,12, 34, 885,456,234,8848,93983,12,39494};
        Map<Integer, Integer> frequencyMap = new LinkedHashMap<>();
        for(int i:arr){
            if(frequencyMap.containsKey(i)){
                frequencyMap.put(i,frequencyMap.get(i)+1);
            }else{
                frequencyMap.put(i,1);
            }
        }
        frequencyMap = frequencyMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n1)->n1, LinkedHashMap::new));
        System.out.println(frequencyMap);

        //Binary search (to be done on a sorted array/list)
        arr = new int[]{23,993,993, 92, 19,23,774, 773,12, 34, 885,456,234,8848,93983,12,39494};
        int searchElement = 885;
        int index = doBinarySearch(arr, searchElement);
        System.out.println("Element found at index :: " + index);

        //get longest non-repeating substring in a String
        Map<Character, Integer> visited = new HashMap<>();
        String output = "";
        for (int start = 0, end = 0; end < input.length(); end++) {
            char currChar = input.charAt(end);
            if (visited.containsKey(currChar)) {
                start = Math.max(visited.get(currChar)+1, start);
            }
            if (output.length() < end - start + 1) {
                output = input.substring(start, end + 1);
            }
            visited.put(currChar, end);
        }
        System.out.println("output ::" + output); //ABCDEFG

    }

    private static int doBinarySearch(int[] arr, int searchElement) {
        Arrays.sort(arr);
        int left =0, right=arr.length-1;
        while(left <= right){
            int mid = left + (right-left)/2;
            if(searchElement == arr[mid]) {
                return mid;
            }
            else if(searchElement > arr[mid])
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    private static int fibonacci(int i) {
        if(i<=1)
            return i;

        return fibonacci(i-1)+fibonacci(i-2);
    }

    private static int[] reverseArr(int[] xarr, int i, int j) {
        while (i < j) {
            int temp = xarr[i];
            xarr[i] = xarr[j];
            xarr[j] = temp;
            i++;
            j--;
        }
        return xarr;
    }
}
