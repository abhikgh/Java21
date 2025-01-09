package com.src.stream;

import com.src.model.Books;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
class ItemData{
	private String itemNo;
	private String itemType;
	private String buCode;
	private String buType;
}


public class MapEx {
	
	public static void main(String[] args) {
		
		Map<Integer,String> map = Map.of(1,"Mango", 2, "Apple", 3, "Banana"); //immutable map
		System.out.println(map.getOrDefault(1, "Lemon"));//Mango
		System.out.println(map.getOrDefault(12, "Lemon"));//Lemon //returns the defualt value , does not insert

		var t = Stream.of("abc","def","iop").anyMatch(x -> x.equals("def"));
		System.out.println(t); //true

		//Searching in multiple maps
		Map<String,String> map1 = new HashMap<>();
		map1.put("net-ip","333");
		map1.put("ip-address","132333");
		Map<String,String> map2 = new HashMap<>();
		map2.put("hostname","kekdkd");
		map2.put("ip-address","232233");
		map2.put("operating-system","win11");
		Map<String,String> map3 = new HashMap<>();
		map3.put("processor","intel");
		map3.put("ip-address","344322");
		map3.put("graphics","nvidia");
		String ipAddress = map1.getOrDefault("ip-address",map2.getOrDefault("ip-address", map3.getOrDefault("ip-address", "NA")));
		System.out.println("ip-address :: " + ipAddress);

	/*
		Illegal use of var

		var name; // error: cannot use 'var' on variable without initialization
		var emptyList = null; //error

		var expression = (String s) -> s.length() > 10; // error: lambda expression needs an explicit target-type

		var arrayList = { 1, 2, 3 }; // error: array initializer needs an explicit target-type

		*/

		String abc = null;
		String xyz = null;
		String pqr = "abc";

		if(Stream.of(abc,xyz).allMatch(Objects::isNull)) {
			System.out.println("null null"); //null null
		}

		if(Stream.of(abc,xyz,pqr).anyMatch(allowedValues -> "abc".equalsIgnoreCase(allowedValues))) {
			System.out.println("anymatch found");
		}

		List<String> list1 = null;
		List<String> list2 = new ArrayList<>();

		if(Stream.of(list1,list2).noneMatch(CollectionUtils::isEmpty)){
			System.out.println("1111111111111");
		}else{
			System.out.println("aaaaaaaaaaa"); //aaaaaaaaaaa
		}

		//check if the itemNo is present in the map, if present set the BUType = "Existing"
		//else insert a new itemNo with BUType = "New"
		ItemData itemData = new ItemData("123","ART","BE","RR");
		ItemData itemData2 = new ItemData("223","SPR","BE","RR");
		Map<String, ItemData> itemDataMap = new HashMap<>();
		itemDataMap.put("123",itemData);
		itemDataMap.put("223",itemData2);

		String itemNoToCheck = "779";

		if(itemDataMap.containsKey(itemNoToCheck)){
			itemDataMap.get(itemNoToCheck).setBuType("Existing");
		}else{
			ItemData itemNotPresent = new ItemData();
			itemNotPresent.setItemNo(itemNoToCheck);
			itemNotPresent.setBuType("New");
			itemDataMap.put(itemNoToCheck, itemNotPresent);
		}
		System.out.println("itemDataMap :: " + itemDataMap);
		//779 : {123=ItemData(itemNo=123, itemType=ART, buCode=BE, buType=RR), 223=ItemData(itemNo=223, itemType=SPR, buCode=BE, buType=RR), 779=ItemData(itemNo=779, itemType=null, buCode=null, buType=New)}
		//123 : {123=ItemData(itemNo=123, itemType=ART, buCode=BE, buType=Existing), 223=ItemData(itemNo=223, itemType=SPR, buCode=BE, buType=RR)}

		//computeIfAbsent
		// if key is present, return value
		// if key is absent, compute value for the key and put key-value in map
		itemDataMap.clear();
		itemDataMap.put("123",itemData);
		itemDataMap.put("223",itemData2);
		itemNoToCheck = "443";

		itemDataMap.computeIfAbsent(itemNoToCheck, itemNoNP -> {
			ItemData itemNotPresent = new ItemData();
			itemNotPresent.setItemNo(itemNoNP);
			itemNotPresent.setBuType("New");
			return itemNotPresent;
		});
		System.out.println("itemDataMap computeIfAbsent :: " + itemDataMap);

		//Immutable map
		Map<String,String> mapImmutable = Map.copyOf(map1);
		//mapImmutable.put("22","222"); //UnsupportedOperationException
		System.out.println(mapImmutable);

		//LinkedHashMap (maintains order of insertion, has a double-linked list to maintain the order of insertion)
		Map<String, String> countryCapitalMap = new LinkedHashMap<String, String>();
		countryCapitalMap.put("India", "Delhi");
		countryCapitalMap.put("Japan", "Tokyo");
		countryCapitalMap.put("France", "Paris");
		countryCapitalMap.put("Russia", "Moscow");
		System.out.println("linkedhashmap");
		System.out.println(countryCapitalMap);

		//sort the map by keys
		countryCapitalMap = countryCapitalMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> n, LinkedHashMap::new));
		System.out.println("mapSortedByKey :: " + countryCapitalMap); //{France=Paris, India=Delhi, Japan=Tokyo, Russia=Moscow}

		//sort the map by keys reversed
		countryCapitalMap = countryCapitalMap.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> n, LinkedHashMap::new));
		System.out.println("mapSortedByKeyReversed :: " + countryCapitalMap); //{Russia=Moscow, Japan=Tokyo, India=Delhi, France=Paris}

		//sort the map by values
		countryCapitalMap = countryCapitalMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> n, LinkedHashMap::new));
		System.out.println("mapSortedByValues :: " + countryCapitalMap); //{India=Delhi, Russia=Moscow, France=Paris, Japan=Tokyo}

		//sort the map by values reversed
		countryCapitalMap = countryCapitalMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n) -> n , LinkedHashMap::new));
		System.out.println("mapSortedByValuesReversed :: " + countryCapitalMap);

		//Count highest occurrences
		String test = "This is a test string with lots of words. Get the word with the highest occurrence";
		Map.Entry<String, Long> mapEntry = Arrays.stream(test.split(" "))
								.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
								.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
								.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n)->n, LinkedHashMap::new))
								.entrySet().iterator().next();
		System.out.println("highest occurring word :: " + mapEntry.getKey() + " with occurrence " + mapEntry.getValue());

		Integer[] intArr = new Integer[]{23, 345, 7373, 736362, 6262, 6262, 727272, 252636, 838383, 837272, 737373};
		Map.Entry<Integer, Long> mapEntry2 =Arrays.stream(intArr)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n)->n, LinkedHashMap::new))
				.entrySet().iterator().next();
		System.out.println("highest occurring number :: " + mapEntry2.getKey() + " with occurrence " + mapEntry2.getValue());

		// ConcurrentHashMap
		// Divides the map into 16 parts
		// Write is locked
		// Modifiable while iterating
		// No null key/value
		Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<Integer, String>();
		concurrentHashMap.put(1234, "asd");
		concurrentHashMap.put(2324, "awsd");
		concurrentHashMap.put(324, "asdfsd");
		concurrentHashMap.put(4334, "sdasd");
		concurrentHashMap.put(13, "sdfasd");
		concurrentHashMap.put(57, "htasd");
		concurrentHashMap.put(577, "jhjasd");
		concurrentHashMap.entrySet().forEach(entry -> {
			if(entry.getKey().equals(324)) {
				concurrentHashMap.put(324, "test"); //can be modified, since ConcurrentHashMap
			}
		});
		System.out.println("concurrentHashMap");
		concurrentHashMap.forEach((key,value) -> System.out.println(key + "--" + value)); // 324 - test

		//by speed
		/*
				1. HashMap
				2. ConcurrentHashMap
				3. Collections.synchronizedMap()
				4. HashTable
		 */

		//TreeMap :: sort the keys 
		TreeMap<Integer, String> treeMap = new TreeMap<>(concurrentHashMap);
		System.out.println("treeMap");
		treeMap.forEach((key,value) -> System.out.println(key + "--" + value));

		//map with key=bookname sorted
		List<Books> booksList = getAllBooks();
		Map<String, Books> bookMap = booksList.stream().collect(Collectors.toMap(Books::getName, book-> book, (o,n)->n , TreeMap::new));
		System.out.println("bookMap");
		System.out.println(bookMap);

		//NavigableMap
		// Map -> SortedMap -> NavigableMap -> TreeMap(C)
		NavigableMap<String, String> navigableMap = new TreeMap<String, String>(); 
		//instance of TreeMap so sorted by the keys
		navigableMap.put("X","xylophone");
		navigableMap.put("R","rabbit");
		navigableMap.put("B","balloon");
		navigableMap.put("A","aeroplane");
		navigableMap.put("K","kite");
		navigableMap.put("P","pencil");
		System.out.println("navigableMap");
		System.out.println(navigableMap); // A B K P R X

		//floorKey :: <= specifiedKey
		System.out.println(navigableMap.floorKey("R")); //R

		//lowerKey :: < specifiedKey
		System.out.println(navigableMap.lowerKey("R")); //P

		//specifiedKey :: <= ceilingKey
		System.out.println(navigableMap.ceilingKey("R")); //R

		//specifiedKey :: < higherKey
		System.out.println(navigableMap.higherKey("R")); //X

		//headMap :: from head of map < specified key
		NavigableMap<String, String> navigableMap2 =  navigableMap.headMap("K", false);
		System.out.println(navigableMap2); // {A=aeroplane, B=balloon}

		//headMap :: from head of map <= specified key
		NavigableMap<String, String> navigableMap3 =  navigableMap.headMap("K", true);
		System.out.println(navigableMap3); // {A=aeroplane, B=balloon, K=kite}

		//tailMap :: specified key < to tail of map
		NavigableMap<String, String> navigableMap4 =  navigableMap.tailMap("K", false);
		System.out.println(navigableMap4); // {P=pencil, R=rabbit, X=xylophone}

		//tailMap :: specified key <= to tail of map
		NavigableMap<String, String> navigableMap5 =  navigableMap.tailMap("K", true);
		System.out.println(navigableMap5); // {K=kite, P=pencil, R=rabbit, X=xylophone}

		//subMap
		NavigableMap<String, String> navigableMap6 =  navigableMap.subMap("B", true, "R", false);
		System.out.println(navigableMap6); // {B=balloon, K=kite, P=pencil}

		//subMap
		NavigableMap<String, String> navigableMap7 =  navigableMap.subMap("B", true, "R", true);
		System.out.println(navigableMap7); // {B=balloon, K=kite, P=pencil, R=rabbit}

		//subMap (default -> true, false)
		NavigableMap<String, String> navigableMap8 =  (NavigableMap<String, String>) navigableMap.subMap("B","R");
		System.out.println(navigableMap8); // {B=balloon, K=kite, P=pencil}

		//MultiValueMap (org.springframework.util)
	        //toMultiValueMap(Map<K, List<V>>)
	        //duplicate key not allowed
	        MultiValueMap<String, String> multiValueMap = CollectionUtils.toMultiValueMap(
	                Map.of("transaction-id", Collections.singletonList("123455"),
	                        "abc", Collections.singletonList("abcdef"),
	                         "cde", List.of("kdkd", "dkdkdkd")));
	        multiValueMap.entrySet().forEach(entry -> {
	            System.out.println(entry.getKey() + "--" + entry.getValue());
	        });
	        /*
	            abc--[abcdef]
	            cde--[kdkd, dkdkdkd]
	            transaction-id--[123455]
	         */
	        List<String> list = multiValueMap.get("cde");
	        System.out.println("list multiValueMap ::" + list); //list multiValueMap ::[kdkd, dkdkdkd]
	
	        //MultiValuedMap (org.apache.commons.collections4)
	        MultiValuedMap<String, String> multiValuedMap = new ArrayListValuedHashMap<>();
	        multiValuedMap.put("fruit", "mango");
	        multiValuedMap.put("fruit", "apple");
	        multiValuedMap.put("fruit", "litchi");
	        multiValuedMap.put("fruit", "banana");
	        System.out.println("multiValuedMap initial :: " + multiValuedMap);
	        //{fruit=[mango, apple, litchi, banana]}
	        multiValuedMap.removeMapping("fruit","litchi");
	        System.out.println("multiValuedMap final :: " + multiValuedMap);
	        //{fruit=[mango, apple, banana]}
	        List<String> fruits = multiValuedMap.get("fruit").stream().toList();
	        System.out.println("list multiValuedMap ::" + fruits); //list multiValuedMap ::[mango, apple, banana]

	}

	private static List<Books> getAllBooks() {
		return List.of(new Books("The FellowShip of the Ring", 1954, "234332", 3883),
				new Books("The Two Towers", 1955, "234332", 73737),
				new Books("The Return of the King", 1954, "234332", 7773),
				new Books("Harry Potter and the Philosopher's Stone", 1954, "234332",73773),
				new Books("And Then There Were None", 1954, "234332",636636));
	}

}
