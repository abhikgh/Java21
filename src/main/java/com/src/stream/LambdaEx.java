package com.src.stream;

import org.cactoos.Func;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Interface with only 1 abstract method (can have default,static, overridden methods)

@FunctionalInterface
interface MathsOperation {
	int mathsOp(int a, int b);
}

//Lambda is an anonymous function that implements FunctionalInterface
//Lambda variables are final 
//Lambda can be passed to a function which accepts FunctionalInterface
//If the return type of a method is FunctionalInterface then it must return the lambda implementation
		
/*
	@FunctionalInterface
	interface MathsOperation {
		int mathsOp(int a, int b);
	}		
	public static MathsOperation getAddition(){
		return (a,b) -> a+10*b;
	}
 	int lambdaOp = getAddition().mathsOp(20, 30);
*/


public class LambdaEx {

	public static void main(String[] args) {

		//Lambda is an anonymous function that implements FunctionalInterface
		MathsOperation addition = (a, b) -> a + b;  //lambda
		System.out.println(addition.mathsOp(10, 5)); // 15

		//Lambda variables are final / effectively final - to reduce concurrency issues
		List<String> list = Stream.of("abc", "def", "mno", "xyz").toList();
		AtomicInteger i = new AtomicInteger(0);
		list.forEach(s -> {
			System.out.print(i + ":" + s + ":"); //0-abc:1-def:2-mno:3-xyz:
			i.getAndIncrement();
		});
		System.out.println();

		/*int[] arr = new int[]{0};
		list.forEach(elm -> {
			System.out.print(arr[0] + ":" + elm + ":"); //0:abc:1:def:2:mno:3:xyz:
			arr[0]++;
		});*/

		//passing lambda to a function (which will accept FunctionalInterface)
		Function<Integer, Integer> function1 = x -> x *10;
		int j1 = testF(10, function1);
		System.out.println("function1 ::" + j1);  //100
		Function<Integer, Integer> function2 = x -> x+10;
		int j2 = testF(10, function2);
		System.out.println("function2 ::" + j2); //20
		Function<Integer, Integer> function3 = x -> x+100;
		int j3 = testF(10, function3);
		System.out.println("function 3 :: " + j3); //110

		//passing lambda to a function (which will accept FunctionalInterface)
		Function<String, String> stringFunction1 = str -> new StringBuffer(str.toUpperCase()).reverse().toString();
		String outputStr = testStr("abcd", stringFunction1);
		System.out.println("outputStr :: " + outputStr); //DCBA
		Function<String, String> stringFunction2 = str -> str.toLowerCase();
		String outputStr2 = testStr("ABcd", stringFunction2);
		System.out.println("outputStr2 :: " + outputStr2); //abcd

		//Method which returns Functional Interface must return the lambda implementation
		int lambdaOp = getAddition().mathsOp(20, 30);
		System.out.println("lambdaOp :: " + lambdaOp); //320

		//Function
		Function<Integer, Integer> functionA = x -> x *10;
		int y = functionA.apply(10);
		System.out.println("function :: " + y); //100

		//BiFunction :: Functional interface with 2 inputs
		/*
			@FunctionalInterface
			public interface BiFunction<T, U, R> {
				R apply(T t, U u);
			}
       */
		BiFunction<Integer, Integer, Integer> biFunction = (x1, x2) -> x1 + x2;
		Integer resultBF = biFunction.apply(2, 3);
		System.out.println("resultBF :: " + resultBF); // 5

		List<String> list1 = Arrays.asList("a", "b", "c");
		List<Integer> list2 = Arrays.asList(1, 2, 3);
		List<String> result = listCombiner(list1, list2, (str, num) -> str+num);
		System.out.println("result 1 :: " + result); //result 1 :: [a1, b2, c3]

		List<Double> list12 = Arrays.asList(1.0d, 2.1d, 3.3d);
		List<Float> list22 = Arrays.asList(0.1f, 0.2f, 4f);
		List<Boolean> result2 = listCombiner(list12, list22, (doN, flN) -> doN  > flN);
		System.out.println("result2 :: " + result2); //result2 :: [true, true, false]

		//BinaryOperator
		/*
			@FunctionalInterface
			public interface BinaryOperator<T> extends BiFunction<T,T,T> {

			}
		 */
		//Functional interface that extends BiFunction
		//same-type
		BinaryOperator<Integer> binaryOperator = (x1, x2) -> x1 + x2;
		Integer resultBO = binaryOperator.apply(2, 3);
		System.out.println("resultBO :: " + resultBO); // 5

		//Higher-Order Function
		//Function as input &/ output
		BinaryOperator<Integer> add = (a, b) -> a + b;
		BinaryOperator<Integer> subtract = (a, b) -> a - b;
		BinaryOperator<Integer> multiply = (a, b) -> a * b;

		int x1 = 10;
		int y1 = 5;
		int resultAdd = applyOperation(x1, y1, add);
		int resultSubtract = applyOperation(x1, y1, subtract);
		int resultMultiply = applyOperation(x1, y1, multiply);
		System.out.println("Addition: " + resultAdd); // Output: Addition: 15
		System.out.println("Subtraction: " + resultSubtract); // Output: Subtraction: 5
		System.out.println("Multiplication: " + resultMultiply); // Output: Multiplication: 50

		//Composite Function
		//Function chaining - output of 1 function is input of the next function
		//Function.andThen
		Function<Integer, Integer> addTwo = xy -> xy+2;
		Function<Integer, Integer> multiplyByThree = xy -> xy*3;
		Function<Integer, Integer> compositeFunction = addTwo.andThen(multiplyByThree);
		Integer resultComposite = compositeFunction.apply(5);
		System.out.println("resultComposite :: " + resultComposite); //21

	}

    /*
		@FunctionalInterface
		interface MathsOperation {
			int mathsOp(int a, int b);
		}
     */
	public static MathsOperation getAddition(){
		return (a,b) -> a+10*b;
	}

	// Higher-Order Function
	 public static Integer testF(Integer x, Function<Integer, Integer> function){
		return function.apply(x);
	 }

	 public static String testStr(String input, Function<String, String> function){
		return function.apply(input);
	 }

	// Higher-Order Function
	public static int applyOperation(int a, int b, BinaryOperator operation) {
		return (int) operation.apply(a, b);
	}

	//BiFunction
	private static <T,U, R> List<R> listCombiner(List<T> list1, List<U> list2, BiFunction<T,U,R> biFunction){
		List<R> resultList = new ArrayList<>();
		for(int i=0;i<list1.size();i++){
			resultList.add(biFunction.apply(list1.get(i), list2.get(i)));
		}
		return resultList;
	}

}
