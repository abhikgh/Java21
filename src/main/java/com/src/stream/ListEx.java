package com.src.stream;


import com.src.model.Books;
import com.src.model.Department;
import com.src.model.Emp;
import com.src.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
//import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
class TaxationDetail {
    private String taxType;
    private TaxAmounts taxAmounts;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class TaxAmounts {
    private TaxationAmounts taxationAmounts;
    private String id;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class TaxationAmounts {
    private BigDecimal unitTaxAmount;
    private BigDecimal taxRate;
}

@Data
@AllArgsConstructor
class Fruit{
    private String name;
    private String colour;
    private String season;
}

@Data
class SummerFruit{
    private String name;
    private String colour;
    private String season;
    private Integer price;
}

@Getter
@Setter
@AllArgsConstructor
@ToString
class ABCE {
    private String firstName;
    private String lastName;
    private int age;
}

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
class Employee1 {

    private String name;
    private int age;
    private int salary;

}

@Data
@AllArgsConstructor
class Service {
    private String serviceId;
    private String serviceName;
    private String serviceType;
    private Integer price;
}

@Getter
@Setter
@AllArgsConstructor
class SICExceptionTrace {
    private String responseCode;
    private String responseMessage;
}


public class ListEx {

    public static void main(String[] args) {

        List<String> list = List.of("aaa","bbb","ccc"); //immutable list

        //orElse() is always executed
        //orElseGet(supplier) is conditionally executed - when Optional value is null

        //first element in list
        String firstElement = Optional.ofNullable(list)
                            .orElseGet(Collections::emptyList)
                            .stream()
                            .reduce((first, second) -> first)   //(or findFirst())
                            .orElseGet(() -> "NA");
        System.out.println("firstElement :: " + firstElement); //ccc

        //last element in list
        String lastElement = list.stream()
                                 .reduce((first, second) -> second)
                                 .orElseGet(() -> "NA");
        System.out.println("lastElement :: " + lastElement); //ccc

        //All elements follow rule
        SICExceptionTrace sic1 = new SICExceptionTrace("Success","200 OK");
        SICExceptionTrace sic2 = new SICExceptionTrace("Success","200 OK");
        SICExceptionTrace sic3 = new SICExceptionTrace("Failure","404 Not OK");
        List<SICExceptionTrace> sicExceptionTraces = Arrays.asList(sic1,sic2,sic3);
        boolean allSuccess = sicExceptionTraces.stream()
                .map(sicException -> sicException.getResponseCode().equalsIgnoreCase("Success"))
                .reduce(Boolean::logicalAnd)
                .orElse(false);
        System.out.println("allSuccess :: " + allSuccess); //false

        //add
        List<TaxationDetail> taxationDetailsList = getTaxationDetailsList();
        BigDecimal finalTaxAmount = taxationDetailsList.stream().filter(taxationDetail ->
                        Objects.nonNull(taxationDetail) &&
                                Objects.nonNull(taxationDetail.getTaxAmounts()) &&
                                Objects.nonNull(taxationDetail.getTaxAmounts().getTaxationAmounts()) &&
                                Objects.nonNull(taxationDetail.getTaxAmounts().getTaxationAmounts().getUnitTaxAmount()))
                .map(taxationDetail -> taxationDetail.getTaxAmounts().getTaxationAmounts().getUnitTaxAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("finalTaxAmount :: " + finalTaxAmount); //1000.92

        //largest int
        List<Integer> integerList = List.of(23, 34, 56,231, 36);
        Integer highestInt = integerList.stream().max(Integer::compareTo).get();
        System.out.println("highestInt :: "  +highestInt);  //231

        //largest number
        List<Number> numberList = List.of(23.09f, 23L, 123.34d);
        Number highestNumber = numberList.stream().map(Number::doubleValue).max(Double::compareTo).get();
        System.out.println("highestNumber :: " + highestNumber);  //123.34

        //Largest word
        List<String> words = Arrays.asList("GFG", "Geeks", "for", "GeeksQuiz", "GeeksforGeeks");
        String largestWord = words.stream().reduce((word1, word2) -> word1.length()>word2.length() ? word1:word2).orElse(null);
        System.out.println("largestWord :: " + largestWord);

        //list distinct
        //Arrays.asList - cannot add /remove from the list
        //List.of       - cannot add /remove from the list
        List<String> listColours1 = new ArrayList<>();
        listColours1.addAll(Stream.of("red","blue","green","yellow", "red").collect(Collectors.toList()));
        List<String> listColours2 = new ArrayList<>();
        listColours2.addAll(Stream.of("blue","orange","white","black", "blue").collect(Collectors.toList()));

        listColours1.addAll(listColours2);
        listColours1 = listColours1.stream().distinct().sorted().collect(Collectors.toList());
        System.out.println("distinct list :: " + listColours1);

        //list from list , and set some variable in the new list
        List<Fruit> allFruits = getAllFruits();
        ModelMapper modelMapper = new ModelMapper();
        List<SummerFruit> summerFruits = allFruits.stream().filter(fruit -> fruit.getSeason().equalsIgnoreCase("summer"))
                .map(fruit -> {
                    SummerFruit summerFruit = modelMapper.map(fruit, SummerFruit.class);
                    summerFruit.setPrice(10);
                    return summerFruit;
                }).toList();
        System.out.println("summerFruits :: " + summerFruits);
        //[SummerFruit(name=mango, colour=yellow, season=summer, price=10), SummerFruit(name=litchi, colour=red, season=summer, price=10)]

        //replace element in a list - 1
        allFruits = getAllFruits();
        allFruits.replaceAll(fruit -> fruit.getName().equalsIgnoreCase("Litchi") ? getNewFruit(fruit): fruit);
        System.out.println("List after replacement 1:: " + allFruits);

        //replace element in a list - 2
        allFruits = getAllFruits();
        ListIterator<Fruit> listIteratorU = allFruits.listIterator();
        while(listIteratorU.hasNext()) {
            Fruit fruit = listIteratorU.next();
            if(fruit.getName().equalsIgnoreCase("litchi")){
                fruit.setSeason("all");
            }
        }
        System.out.println("List after replacement 2:: " + allFruits);

        //remove from list
        allFruits = getAllFruits();
        List<Fruit> allFruits2 = new ArrayList<>(allFruits);
        ListIterator<Fruit> listIteratorR = allFruits2.listIterator();
        while (listIteratorR.hasNext()) {
            Fruit fruit = listIteratorR.next();
            if(fruit.getName().equalsIgnoreCase("litchi")){
                listIteratorR.remove();
            }
        }
        System.out.println("List after removal: " + allFruits2);

        //Given a list of numbers, square them and filter the numbers which are greater 10000 and then find average of them
        Integer[] arr=new Integer[]{100,24,13,44,114,200,40,112};
        List<Integer> list2 = Arrays.asList(arr);
        Double avg = list2.stream().map(i -> i*i).filter(i-> i>10000).collect(Collectors.averagingInt(i->i));
       // BigDecimal bigDecimalAvg = new BigDecimal(avg).setScale(2, RoundingMode.HALF_UP);
     //   System.out.println("average in BigDecimal:: " + bigDecimalAvg); // 21846.67

        //from given numbers, double the even numbers and sum them
        List<Integer> list3 = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        int sum = list3.stream().filter(i -> i % 2 == 0).map(i -> i * 2).mapToInt(v -> v).sum();
        System.out.println("final sum " + sum);   //60

        //Sum of squares of odd numbers in the list
        List<Integer> listNumbers2 = Arrays.asList(5,10,20,40,15,10,8,9,7);
        int sum2 = listNumbers2.stream().filter(i -> i % 2 == 1).map(i -> i * i).collect(Collectors.summingInt(v->v));
        System.out.println(sum2); //380

        //Stream
        //data pipeline - passes a collection of data through a series of operations - produces new Stream - lazy loaded

        //Do not Use Parallel Stream  as it is NOT Thread-Safe , also order is NOT maintained
        List<String> myList = Arrays.asList("aaa", "nads", "akds", "fdsdf", "kjlkj");

        myList = myList.parallelStream()
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());

        System.out.println(myList); //[AAA, AKDS, FDSDF, KJLKJ, NADS]

        List<ABCE> listPersons = new ArrayList<>();
        ABCE abce1 = new ABCE("Ruri", "Muku", 25);
        ABCE abce2 = new ABCE("Awadh", "Bonku", 36);
        ABCE abce3 = new ABCE("Luru", "Gomex", 27);
        ABCE abce4 = new ABCE("Pichku", "Lulu", 37);
        ABCE abce5 = new ABCE("Bombo", "Toto", 56);
        ABCE abce6 = new ABCE("Bombo", "Roro", 23);
        ABCE abce7 = new ABCE("Bonka", "Gomex", 28);
        ABCE abce8 = new ABCE("Gombo", "Lolo", 56);
        ABCE abce9 = new ABCE("Toto", null, 46);

        listPersons.add(abce1);
        listPersons.add(abce2);
        listPersons.add(abce3);
        listPersons.add(abce4);
        listPersons.add(abce5);
        listPersons.add(abce6);
        listPersons.add(abce7);
        listPersons.add(abce8);
        listPersons.add(abce9);

        // Sort by lastName
        System.out.println("----------------------Sort by lastName----------------------");
        listPersons = listPersons.stream().sorted(Comparator.comparing(ABCE::getLastName, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
        listPersons.stream().forEach(s -> System.out.println(s));

        // Sort by lastName descending
        System.out.println("----------------------Sort by lastName descending----------------------");
        listPersons = listPersons.stream().sorted(Comparator.comparing(ABCE::getLastName,Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList());
        listPersons.stream().forEach(s -> System.out.println(s));

        // Sort by lastName descending, firstName ascending
        System.out.println("----------------------Sort by lastName descending, firstName ascending----------------------");
        listPersons = listPersons.stream().sorted(Comparator.comparing(ABCE::getLastName,Comparator.nullsLast(Comparator.reverseOrder())).thenComparing(ABCE::getFirstName)).collect(Collectors.toList());
        listPersons.stream().forEach(s -> System.out.println(s));

        // Sort by lastName descending, firstName descending
        System.out.println("----------------------Sort by lastName descending, firstName descending----------------------");
        listPersons = listPersons.stream().sorted(Comparator.comparing(ABCE::getLastName,Comparator.nullsLast(Comparator.reverseOrder())).thenComparing(ABCE::getFirstName, Comparator.reverseOrder())).collect(Collectors.toList());
        listPersons.stream().forEach(s -> System.out.println(s));

        // Sort by lastName descending, firstName descending, age descending
        System.out.println("----------------------Sort by lastName descending, firstName descending, age descending--------------");
        listPersons = listPersons.stream().sorted(Comparator.comparing(ABCE::getLastName, Comparator.nullsLast(Comparator.reverseOrder())).thenComparing(ABCE::getFirstName, Comparator.reverseOrder()).thenComparing(ABCE::getAge, Comparator.reverseOrder())).collect(Collectors.toList());
        listPersons.stream().forEach(s -> System.out.println(s));

        // Sort by age
        System.out.println("----------------------Sort by age----------------------");
        listPersons = nullSafeCollection(listPersons).stream().sorted(Comparator.comparingInt(ABCE::getAge)).toList();
        System.out.println("Sort persons by age-----------------");
        listPersons.stream().forEach(s -> System.out.println(s));

        // Sort by age descending
        System.out.println("----------------------Sort by age descending----------------------");
        listPersons = listPersons.stream().sorted(Comparator.comparing(ABCE::getAge,Comparator.reverseOrder())).collect(Collectors.toList());
        listPersons.stream().forEach(System.out::println);

        //get max aged employees
        System.out.println("----------------------get max aged employees----------------------");
        //use this if return list
        int maxAge = listPersons.stream().map(ABCE::getAge).max(Integer::compareTo).orElse(0);
        listPersons = listPersons.stream().filter(abce -> abce.getAge()==maxAge).toList();
        System.out.println("max aged employee");
        listPersons.stream().forEach(System.out::println);

        //use this if return Object
        ABCE maxAgedPerson = listPersons.stream().reduce((person1, person2) -> person1.getAge()> person2.getAge() ? person1 : person2).orElse(null);
        System.out.println("max aged employee 2:: " + maxAgedPerson);

        // Sort by firstName and then by age
        System.out.println("----------------------Sort by firstName and then by age---------------------");
        listPersons = listPersons.stream().sorted(Comparator.comparing(ABCE::getFirstName).thenComparing(ABCE::getAge)).toList();
        listPersons.stream().forEach(System.out::println);

        // Sort by firstName and then by age descending
        System.out.println("----------------------Sort by firstName and then by age descending---------------------");
        listPersons = listPersons.stream().sorted(Comparator.comparing(ABCE::getLastName).thenComparing(ABCE::getAge, Comparator.reverseOrder())).toList();
        listPersons.stream().forEach(System.out::println);

        List<String> listStr = Arrays.asList("kajd", "jdjsjsj", "ewriew", "sldss", "sjfdksdf", "fisifi");
        listStr = listStr.stream().sorted().collect(Collectors.toList());
        listStr.stream().forEach(s -> System.out.println(s));
        // ewriew fisifi jdjsjsj kajd sjfdksdf sldss
        System.out.println("**********************************");
        listStr = listStr.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        listStr.stream().forEach(s -> System.out.println(s));

        //------------------------Custom sort ----------------------------------------------------//

        List<String> definedOrder = Arrays.asList("Mango", "Apple", "Grapes");
        List<String> inputList = Arrays.asList("Banana", "Apple", "Lichi", "Grapes", "Peach", "Mango", "Guava");

        List<String> orderedList = new ArrayList<>();
        List<String> remainingList = new ArrayList<>();

        inputList.forEach(input -> {
            if(definedOrder.contains(input)){
                orderedList.add(input);
            }else{
                remainingList.add(input);
            }
        });

        //sort the orderedList as per defined order
        List<String> finalList =  orderedList.stream().sorted(Comparator.comparing(definedOrder::indexOf)).collect(Collectors.toList());
        finalList.addAll(remainingList.stream().sorted().toList());

        System.out.println("finalList :: " + finalList);//[Mango, Apple, Grapes, Banana, Guava, Lichi, Peach]

        List<String> definedOrder2 = Arrays.asList("Banana", "Mango", "Grapes");
        List<String> inputList2 = Arrays.asList("Grapes", "Mango", "Banana");
        finalList = inputList2.stream().sorted(Comparator.comparingInt(definedOrder2::indexOf)).collect(Collectors.toList());
        System.out.println("finalList 2:: " + finalList);

        //sort a list of objects based on another list
        //List<String> booksToSort = getAllBooks().stream().map(Books::getName).toList();
        //List<Books> booksListFinal = getAllBooksUnSorted().stream().sorted(Comparator.comparing(Books::getName,booksToSort::indexOf)).toList();
        //System.out.println("booksListFinal :: " +booksListFinal);

        //list sort by length
        List<String> listSortByLength = new ArrayList<String>();
        listSortByLength.add("ABCDDF"); listSortByLength.add("RBCDDFEFFLFLFJFJFJ");
        listSortByLength.add("MBCDDFKDKDKSK"); listSortByLength.add("EBCDDFSFDSFDSF");
        listSortByLength.add("LBCDDHKHHKHKH"); listSortByLength.add("BBCD");

        List<String> listSortByLengthAsc = listSortByLength.stream().sorted(Comparator.comparing(String::length)).toList();
        System.out.println("listSortByLengthAsc :: " + listSortByLengthAsc);

        List<String> listSortByLengthDesc = listSortByLength.stream().sorted(Comparator.comparing(String::length, Comparator.reverseOrder())).toList();
        System.out.println("listSortByLengthDesc :: " + listSortByLengthDesc);

        //sort the list by natural order, with nulls last
        List<String> listTest = Arrays.asList("Mango", "Apple", "Grapes", null, "Papaya", "Banana");
        List<String>  orderedNullLastList = listTest.stream().sorted(Comparator.nullsLast(Comparator.naturalOrder())).toList();
        System.out.println("orderedNullLastList :: " + orderedNullLastList); //[Apple, Banana, Grapes, Mango, Papaya, null]

        //sort the list by reverse order, with nulls last
        List<String>  reversedNullLastList =listTest.stream().sorted(Comparator.nullsLast(Comparator.reverseOrder())).toList();
        System.out.println("reversedNullLastList :: " + reversedNullLastList);

        //sort the list by reverse order if no null
        List<String> listTest2 = Arrays.asList("Mango", "Apple", "Grapes", "Papaya", "Banana");
        List<String>  reversedListWithoutNull = listTest2.stream().sorted(Comparator.reverseOrder()).toList();
        System.out.println("reversedListWithoutNull :: " + reversedListWithoutNull); //[Papaya, Mango, Grapes, Banana, Apple]

        /*//reverse list
        List<Integer> integersList = List.of(23, 34, 56,231, 36);
        List<Integer> reverseList = integersList.reversed();
        System.out.println("reverseList : " +  reverseList);

        //reverse array
        Integer[] integersArr = new Integer[]{34,99,9393,8282,93};
        Integer[] reversedArr = Arrays.asList(integersArr).reversed().toArray(new Integer[integerList.size()]);
        for(int i =0;i<reversedArr.length; i++){
            System.out.println(reversedArr[i]);
        }

        //copy the array
        Integer[] reversedArrCopy = Arrays.copyOf(reversedArr, reversedArr.length);
        System.out.println("reversedArrCopy");
        for(int i =0;i<reversedArrCopy.length; i++){
            System.out.println(reversedArrCopy[i]);
        }*/

        //remove specific elements from a list
        List<String> list32 = new ArrayList<>();
        list32.add("djdjdjjd");list32.add("jsjsjsjjs");list32.add("jshdhdhhd");list32.add("ududddj");
        list32.removeIf(word -> word.startsWith("j"));
        System.out.println(list32); //[djdjdjjd, ududddj]

        List<String> names = new ArrayList<>();
        names.add("Robb");
        names.add("Bran");
        names.add("Rick");
        names.add("Bran");

        if(names.remove("Bran")) //Removes the first occurrence of the element from this list
            names.add("Jon");

        System.out.println("names :: " + names); //[Robb, Rick, Bran, Jon]


        Department admin = new Department(1001, "Admin", null);
        Department hr = new Department(1002, "HR", null);
        Department it = new Department(1003, "IT", null);

        Employee employee1 = new Employee (2001, "Asks", hr, new BigDecimal(12));
        Employee employee2 = new Employee (2002, "Tres", it, new BigDecimal(33));
        Employee employee3 = new Employee (2003, "Krrs", admin, new BigDecimal(67));
        Employee employee4 = new Employee (2004, "Lfkf", it, new BigDecimal(343));
        Employee employee5 = new Employee (2005, "Asks", it, new BigDecimal(443));
        Employee employee6 = new Employee (2006, "Dlde", admin, new BigDecimal(3322));
        Employee employee7 = new Employee (2007, "Orod", it,new BigDecimal(432));
        Employee employee8 = new Employee (2008, "Bunr", hr, new BigDecimal(443));

        List<Employee > employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);
        employeeList.add(employee4);
        employeeList.add(employee5);
        employeeList.add(employee6);
        employeeList.add(employee7);
        employeeList.add(employee8);

        //Sort by employee name and then by department
        System.out.println("----------------------Sort by employee name and then by department-----------------------");
        employeeList = employeeList.stream().sorted(Comparator.comparing(Employee ::getEmployeeName).thenComparing(e -> e.getDepartment().getDepartmentName())).collect(Collectors.toList());
        employeeList.forEach(emp -> System.out.println(emp.getEmployeeName() + "-" + emp.getDepartment().getDepartmentName()));

        //Sort by employee name and then by department descending
        System.out.println("----------------------Sort by employee name and then by department descending-----------------------");
        employeeList = employeeList.stream().sorted(Comparator.comparing(Employee ::getEmployeeName).thenComparing(e -> e.getDepartment().getDepartmentName(),Comparator.reverseOrder())).collect(Collectors.toList());
        employeeList.forEach(emp -> System.out.println(emp.getEmployeeName() + "-" + emp.getDepartment().getDepartmentName()));

        //Print each employee's department
        employeeList.forEach(emp -> System.out.println(Optional.ofNullable(emp).map(Employee::getDepartment).map(Department::getDepartmentId).orElse(0)));

        //distinct service ids
        List<Service> serviceList = getAllServices();
        Set<String> set = new HashSet<>();
        List<String> distinctServiceIds = serviceList.stream().map(Service::getServiceId).distinct().toList();
        System.out.println("distinctServiceIds :: " + distinctServiceIds); //[123, 234, 900, 1234]

        //Get all words
        List<String> listWords = new ArrayList<>();
        listWords.add("corejava advancedjava oracle ");
        listWords.add("nit kit nacre ");
        listWords.add("ram sg hk");
        List<String> allWords = listWords.stream().flatMap(listWords2 -> Arrays.stream(listWords2.split(" "))).collect(Collectors.toList());
        System.out.println("allWords :: " + allWords); // [corejava, advancedjava, oracle, nit, kit, nacre, ram, sg, hk]

        //Combine lists
        List<Integer> combList1 = Arrays.asList(1,2,3);
        List<Integer> combList2 = Arrays.asList(4,5,6);
        List<Integer> combList3 = Arrays.asList(7,8,9);
        List<Integer> combList = List.of(combList1, combList2, combList3).stream().flatMap(list1 -> list1.stream()).toList();
        System.out.println("combList :: " + combList); //[1, 2, 3, 4, 5, 6, 7, 8, 9]

        //Combine arrays
        String[] arr1 = new String[] {"red","blue","green","yellow"};
        String[] arr2 = new String[] {"Kite","Marble","Ball","Balloon"};
        List<String> strArr = List.of(arr1, arr2).stream().flatMap(Arrays::stream).toList();
        System.out.println("strArr ::" + strArr); // [red, blue, green, yellow, Kite, Marble, Ball, Balloon]
        
        List<String> fruitList = Arrays.asList("mango","apple","guava","litchi","orange","yellow");
        List<String> colourList = Arrays.asList("red","blue","green","yellow");
        List<String> toyList = Arrays.asList("chess","carrom","puzzle","checker", "yellow");

        //------------------------anymatch ,allmatch, nonematch starts ----------------------------------------------------//

        //check if all elements in list1 not in list2, list3
        boolean matched = fruitList.stream().allMatch(fruit -> !colourList.contains(fruit) && !toyList.contains(fruit));
        System.out.println("allElementsNotInEitherList :: " + matched); //false

        //check if any element in list1 is in list2/list3
        boolean matched2 = fruitList.stream().anyMatch(fruit -> colourList.contains(fruit) || toyList.contains(fruit));
        System.out.println("anyElementNotInEitherList :: " + matched2); //true

        //check if is there any element in list1 which is NOT in list2 but IS in list3
        boolean matched3 = fruitList.stream().anyMatch(fruit -> !colourList.contains(fruit) && toyList.contains(fruit));
        System.out.println("anyElementNotInListButIsInList3 :: " + matched3); //false

        //check if list contains any fruit with name guava
        boolean matched4 = fruitList.stream().anyMatch("guava"::equalsIgnoreCase);
        System.out.println("matched4 :: " + matched4); //true

        //check if list contains any fruit with name containing app (ignore case)
        boolean matched5 = fruitList.stream().anyMatch(fruit -> fruit.toUpperCase().contains("APP"));
        System.out.println("matched5 :: " + matched5); //true

        //check if list does not contain any fruit with name berries
        boolean matched6 = fruitList.stream().noneMatch("berries"::equalsIgnoreCase);
        System.out.println("matched6 :: " + matched6); //true

        //------------------------anymatch ,allmatch, nonematch ends ----------------------------------------------------//


        List<String> listIt = new ArrayList<>();
        listIt.add("A");listIt.add("B");listIt.add("C");listIt.add("D");
        //Add to end of list
        listIt.add("E");
        System.out.println("listIt :: " + listIt);  //[A, B, C, D, E]

        //List equals
        List<String> list11 = Arrays.asList("a","c","b").stream().sorted().toList();
        List<String> list12 = Arrays.asList("c","a","b").stream().sorted().toList();
        boolean listEquals = list11.equals(list12);
        System.out.println("List equals :: " + listEquals);

        //List                                              ListIterator
        //cannot modify list while traversing               can modify list while traversing
        //List adds to the end of list                      ListIterator adds to the start of list / add after specific element
        //Can traverse only forward                         Can traverse both forward and back

        //Add to start of list
        List<String> listIt2 = new ArrayList<>();
        listIt2.add("A");listIt2.add("B");listIt2.add("C");listIt2.add("D");
        ListIterator<String> listIterator2 = listIt2.listIterator();
        listIterator2.add("-1");
        System.out.println("listIt2 :: " + listIt2);  //[-1, A, B, C, D]

        //Add after specific element (B)
        List<String> listIt3 = new ArrayList<>();
        listIt3.add("A");listIt3.add("B");listIt3.add("C");listIt3.add("D");
        ListIterator<String> listIterator3 = listIt3.listIterator();
        while(listIterator3.hasNext()) {
            String s = listIterator3.next();
            if(s.equals("B"))
                listIterator3.add("R");
        }
        System.out.println("listIt3 :: " + listIt3); //[A, B, R, C, D]

        //Add before specific element (B)
        List<String> listIt4 = new ArrayList<>();
        listIt4.add("A");listIt4.add("B");listIt4.add("C");listIt4.add("D");
        ListIterator<String> listIterator4 = listIt4.listIterator();
        while(listIterator4.hasNext()) {
            String s = listIterator4.next();
            if(s.equals("B")) {
                listIterator4.previous();
                listIterator4.add("M");
                listIterator4.next();
            }
        }
        System.out.println("listIt4 :: " + listIt4); //[A, M, B, C, D]

        //remove an element from the list while traversing the list using listIterator
        List<String> listIt5 = new ArrayList<>();
        listIt5.add("A");listIt5.add("B");listIt5.add("C");listIt5.add("D");listIt5.add("E");listIt5.add("F");
        //remove C
        ListIterator<String> listIterator5 = listIt5.listIterator();
        while(listIterator5.hasNext()){
            String element = listIterator5.next();
            if(element.equalsIgnoreCase("C"))
                listIterator5.remove();
        }
        System.out.println("listIt5");
        System.out.println(listIt5); //[A, B, D, E, F]

        List<Integer> listNumbers3 = Arrays.asList(25, 190, 68, 20, 135, 335, 140, 18, 19, 102, 201, 156);
        //Get the largest number in a list of numbers
        int largestNo = listNumbers3.stream().sorted(Comparator.reverseOrder()).toList().get(0);
        System.out.println("largestNo :: " + largestNo); //335

        largestNo = Collections.max(listNumbers3, Comparator.comparing(v ->v));
        //System.out.println("largestNo :: " + largestNo);

        //Get the second-largest number in a list of numbers
        int secondLargestNo = listNumbers3.stream().sorted(Comparator.reverseOrder()).toList().get(1);
        // System.out.println("secondLargestNo :: " + secondLargestNo); //201

        secondLargestNo = listNumbers3.stream().sorted().skip(listNumbers3.size()-2).findFirst().orElse(0);
        // System.out.println("secondLargestNo :: " + secondLargestNo); //201

        //From 1 to 10, if even then print "even", if odd then print "odd"
        IntStream.rangeClosed(1, 10).forEach(i -> {
            String res = "";
            if(i%2==0){
                res = "even";
            }else{
                res = "odd";
            }
            System.out.print(i + ":" + res);
        });
        System.out.println("");

        //Immutable list (does not take null)
        //List.of("","")
        //List.copyOf(list)
        //Collections.unmodifiableList(list)

        //No add, Only change
        //Arrays.asList("","")
        //Collections.synchronizedList(list)

        List<String> mutableList = Arrays.asList("aaaa","bbbb","cccc");
        //mutableList.add(0,"00000"); //not allowed
        mutableList.set(0,"aaaaaaaaaa");

        System.out.println("immutableList");
        List<String> immutableList = List.of("aaaa","bbbb","cccc");
        //immutableList.add(0,"00000"); //not allowed
        //immutableList.set(0,"aaaaaaaaaa"); //not allowed
        immutableList = immutableList.stream().sorted(Comparator.nullsLast(Comparator.naturalOrder())).collect(Collectors.toList());
        immutableList.forEach(System.out::println);

        System.out.println("synchronizedList");
        List<String> synchronizedList = Collections.synchronizedList(mutableList);
        //synchronizedList.add("ddkdkd");//not allowed
        synchronizedList.set(0,"ddkdkd");
        synchronized (synchronizedList){
            synchronizedList.forEach(System.out::println);
        }

        System.out.println("unmodifiableList");
        List<String> unmodifiableList = Collections.unmodifiableList(mutableList);
        //unmodifiableList.add("ddkdkd");//not allowed
        //unmodifiableList.set(0,"ddkdkd");
        unmodifiableList = unmodifiableList.stream().sorted(Comparator.nullsLast(Comparator.naturalOrder())).collect(Collectors.toList());
        unmodifiableList.forEach(System.out::println);

        System.out.println("copyOfList");
        List<String> copyOfList = List.copyOf(mutableList);
        //copyOfList.add("ddkdkd");//not allowed
        //copyOfList.set(0,"ddkdkd");
        copyOfList = copyOfList.stream().sorted(Comparator.nullsLast(Comparator.naturalOrder())).collect(Collectors.toList());
        copyOfList.forEach(System.out::println);

        //CopyOnWriteArrayList
        List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>(mutableList);
        for(String s : copyOnWriteArrayList){
            if(s.equalsIgnoreCase("ccc")){
                copyOnWriteArrayList.add(copyOnWriteArrayList.indexOf("ccc")+1,"111"); //[aaa, bbb, ccc, 111, ddd]
            }
        }
        System.out.println("copyOnWriteArrayList :: " + copyOnWriteArrayList);


        /*
        ArrayList             vs                      CopyOnWriteArrayList
        ------------------------------------------------------------------------
                                                          thread-safe
                                                          fail-safe (no ConcurrentModificationException, ie change while reading is possible)
           faster                                         slower (makes a fresh copy of the array with every mutative operation)


         ArrayList             vs                        LinkedList
        ------------------------------------------------------------------------
           stores in array                                stores in Nodes (doubly-linked)
           Search is fast -  o(1) (RandomAccess)		  Search is slow - o(n) (Sequential)
           Insertion is slow - o(n)					      Insertion is fast - o(1)
           takes lesser memory                            takes more memory


           ArrayList             vs                        Vector
        ------------------------------------------------------------------------
                                                           Synchronized
                                                           slower
           Increases by 50%		                           Increases by 100%
                                    		               legacy




        * ArrayList

            Interfaces :
            ----------------
                1. Iterable
                2. Collection
                3. List
                4. Serializable
                5. Cloneable
                6. RandomAccess

            ArrayList size
            ----------------------
                For a newly created arrayList, an array is created with initial capacity =10 , size = 0
                Each time an element is added, the size increases by 1
                When size reaches capacity and capacity needs to increase, a new array is created which is 1.5 times the initial capacity and the values of the old array are copied to the new array.


         * LinkedList

            Interfaces :
            ----------------
                1. Iterable
                2. Collection
                3. List
                4. Serializable
                5. Cloneable
                6. Queue
                7. Dequeue



        * CopyOnWriteArrayList

            Interfaces :
            ----------------
                1. Iterable
                2. Collection
                3. List
                4. Serializable
                5. Cloneable
                6. RandomAccess

        */

        //List.remove()
        List<Integer> integerList2 = new ArrayList<Integer>();
        integerList2.add(1);integerList2.add(2);integerList2.add(3);integerList2.add(4);
        integerList2.remove(1); // index :: [1, 3, 4]
        System.out.println("Removed list :: " + integerList2);
        //remove specific element
        integerList2.remove(integerList2.indexOf(4)); //[1, 3]
        System.out.println("Removed list 2 :: " + integerList2);
        //remove all occurrences of an element
        integerList2.add(1);integerList2.add(2);integerList2.add(3);integerList2.add(4);
        integerList2.removeAll(Collections.singletonList(1));
        System.out.println("Removed list 3 :: " + integerList2); //[3, 2, 3, 4]


        //check number of lists are not empty
        System.out.println("allEmpty-------------");
        List<String> listCheck1 = new ArrayList<>();
        List<String> listCheck2 = null;
        List<String> listCheck3 = List.of("jdjjd", "djdjd");
        
        //any null / empty -> SDM, else SPE
        String sourceSystem = Stream.of(listCheck1, listCheck2, listCheck3).anyMatch(CollectionUtils::isEmpty)?"SDM":"SPE";
        System.out.println("sourceSystem :: " + sourceSystem);

        //get anagrams
        //check each element if it is anagram is present in the map as key - add it to the value - else add the element as a new key in the map
        String[] arrString = {"abcd", "java", "dcba", "ajav", "xyz", "epam", "pame", "aepm"};
        Map<String, Set<String>> map = new HashMap<>();
        for(int i=0;i<arrString.length;i++){
            String newElement = arrString[i];
            AtomicInteger flag = new AtomicInteger();
            if(i==0){
                HashSet<String> set0 = new HashSet<>();
                set0.add(newElement);
                map.put(newElement, set0);
            }else{
                //check if the new element is a anagram of any of the map keys
                //if yes , add it to the value set, else add it as key
                map.keySet().forEach(key -> {
                    if(isAnagram(key,newElement)){
                        map.get(key).add(newElement);
                        flag.set(1);
                    }
                });
                if(flag.get() ==0){
                    HashSet<String> set0 = new HashSet<>();
                    set0.add(newElement);
                    map.put(newElement, set0);
                }
            }
        }

        System.out.println("anagram list");
        System.out.println(map);
        map.values().forEach(setS -> {
                if(setS.size() > 1){
                    System.out.println(setS);
                }
        });
        /*
        [java, ajav]
        [epam, aepm, pame]
        [dcba, abcd]*/


        List<Emp> empList = new ArrayList<>();
        Emp emp1 = new Emp("Ruru","Lipu",23, new BigDecimal(67),List.of("programming"));
        Emp emp2 = new Emp("Gogo","Biku",23, new BigDecimal(23),List.of("programming"));
        Emp emp3 = new Emp("Simu","Cool",23, new BigDecimal(13),List.of("programming"));
        Emp emp4 = new Emp("Lote","Bool",23, new BigDecimal(45),List.of("programming"));
        Emp emp5 = new Emp("Toto","Hoda",23, new BigDecimal(30),List.of("programming"));
        Emp emp6 = new Emp("Senu","Ray",23, new BigDecimal(82),List.of("programming"));
        Emp emp7 = new Emp("Aru","Rere",23, new BigDecimal(32),List.of("programming"));
        empList.addAll(List.of(emp1, emp2, emp3, emp4, emp5, emp6, emp7));
       
        int n = 3;
        //get the sublist of n most salaried employees
        List<Emp> nMostSalariedEmployees = empList.stream().sorted(Comparator.comparing(Emp::getSalary, Comparator.reverseOrder())).toList().subList(0, Math.min(n, empList.size()));
        System.out.println("nMostSalariedEmployees :: " + nMostSalariedEmployees);

        //get all the nth employee in the list of highest to lowest salary
        empList.sort(Comparator.comparing(Emp::getSalary, Comparator.reverseOrder()));
        List<Emp> allNthEmployee = IntStream.rangeClosed(0, empList.size() - 1)
                   .filter(i ->(i + 1) % n == 0)
                   .mapToObj(empList::get)
                   .toList();
        System.out.println("allNthEmployee :: " + allNthEmployee);

        //get nth highest salary
        BigDecimal nthHighestSalary =
        employeeList.stream().map(Employee::getSalary).distinct().sorted(Comparator.reverseOrder()).toList().get(n-1);
        System.out.println("nthHighestSalary :: " + nthHighestSalary);

        //select a random employee
        Employee employeeRandom = employeeList.get( (int) (Math.random() * employeeList.size()));
        System.out.println("employeeRandom :: " + employeeRandom) ;

        //select a random number between 10(incl) and 100(excl)
        int upper = 100;
        int lower = 10;
        int rand = (int) (Math.random() * (upper - lower)) + lower;
        System.out.println("rand :: " + rand);

    }

    private static Fruit getNewFruit(Fruit fruit) {
        fruit.setSeason("all");
        return fruit;
    }


    private static boolean isAnagram(String string1, String string2){
        return string1.toUpperCase().chars().mapToObj(i -> (char)i).sorted().toList().equals(string2.toUpperCase().chars().mapToObj(i -> (char)i).sorted().toList());
    }

    private static List<Fruit> getAllFruits(){

        Fruit apple = new Fruit("apple","red","all");
        Fruit banana = new Fruit("banana","yellow","all");
        Fruit mango = new Fruit("mango","yellow","summer");
        Fruit litchi = new Fruit("litchi","red","summer");
        Fruit guava = new Fruit("guava","green","all");
        Fruit litchi2 = new Fruit("LITCHI","red","summer");
        return Arrays.asList(apple,banana,mango,litchi,guava,litchi2);

    }


    private static List<TaxationDetail> getTaxationDetailsList() {
        TaxationDetail taxationDetail1 = new TaxationDetail("VAT", new TaxAmounts(new TaxationAmounts(BigDecimal.valueOf(100.23), BigDecimal.valueOf(133.44)), "13"));
        TaxationDetail taxationDetail2 = new TaxationDetail("VAT", new TaxAmounts(new TaxationAmounts(BigDecimal.valueOf(200.23), BigDecimal.valueOf(233.44)), "23"));
        TaxationDetail taxationDetail3 = new TaxationDetail("GST", new TaxAmounts(new TaxationAmounts(BigDecimal.valueOf(300.23), BigDecimal.valueOf(333.44)), "33"));
        TaxationDetail taxationDetail4 = new TaxationDetail("GST", new TaxAmounts(new TaxationAmounts(BigDecimal.valueOf(400.23), BigDecimal.valueOf(433.44)), "43"));
        TaxationDetail taxationDetail5 = new TaxationDetail("TDS", new TaxAmounts(new TaxationAmounts(null, BigDecimal.valueOf(533.44)), "53"));
        TaxationDetail taxationDetail6 = new TaxationDetail("TDS", null);

        return List.of(taxationDetail1, taxationDetail2, taxationDetail3, taxationDetail4, taxationDetail5, taxationDetail6);

    }

    private static List<Service> getAllServices() {
        Service service1 = new Service("123", "Provided Service", "Provided", 100);
        Service service2 = new Service("234", "Internal Service", "Internal", 300);
        Service service3 = new Service("123", "Delivery Service", "Delivery", 400);
        Service service4 = new Service("900", "Retail Service", "Provided", 200);
        Service service5 = new Service("1234", "Retail Service", "Provided", 200);
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(service1);serviceList.add(service2);serviceList.add(service3);
        serviceList.add(service4);serviceList.add(service5);
        return  serviceList;
    }

    private static <T> Collection<T> nullSafeCollection(Collection<T> list){
        return list.isEmpty()?Collections.emptyList():list;
    }

    private static List<Books> getAllBooks() {
        return List.of(new Books("The FellowShip of the Ring", 1954, "234332", 3883),
                new Books("The Two Towers", 1955, "234332", 73737),
                new Books("The Return of the King", 1954, "234332", 7773),
                new Books("Harry Potter and the Philosopher's Stone", 1954, "234332",73773),
                new Books("And Then There Were None", 1954, "234332",636636));
    }

    private static List<Books> getAllBooksUnSorted() {
        return List.of(
                new Books("Harry Potter and the Philosopher's Stone", 1954, "234332",173773),
                new Books("The Two Towers", 1955, "234332", 173737),
                new Books("The FellowShip of the Ring", 1954, "234332", 13883),
                new Books("And Then There Were None", 1954, "234332",1636636),
                new Books("The Return of the King", 1954, "234332", 17773));
    }
}
