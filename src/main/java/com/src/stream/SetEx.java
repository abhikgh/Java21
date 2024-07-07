package com.src.stream;

import com.src.model.Fruits;
import com.src.model.Vegetables;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SetEx {

	public static void main(String[] args) {

		//HashSet
		/*
			How HashSet stores elements :: by storing the values as HashMap keys
			null key

			Interfaces
			---------------
			 1.Iterable
			 2.Collection
			 3. Set
			 4. Serializable
			 5. Cloneable

		 */

		//TreeSet
		/*
			TreeSet will keep the elements in their natural order
			no null key
		 */

		//NavigableSet
		NavigableSet<String> navigableSet = new TreeSet<String>();
		navigableSet.add("P");navigableSet.add("X");navigableSet.add("C");navigableSet.add("A");navigableSet.add("T");
		System.out.println(navigableSet); // A C P T X
		
		NavigableSet<String> navigableSet2 = navigableSet.headSet("P", false); 
		System.out.println(navigableSet2); // A C
		
		NavigableSet<String> navigableSet3 = navigableSet.headSet("P", true); 
		System.out.println(navigableSet3); // A C P
		
		NavigableSet<String> navigableSet4 = navigableSet.tailSet("P", false); 
		System.out.println(navigableSet4); // T X
		
		NavigableSet<String> navigableSet5 = navigableSet.tailSet("P", true); 
		System.out.println(navigableSet5); // P T X
		
		NavigableSet<String> navigableSet6 = (NavigableSet<String>) navigableSet.subSet("C", "T");
		System.out.println(navigableSet6); // C P
		
		NavigableSet<String> navigableSet7 = (NavigableSet<String>) navigableSet.subSet("C", true, "T" ,true);
		System.out.println(navigableSet7); // C P T

		Fruits apple = new Fruits("Apple", "All");
		Fruits banana = new Fruits("Banana", "All");
		Fruits mango = new Fruits("Mango", "Summer");
		List<Fruits> fruitsList = List.of(apple, banana, mango);

		Vegetables potato = new Vegetables("Potato", "All");
		Vegetables cabbage = new Vegetables("Cabbage", "Winter");
		Vegetables cauliflower = new Vegetables("Cauliflower", "Winter");
		Vegetables beans = new Vegetables("Beans", "Summer");
		List<Vegetables> vegetablesList = List.of(potato, cabbage, cauliflower, beans);

		List<Object> allList = new ArrayList<>();
		allList.addAll(fruitsList);
		Set<String> typesSet = fruitsList.stream().map(Fruits::getFruitType).collect(Collectors.toSet());
		Set<Vegetables> otherVeggies = vegetablesList.stream().filter(vegetables -> !typesSet.contains(vegetables.getVegetableType())).collect(Collectors.toSet());
		allList.addAll(otherVeggies);
		System.out.println(allList);
		//[Fruits(fruitName=Apple, fruitType=All), Fruits(fruitName=Banana, fruitType=All), Fruits(fruitName=Mango, fruitType=Summer), Vegetables(vegetableName=Cauliflower, vegetableType=Win






		
	}
}
