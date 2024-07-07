package com.src.hackerRank;

import java.util.Scanner;

interface Food {
	public String getType();
}

class Pizza implements Food {
	public String getType() {
		return "Someone ordered a Fast Food!";
	}
}

class Cake implements Food {

	public String getType() {
		return "Someone ordered a Dessert!";
	}
}

class FoodFactory {
	public Food getFood(String order) {
		if (order.equalsIgnoreCase("cake")) {
			return new Cake();
		} else if (order.equalsIgnoreCase("pizza")) {
			return new Pizza();
		}
		return null;
	}

}

public class FactoryEx {

	public static void main(String args[]) {

		try {

			Scanner sc = new Scanner(System.in);
			// creating the factory
			FoodFactory foodFactory = new FoodFactory();

			// factory instantiates an object
			Food food = foodFactory.getFood(sc.nextLine());

			System.out.println("The factory returned " + food.getClass());
			System.out.println(food.getType());
		} catch (Exception e) {
			System.out.println("Unsuccessful Termination!!");
		}
	}

}
