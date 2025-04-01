package com.src.stream;


import com.src.model.User;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class ItemX {
    private String itemType;
    private String itemCC;
    private String itemNumber;
    private String itemClassification;
    private boolean isReferenceItem;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class ServicePrice {
    private List<Saving> savingList;
    private String servicePriceId;
    private String servicePriceName;
}

@Data
@AllArgsConstructor
@ToString
class Saving {
    private BigDecimal amount;
    private String id;
    private Boolean isApplied;
    private String code;
    private Integer sequence;
    private String discountType;
    private Discount discount;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Discount {
    private Integer count;
}

@Getter
@Setter
@AllArgsConstructor
@ToString
class Book {
    private String name;
    private String author;
    private int releaseYear;
    private String isbn;
    private int price;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {

	private String name;
	private Integer age;
	private long salary;
	private String department;

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Item {
    private String itemNo;
    private String priceType;
    private BigDecimal price;
}

/*
         Collectors.groupingBy
         ---------------------------
        .collect(Collectors.groupingBy(Student::getStandard, Collectors.counting()));


            Collectors.mapping
            -----------------------------
             If map Value has >1 values which has to be transformed
              - Collectors.groupingBy , Collectors.mapping
                    Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper,
                               Collector<? super U, A, R> downstream) {

        .collect(Collectors.groupingBy(Student::getStandard, Collectors.mapping(Student::getFullName, Collectors.toList())));
	    .collect(Collectors.groupingBy(Student::getStandard, Collectors.mapping(Student::getHouse, Collectors.joining(","))));
        .collect(Collectors.groupingBy(Student::getStandard, Collectors.mapping(student -> student.getHobbies().stream().collect(Collectors.joining(",")),Collectors.joining(","))));
        .collect(Collectors.groupingBy(ServicePrice::getServicePriceId,
                Collectors.mapping(servicePrice -> servicePrice.getSavingList().stream().filter(Saving::getIsApplied).map(Saving::getCode).collect(Collectors.joining(",")), Collectors.joining(","))));


            Collectors.reducing
            --------------------------
             If map Value has >1 values which has to be reduced to a single value - use Collectors.reducing
                    java.util.stream.Collectors:reducing((U least,
                                Function<? super T, ? extends U> mapper,
                                BinaryOperator<U> binaryOp) {

                                }
                    If you want to just summarize the elements of an Integer stream into an integer, you could use
                          Collectors.reducing(0, x -> x, (x, y) -> x + y).
                    If you want to summarize the lengths of Strings in a String stream, you could use
                          Collectors.reducing(0, String::length, (x, y) -> x + y).
                    If you want to get the maximum Double from a stream of Doubles, but no less than Math.PI, you could use
                          Collectors.reducing(Math.PI, x -> x, Math::max).

            .collect(Collectors.groupingBy(Saving::getIsApplied, Collectors.reducing(BigDecimal.ZERO, Saving::getAmount, BigDecimal::add)));
            .collect(Collectors.groupingBy(x-> x.substring(0, 1),Collectors.reducing(0, x-> x.length(), (x, y)-> x + y)));
            .collect(Collectors.groupingBy(User::getUser, Collectors.reducing(
                        "", User::getLanguage, (l1,l2)-> String.join(",", l1, l2)
                )));
            .collect(Collectors.groupingBy(User::getUser, Collectors.reducing(
                    "", User::getLanguage, (l1,l2)-> String.join(",", l1, l2)
            ))).entrySet().forEach(entry -> {
                User user = new User();
                user.setUser(entry.getKey());
                user.setLanguage(entry.getValue());
                userList2.add(user);
            });
            .collect(Collectors.groupingBy(Saving::getDiscountType, Collectors.reducing(BigDecimal.ZERO, Saving::getAmount, BigDecimal::add)));


            Collectors.toMap
            --------------------
                If map Value has 1 value which has to be transformed - use Collectors.toMap`
                To remove Optional from maxBy - use Collectors.toMap


              Arrays.stream(arr2).collect(Collectors.collectingAndThen(Collectors.groupingBy(Function.identity(), Collectors.counting()),
                            map -> map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue, Collections.reverseOrder()))
                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n) -> n, LinkedHashMap::new))
                                    .entrySet().iterator().next().getKey()));
              Arrays.stream(price.split(",")).map(s -> s.split(":")).collect(Collectors.toMap(array2 -> Integer.parseInt(array2[0]), array2 -> array2[1]));

             Collectors.collectingAndThen
           ----------------------------------

               .collect(Collectors.collectingAndThen(Collectors.groupingBy(Book::getReleaseYear, Collectors.counting()),
                    map -> map.get(1984)));

               .collect(Collectors.collectingAndThen(Collectors.groupingBy(Book::getReleaseYear, Collectors.summingInt(Book::getPrice)),
                    map -> map.get(1984)));

               .collect(Collectors.collectingAndThen(Collectors.groupingBy(Book::getAuthor, Collectors.counting()),
                map -> map.entrySet().stream().filter(entry -> entry.getValue()>1).map(Map.Entry::getKey).toList()));

                ----group-by multiple fields----

                Function<Item, String> compositeKeyItem = item -> item.getItemNo().concat("-").concat(item.getPriceType());

                List<String> itemsWithMultiplePricesOfSameType =
        itemList.stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(compositeKeyItem, Collectors.counting()),
                map -> map.entrySet().stream().filter(entry -> entry.getValue()> 1).map(Map.Entry::getKey).toList()));

                .collect(Collectors.collectingAndThen(Collectors.groupingBy(compositeKeyItem, Collectors.counting()),
                    map -> map.entrySet().stream().map(entry->entry.getValue()==1).reduce(Boolean::logicalAnd).orElse(false)
                ));




 */

public class CollectorEx {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(10, 20, 30, 40, 50);

        //Sum
        int sum = list.stream().mapToInt(v -> v).sum();
        System.out.println(sum); //150

        //Sum of squares of each number
        int sumOfSquares = list.stream().collect(Collectors.summingInt(v -> v * v));
        System.out.println(sumOfSquares); //5500

        //Average
        double average = list.stream().collect(Collectors.averagingInt(v -> v));
        System.out.println(average); //30.0

        //Count
        Long count = list.stream().collect(Collectors.counting());
        System.out.println(count); //5

        //Half of sum
        int halfOfSum = list.stream().collect(Collectors.collectingAndThen(Collectors.summingInt(v-> v), summation -> summation/2));
        System.out.println("halfOfSum :: " + halfOfSum); //75

        //Square of sum
        int squareOfSum =
        list.stream().collect(Collectors.collectingAndThen(Collectors.summingInt(v -> v), result -> result * result));
        System.out.println("squareOfSum :: " + squareOfSum); //22500

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "    ", "jkl");

        int[] arr = new int[]{12, 23, 494, 393, 838};
        sum = Arrays.stream(arr).sum();
        System.out.println(sum); // 1760
        int max = Arrays.stream(arr).max().getAsInt();
        System.out.println(max);

        //Join all non-empty , non-blank Strings with ,
        String commaSeparatedNonEmptyString = strings.stream().filter(StringUtils::isNotBlank).collect(Collectors.joining(","));
        System.out.println("commaSeparatedNonEmptyString :: " + commaSeparatedNonEmptyString); //abc,bc,efg,abcd,jkl

        //Join all non-empty Strings
        String nonEmptyStringsJoined = strings.stream().filter(StringUtils::isNotBlank).collect(Collectors.joining());
        System.out.println(nonEmptyStringsJoined); //abcbcefgabcdjkl

        //Join all non-empty Strings with , prefix+, suffix-
        String nonEmptyStringsJoinedPS = strings.stream().filter(StringUtils::isNotBlank).collect(Collectors.joining(",", "+", "-"));
        System.out.println(nonEmptyStringsJoinedPS); //+abc,bc,efg,abcd,jkl-

        Integer[] arr2 = new Integer[]{100, 100, 9, 8, 200};
        Double avg = Arrays.stream(arr2).map(i -> i * i).filter(i -> i <= 100).collect(Collectors.averagingInt(v -> v));
        System.out.println(avg);

        //---------------------------------------------------------------------------------------------------------------------//

        //Remove all null, empty and duplicates from the list
        //apache-commons-lang3 :: StringUtils.isNotEmpty - checks for blank and null string
        List<String> list2 = Arrays.asList("abc", "", "bc", "efg", "abcd", "  ", "jkl", null, "abc", "fgh");
        list2 = list2.stream().filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
        System.out.println("list2 :: " + list2);

        //Join all non-null,non-empty Strings with , prefix+, suffix-
        String joined = list2.stream().filter(StringUtils::isNotBlank).collect(Collectors.joining(",","+","-"));
        System.out.println("joined ::" +joined);

        //---------------------------------------------------------------------------------------------------------------------//

        List<ItemX> itemXList = getItems();
        Set<String> uniqueKeys = new HashSet<>();
        //remove duplicates(itemType+itemCC) from a list
        System.out.println("remove duplicates(itemType+itemCC) from a list--------------------");
        List<ItemX> uniqueItemXList = itemXList.stream().filter(itemX -> uniqueKeys.add(itemX.getItemType().concat(itemX.getItemCC()))).toList();
        System.out.println("uniqueItemXList :: " + uniqueItemXList);

	   //remove duplicates(itemType+itemCC) from a list
        Map<String, ItemX> nodupsMap = new HashMap<>();
        for(ItemX itemX: itemXList) {
            if(!nodupsMap.containsKey(itemX.getItemType().concat(itemX.getItemCC()))){
                nodupsMap.put(itemX.getItemType().concat(itemX.getItemCC()), itemX);
            }
        }
        List<ItemX> noDupsList = nodupsMap.values().stream().toList();
        System.out.println("noDupsList ::" + noDupsList);    

	//-------- Collectors.groupingBy-----------------------------------
	    // Map<Integer, List<Book>> bookMap = books.stream().collect(Collectors.groupingBy(Book::getReleaseYear));
	    //.collect(Collectors.groupingBy(x, Collectors.mapping(y, Collectors.joining("."));
	    //.collect(Collectors.groupingBy(x, Collectors.counting());
	    //.collect(Collectors.groupingBy(x, Collectors.reducing(BigDecimal.ZERO, Saving::getAmount, BigDecimal::add)));
	    //.collect(Collectors.groupingBy(compositeKey, Collectors.toList()));
	    
	    
        //for each item type get all itemNos in that type in a String
        System.out.println("get each type of item and all the items in that type ----------------------");
        Map<String, String> mapItemTypeAndItemsMap =
        itemXList.stream().collect(Collectors.groupingBy(ItemX::getItemType, Collectors.mapping(ItemX::getItemNumber, Collectors.joining(","))));
        System.out.println("mapItemTypeAndItemsMap");
        System.out.println(mapItemTypeAndItemsMap);
        //{bedside-table=DMDSV2,DMDSV3, bed=DGDSV2,DMDSV2, sofa=DMDSV2, chair=RESRD1,RESRD2, lamp=DRDBV2, table=DRDSV2,DMDSV2}

        //get count of each itemClassfication
        Map<String, Long> mapitemClassificationNum =
        itemXList.stream().collect(Collectors.groupingBy(ItemX::getItemClassification, Collectors.counting()));
        System.out.println("mapitemClassificationNum");
        mapitemClassificationNum.forEach((key, value) -> System.out.println(key + "-" + value));

        //for each item type get count of items which have itemClassification=Living
        Map<String, Long> mapItemTypeCountLiving =
                itemXList.stream().collect(Collectors.groupingBy(ItemX::getItemType, Collectors.mapping(itemX -> itemX.getItemClassification().equalsIgnoreCase("Living"), Collectors.counting())));
        System.out.println("mapItemTypeCountLiving");
        mapItemTypeCountLiving.forEach((key, value) -> System.out.println(key + "-" + value));

       /*
       bedside-table-0
        bed-0
        sofa-1
        chair-2
        lamp-1
        table-0*/

        List<ServicePrice> servicePriceList = getAllServicePrices();

        //groupBy servicePriceID for the isAppliedPrice = true on savings
        Map<String, String> mapOfSaving =
        servicePriceList.stream().collect(Collectors.groupingBy(ServicePrice::getServicePriceId,
                Collectors.mapping(servicePrice -> servicePrice.getSavingList().stream().filter(Saving::getIsApplied).map(Saving::getCode).collect(Collectors.joining(",")), Collectors.joining(","))));
        System.out.println("mapOfSaving");
        System.out.println(mapOfSaving); //{servicePrice2=UD3U9222,939SMSMSMS, servicePrice3=9292MM22,9939SMMS033, servicePrice1=DMDM2O2MS,383JSSMSM3,3I3SMSRNND}

        //total saving amount
        BigDecimal totalSaving =
                servicePriceList.stream().flatMap(servicePrice -> servicePrice.getSavingList().stream()).map(Saving::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("totalSaving :: " + totalSaving);

        //count of each type of saving (isApplied or not)
        Map<Boolean, Long> mapEachTypeOfSaving =
                servicePriceList.stream().flatMap(servicePrice -> servicePrice.getSavingList().stream()).collect(Collectors.groupingBy(Saving::getIsApplied, Collectors.counting()));
        System.out.println("mapEachTypeOfSaving :: " + mapEachTypeOfSaving); //{false=3, true=7}

        //highest saving amount
        AtomicReference<BigDecimal> highestSavingsAmount = new AtomicReference<>(BigDecimal.ZERO);
        servicePriceList.stream().flatMap(servicePrice -> servicePrice.getSavingList().stream()).max(Comparator.comparing(Saving::getAmount)).stream().findFirst().ifPresent(saving ->  highestSavingsAmount.set(saving.getAmount()));
        System.out.println("highestSavingsAmount :: " + highestSavingsAmount.get()); //2000

        //highest savings amount for savings isapplied=true
        BigDecimal highestSavingIsAppliedTrue =
        servicePriceList.stream().flatMap(servicePrice -> servicePrice.getSavingList().stream())
                .filter(Saving::getIsApplied).max(Comparator.comparing(Saving::getAmount)).get().getAmount();
        System.out.println("highestSavingIsAppliedTrue :: " + highestSavingIsAppliedTrue); //400

        //total savings amount for SP1
        BigDecimal totalSavingsAmountForSP1 = servicePriceList.stream().filter(servicePrice -> servicePrice.getServicePriceName().equalsIgnoreCase("SP1"))
                .flatMap(servicePrice -> servicePrice.getSavingList().stream())
                .map(Saving::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("totalSavingsAmountForSP1 :: " + totalSavingsAmountForSP1);

        //total savings amount for savings isapplied=true
        BigDecimal totalSavingIsAppliedTrue =
        servicePriceList.stream()
                .flatMap(servicePrice -> servicePrice.getSavingList().stream())
                .filter(Saving::getIsApplied)
                .map(Saving::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("totalSavingIsAppliedTrue :: " + totalSavingIsAppliedTrue); //1216

        //sum of each savings type (isApplied or not)
        Map<Boolean, BigDecimal> sumOfEachTypeOfSaving =
        servicePriceList.stream()
                .flatMap(servicePrice -> servicePrice.getSavingList().stream())
                .collect(Collectors.groupingBy(Saving::getIsApplied, Collectors.reducing(BigDecimal.ZERO, Saving::getAmount, BigDecimal::add)));
        System.out.println("sumOfEachTypeOfSaving :: " + sumOfEachTypeOfSaving); //{false=3300, true=1216}
/*
	@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemNo {

    private String lineId;
    private String itemNo;
    private BigDecimal unitPrice;
}

	List<ItemNo> itemNoList = new ArrayList<>();
        ItemNo itemNo1 = new ItemNo("1","100", BigDecimal.valueOf(10));
        ItemNo itemNo2 = new ItemNo("2","100", BigDecimal.valueOf(10));
        ItemNo itemNo3 = new ItemNo("3","200", BigDecimal.valueOf(20));
        ItemNo itemNo4 = new ItemNo("4","200", BigDecimal.valueOf(20));
        ItemNo itemNo5 = new ItemNo("5","200", BigDecimal.valueOf(20));
        ItemNo itemNo6 = new ItemNo("6","300", BigDecimal.valueOf(30));
        itemNoList.addAll(List.of(itemNo1, itemNo2, itemNo3, itemNo4, itemNo5, itemNo6));

        Map<String, BigDecimal> map6 =
        itemNoList.stream().collect(Collectors.groupingBy(ItemNo::getItemNo, Collectors.reducing(BigDecimal.ZERO, ItemNo::getUnitPrice, BigDecimal::add)));
        System.out.println("map6 :: " + map6);

	    */

        //each of the distinct discountTypes
        List<String> distinctDiscountTypes =
                servicePriceList.stream().flatMap(servicePrice -> servicePrice.getSavingList().stream())
                        .map(Saving::getDiscountType).distinct().toList();
        System.out.println("distinctDiscountTypes :: " + distinctDiscountTypes); //[Voucher, Promotion, Sale, Coupon]

        //count of each of discountTypes
        Map<String, Long> mapCountOfDiscountTypes =
                servicePriceList.stream().flatMap(servicePrice -> servicePrice.getSavingList().stream())
                        .collect(Collectors.groupingBy(Saving::getDiscountType, Collectors.counting()));
        System.out.println("mapCountOfDiscountTypes :: " + mapCountOfDiscountTypes); //{Sale=3, Coupon=2, Promotion=2, Voucher=3}

        //sum of each discountType
        Map<String, BigDecimal> mapSumOfDiscountTypes  =
        servicePriceList.stream()
                .flatMap(servicePrice -> servicePrice.getSavingList().stream())
                .collect(Collectors.groupingBy(Saving::getDiscountType, Collectors.reducing(BigDecimal.ZERO,Saving::getAmount,BigDecimal::add)));
        System.out.println("mapSumOfDiscountTypes :: " + mapSumOfDiscountTypes); //{Sale=1300, Coupon=366, Promotion=700, Voucher=2150}


        //Rule : .filter().findFirst().ifPresent() / .filter().findFirst().orElse()
        //Rule : .flatmap().map()

        List<Person> personList = List.of(
                new Person("Paul", 24, 20000 ,"Admin"),
                new Person("Mark", 30, 30000, "Finance"),
                new Person("Will", 28, 28000, "IT"),
                new Person("William", 28, 28000, "Admin"),
                new Person("Will", 28, 30000, "Finance"));

        //Group by name and age
        //group-by multiple fields -> group by compositeKey
        Function<Person, String> compositeKey = person -> person.getName().concat(person.getAge().toString());
        Map<String, List<Person>> mapGroupByNameAndAge = personList.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));
        System.out.println("mapGroupByNameAndAge :: " + mapGroupByNameAndAge);
        //{William28=[Person(name=William, age=28, salary=28000, department=Admin)], Will28=[Person(name=Will, age=28, salary=28000, department=IT), Person(name=Will, age=28, salary=30000, department=Finance)], Mark30=[Person(name=Mark, age=30, salary=30000, department=Finance)], Paul24=[Person(name=Paul, age=24, salary=20000, department=Admin)]}

        List<Item> itemList = getItemsWithPrice();

        //items which has >1 price of the same type
        //group-by multiple fields
        Function<Item, String> compositeKeyItem = item -> item.getItemNo().concat("-").concat(item.getPriceType());
        List<String> itemsWithMultiplePricesOfSameType =
        itemList.stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(compositeKeyItem, Collectors.counting()),
                map -> map.entrySet().stream().filter(entry -> entry.getValue()> 1).map(Map.Entry::getKey).toList()));
        System.out.println("itemsWithMultiplePricesOfSameType :: "  +itemsWithMultiplePricesOfSameType);

        //check if any item has >1 price of the same type
        //group-by multiple fields
        boolean noItemsHaveMultiplePriceOfSameType =
        itemList.stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(compositeKeyItem, Collectors.counting()),
                map -> map.entrySet().stream().map(entry->entry.getValue()==1).reduce(Boolean::logicalAnd).orElse(false)
        ));
        System.out.println("noItemsHaveMultiplePriceOfSameType :: "  +noItemsHaveMultiplePriceOfSameType);

        //itemNo with highest price
        //highest = Collectors.maxBy(Comparator.comparing())
        String itemNoWithHighestPrice =
                      itemList.stream().max(Comparator.comparing(Item::getPrice)).get().getItemNo();
        System.out.println("itemNoWithHighestPrice :: " + itemNoWithHighestPrice);

        //highest price
        BigDecimal highestPrice = itemList.stream().map(Item::getPrice).max(BigDecimal::compareTo).orElse(null);
        System.out.println("highestPrice :: " + highestPrice);

        List<Book> books = getBooks();

        //Get all books of year 1984
        books.stream().filter(book -> book.getReleaseYear() == 1984).toList().forEach(System.out::println);
        //Book(name=My First Book, author=First Author, releaseYear=1984, isbn=0395489318, price=230)
        //Book(name=My Second Book, author=Second Author, releaseYear=1984, isbn=0345339711, price=534)

        //Grouping By year
        Map<Integer, List<Book>> bookMap = books.stream().collect(Collectors.groupingBy(Book::getReleaseYear));
        System.out.println("bookMap :: " + bookMap);

        //Grouping By and Count
        Map<Integer, Long> mapByYear = books.stream().collect(Collectors.groupingBy(Book::getReleaseYear, Collectors.counting()));
        System.out.println("mapByYear" + mapByYear); //{1984=2, 1985=1, 1986=1}

        //Get total price of books per year
        Map<Integer, Integer> priceBookMap =
                books.stream().collect(Collectors.groupingBy(Book::getReleaseYear, Collectors.summingInt(Book::getPrice)));
        System.out.println("priceBookMap :: " + priceBookMap);
        //priceBookMap :: {1984=764, 1985=773, 1986=543}

        //Get all books of author Second Author
        books.stream().filter(book -> book.getAuthor().equalsIgnoreCase("Second Author")).collect(Collectors.toList()).forEach(System.out::println);

        //Get number of books of 1984
        Long countOfBooks1984 =
                books.stream().filter(book -> book.getReleaseYear()==1984).count();
       /* books.stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(Book::getReleaseYear, Collectors.counting()),
                    map -> map.get(1984)));*/
        System.out.println("countOfBooks1984 :: " + countOfBooks1984); //2

        //get total price of books of 1984

        int sumOfPrice1984 =
                /*books.stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(Book::getReleaseYear, Collectors.summingInt(Book::getPrice)),
                                        map -> map.get(1984)));*/
        books.stream().filter(book -> book.getReleaseYear() == 1984).map(Book::getPrice).collect(Collectors.summingInt(value -> value));
        System.out.println("sumOfPrice1984 :: " + sumOfPrice1984); // 764

        //authors who has >1 books
        List<String> authorWithMoreThan1Book =
        books.stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(Book::getAuthor, Collectors.counting()),
                map -> map.entrySet().stream().filter(entry -> entry.getValue()>1).map(Map.Entry::getKey).toList()));
        System.out.println("authorWithMoreThan1Book :: " + authorWithMoreThan1Book); //[Second Author]

        //----------------------------------------------------------------------

        //Remove all null and blanks and join the strings by ;
        String str = "abhik, rahul, joy, ,1,  ";
        String result = Arrays.asList(str.split(",")).stream().filter(StringUtils::isNotEmpty).map(String::trim).collect(Collectors.joining(";"));
        System.out.println(result); //abhik;rahul;joy;1

        //Select word with highest occurrence in a String
        String str2 = "This is the test string which contains lots of words.Please select the word with the highest occurrence.";
        String maxWord = Arrays.stream(str2.split(" ")).collect(Collectors.collectingAndThen(Collectors.groupingBy(Function.identity(), Collectors.counting()),
                    map -> map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n)->n, LinkedHashMap::new))
                                            .entrySet().iterator().next().getKey()));
        System.out.println("maxWord :: " + maxWord); //the


        Integer[] arr3 = new Integer[]{12, 33, 223, 33, 223, 77, 882, 929, 828, 49, 50, 843, 33};
        int maxOccurredInteger =
                Arrays.stream(arr3).collect(Collectors.collectingAndThen(Collectors.groupingBy(Function.identity(), Collectors.counting()),
                        map -> map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n) -> n, LinkedHashMap::new))
                                .entrySet().iterator().next().getKey()));
        System.out.println("maxOccurredInteger :: " + maxOccurredInteger); //33

        String price = "1:RegularBistroEatInSalesUnitPrice,2:RegularEatInSalesUnitPrice,3:RegularBistroSalesUnitPrice";
        Map<Integer, String> priceTypes =
                Arrays.stream(price.split(",")).map(s -> s.split(":")).collect(Collectors.toMap(array2 -> Integer.parseInt(array2[0]), array2 -> array2[1]));
        System.out.println("priceTypes :: " + priceTypes);
        //{1=RegularBistroEatInSalesUnitPrice, 2=RegularEatInSalesUnitPrice, 3=RegularBistroSalesUnitPrice}

        List<User> userList = new ArrayList<>();
        userList.add(new User("sam", "java"));
        userList.add(new User("sam", "js"));
        userList.add(new User("apollo", "html"));
        Map<String, String> userLangMap = userList.stream().collect(Collectors.groupingBy(User::getUser, Collectors.mapping(User::getLanguage, Collectors.joining(","))));
        System.out.println("userLangMap :: " + userLangMap); //{apollo=html, sam=java,js}

	    List<User> userList2 = new ArrayList<>();
        userList.stream().collect(Collectors.groupingBy(User::getUser, Collectors.mapping(User::getLanguage, Collectors.joining(","))))
                .forEach((key, value) -> {
                    User user = new User();
                    user.setUser(key);
                    user.setLanguage(value);
                    userList2.add(user);
                });
        System.out.println("userList2 :: " + userList2);

        Map<String, Integer> output = Stream.of("this", "word", "is", "the", "best")
                .collect(Collectors.groupingBy(x-> x.substring(0, 1), Collectors.reducing(0, x-> x.length(), (x, y)-> x + y)));
        System.out.println("output :: " + output); //output :: {b=4, t=7, w=4, i=2}

        List<List<Integer>> list10 = Arrays.asList(
                Arrays.asList(1, 2, 3, 4, 5, 6),
                Arrays.asList(7, 8, 9, 10));
        Map<Integer, List<Integer>> map10=
        list10.stream().collect(Collectors.groupingBy(Collection::size, Collectors.flatMapping(l -> l.stream().filter(i -> i%2==0), Collectors.toList())));
        System.out.println("map10 :: " + map10); //{4=[8, 10], 6=[2, 4, 6]}

        System.out.println("-------------Calculate basket-------------");
        AtomicReference<Double> atomicReference = new AtomicReference<>((double)0);
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> itemDetailsMap = new HashMap<>();
        for (int i=0;i<5;i++){
            String item = scanner.next();
            int quantity = scanner.nextInt();
            itemDetailsMap.put(item, quantity);
        }
        Map<String, Double> itemPricesMap = new HashMap<>();
        itemPricesMap.put("apple", 20.0);
        itemPricesMap.put("banana", 2.5);
        itemPricesMap.put("lemon",3.5);
        itemPricesMap.put("mango", 10.0);
        itemPricesMap.put("oranges", 4.5);

        itemDetailsMap.forEach((key, value) -> {
            Double priceOfItem = itemPricesMap.get(key);
            Double costOfItem = priceOfItem * value;
            BinaryOperator<Double> binaryOperator = (u,v) -> u+v;
            atomicReference.accumulateAndGet(costOfItem, binaryOperator);
        });

        System.out.println("Total cost :: " + atomicReference.get());

    }

    public static List<Item> getItemsWithPrice() {
        Item item1 = new Item("929292", "RegularSalesUnitPrice" ,new BigDecimal(123.23));
        Item item2 = new Item("929292", "FamilyPrice" ,new BigDecimal(123.23));
        Item item3 = new Item("3533", "RegularSalesUnitPrice" ,new BigDecimal(123.23));
        Item item4 = new Item("343435", "RegularSalesUnitPrice" ,new BigDecimal(332.23));
        Item item5 = new Item("45646", "RegularSalesUnitPrice" ,new BigDecimal(332.23));
        Item item6 = new Item("23424", "FamilyPrice" ,new BigDecimal(123.23));
        Item item7 = new Item("6456456", "RegularSalesUnitPrice" ,new BigDecimal(123.23));
        Item item8 = new Item("4344343", "RegularSalesUnitPrice" ,new BigDecimal(4234));
        Item item9 = new Item("45646", "RegularSalesUnitPrice" ,new BigDecimal(24234));
        Item item10 = new Item("23423", "RegularSalesUnitPrice" ,new BigDecimal(2424));
        return List.of(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10);
    }

    private static boolean isReferenceItem(boolean referenceItem) {
        return Objects.nonNull(referenceItem) && referenceItem;
    }

    private static List<ItemX> getItems() {
        ItemX itemX1 = new ItemX("chair", "SW", "RESRD1", "Living", false);
        ItemX itemX2 = new ItemX("chair", "SW", "RESRD2", "Living", true);
        ItemX itemX3 = new ItemX("table", "SW", "DRDSV2", "Dining", true);
        ItemX itemX4 = new ItemX("bed", "SW", "DGDSV2", "Bedroom", true);
        ItemX itemX5 = new ItemX("lamp", "SW", "DRDBV2", "Living", true);
        ItemX itemX6 = new ItemX("bedside-table", "SW", "DMDSV2", "Bedroom", true);
        ItemX itemX7 = new ItemX("bedside-table", "SW", "DMDSV3", "Bedroom", true);
        ItemX itemX8 = new ItemX("bed", "GE", "DMDSV2", "Bedroom", true);
        ItemX itemX9 = new ItemX("sofa", "GE", "DMDSV2", "Living", true);
        ItemX itemX10 = new ItemX("table", "SW", "DMDSV2", "Dining", true);
        return List.of(itemX1, itemX2, itemX3, itemX4, itemX5, itemX6, itemX7, itemX8, itemX9, itemX10);
    }

    public static List<Book> getBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("My First Book", "First Author", 1984, "0395489318", 230));
        bookList.add(new Book("My Second Book", "Second Author", 1984, "0345339711", 534));
        bookList.add(new Book("My Third Book", "Third Author", 1985, "0618129111", 773));
        bookList.add(new Book("My Another Book", "Second Author", 1986, "0335339711", 543));
        return bookList;
    }

    public static List<ServicePrice> getAllServicePrices() {

        Saving saving1 = new Saving(BigDecimal.valueOf(100), "saving1", Boolean.TRUE, "DMDM2O2MS", 1, "Voucher", new Discount(10));
        Saving saving2 = new Saving(BigDecimal.valueOf(50), "saving2", Boolean.TRUE, "UD3U9222", 1, "Voucher", new Discount(10));
        Saving saving3 = new Saving(BigDecimal.valueOf(133), "saving3", Boolean.TRUE, "9292MM22", 1, "Coupon", new Discount(20));
        Saving saving4 = new Saving(BigDecimal.valueOf(233), "saving4", Boolean.TRUE, "939SMSMSMS", 1, "Coupon", new Discount(5));
        Saving saving5 = new Saving(BigDecimal.valueOf(400), "saving5", Boolean.TRUE, "383JSSMSM3", 1, "Promotion", new Discount(8));
        Saving saving6 = new Saving(BigDecimal.valueOf(300), "saving6", Boolean.FALSE, "83838SNSNS", 1, "Promotion", new Discount(4));
        Saving saving7 = new Saving(BigDecimal.valueOf(200), "saving7", Boolean.TRUE, "9939SMMS033", 1, "Sale", new Discount(12));
        Saving saving8 = new Saving(BigDecimal.valueOf(1000), "saving8", Boolean.FALSE, "939SSMSMS", 1, "Sale", new Discount(14));
        Saving saving9 = new Saving(BigDecimal.valueOf(100), "saving9", Boolean.TRUE, "3I3SMSRNND", 1, "Sale", new Discount(6));
        Saving saving10 = new Saving(BigDecimal.valueOf(2000), "saving10", Boolean.FALSE, "9393MSSJW2", 1, "Voucher", new Discount(10));

        ServicePrice servicePrice1 = new ServicePrice();
        servicePrice1.setServicePriceId("servicePrice1");
        servicePrice1.setServicePriceName("SP1");
        servicePrice1.setSavingList(Arrays.asList(saving1, saving5, saving9, saving10));

        ServicePrice servicePrice2 = new ServicePrice();
        servicePrice2.setServicePriceId("servicePrice2");
        servicePrice2.setServicePriceName("SP2");
        servicePrice2.setSavingList(Arrays.asList(saving2, saving4, saving8));

        ServicePrice servicePrice3 = new ServicePrice();
        servicePrice3.setServicePriceId("servicePrice3");
        servicePrice3.setServicePriceName("SP3");
        servicePrice3.setSavingList(Arrays.asList(saving3, saving6, saving7));

        return Arrays.asList(servicePrice1, servicePrice2, servicePrice3);

    }
}
