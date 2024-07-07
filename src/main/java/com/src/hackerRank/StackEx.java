package com.src.hackerRank;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Stream;

public class StackEx {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		List<Boolean> list = new ArrayList<Boolean>();
		int flag = 0;
		while (scanner.hasNext()) {
			String input = scanner.next();
			if (isBalanced(input))
				System.out.println("true");
			else
				System.out.println("false");

		}
	}

	private static boolean isBalanced(String input) {

		int length = input.length();
		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < length; i++) {
			char inp = input.charAt(i);
			if (Stream.of("(", "{", "[").anyMatch(n -> n.equals(String.valueOf(inp)))) {
				stack.push(inp);
			} else if (Stream.of(")", "}", "]").anyMatch(n -> n.equals(String.valueOf(inp)))) {
				if (stack.isEmpty()) {
					return false;
				} else {
					switch (String.valueOf(inp)) {
					case ")":
						if (!stack.pop().equals('(')) {
							return false;
						}
						break;
					case "}":
						if (!stack.pop().equals('{')) {
							return false;
						}
						break;
					case "]":
						if (!stack.pop().equals('[')) {
							return false;
						}
						break;
					default:
						break;
					}
				}
			}
		}
		return stack.isEmpty();
	}

}
