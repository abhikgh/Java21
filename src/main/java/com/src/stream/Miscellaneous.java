package com.src.stream;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.math.IntMath;
import com.src.model.Department;
import com.src.model.Employee;
import com.src.model.FieldTest;
import com.src.model.Items;
import lombok.Data;
import lombok.SneakyThrows;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Spliterator;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ABC implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("Closing ABC..");
    }
}

class XYZ implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("Closing XYZ..");
    }
}

class ABCD {
    public void print(){
        System.out.println("ABCD...");
    }
}

@Data
class ASDF {
    private String aaa;
    private BigDecimal bbb;
}

public class Miscellaneous {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, CloneNotSupportedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // ---------------------------------- try-with-resources -----------------------------------//

        //try-with-resources resource that implements AutoClosable (override close()), will be closed by JVM
        //resources will be closed by JVM in the reverse order
        try (ABC abc = new ABC(); XYZ xyz = new XYZ()) {

            System.out.println("In try block...");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //In try block...
        //Closing XYZ..
        //Closing ABC..

        //try-with-resources
        String pathname = "src/main/resources/abc.txt";
        File file = new File(pathname);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        try (bufferedReader) {
            String line = "";
            StringJoiner stringJoiner = new StringJoiner(" ");
            while ((line = bufferedReader.readLine()) != null) {
                stringJoiner.add(line);
            }
            System.out.println(stringJoiner); //Sample text again

        } catch (Exception e) {
            e.printStackTrace();
        }

        //----------------------------------- BigDecimal --------------------------------------------

        BigDecimal b1 = new BigDecimal("34223.45992").setScale(2, RoundingMode.HALF_UP);
        System.out.println(b1); //34223.46
        BigDecimal b2 = new BigDecimal("34223.45392").setScale(2, RoundingMode.HALF_UP);
        System.out.println(b2); //34223.45
        BigDecimal b3 = new BigDecimal("34223.45500").setScale(2, RoundingMode.HALF_UP);
        System.out.println(b3); //34223.46

        BigDecimal bAdd = b1.add(b2);
        System.out.println(bAdd);  //68446.91

        BigDecimal bSub = b1.subtract(b2);
        System.out.println(bSub); //0.01

        BigDecimal bMul = b1.multiply(b2).setScale(2, RoundingMode.HALF_UP);
        System.out.println(bMul); //1171244872.14

        BigDecimal bDiv = b1.divide(b2, 2, RoundingMode.HALF_UP);
        System.out.println(bDiv); //1.00

        //minimum
        var bdMin = bAdd.min(bSub);
        System.out.println("bdMin :: " + bdMin); //0.01

        // ----------------------------------Generics -----------------------------------//

        /*
           1. Parameterized types. (The idea is to allow type (Integer, String, … etc, and user-defined types) to be a
           parameter to methods, classes, and interfaces)
           2. Type-Erasure - Compiler will replace the generic data type with the actual data type at Compile-time.
           3. Type-Safety - prevents ClassCastException
                   List<String> list = new ArrayList<>();
           4. Wildcard
                    Wildcard ? = unknown type
                    Bounded wildcards :  <? extends X>    //Upper Bound
                                         <? super X>	//Lower Bound
                    Unbounded wildcards :<?>


             List<Object>  vs               List<?> , List<? extends Object> , List<? extends T>
            ----------------------------------------------------------------------------------------
            anything can be added           cannot add anything to the list except null
                                              

                    List<String> list = new ArrayList<String>();    //List of String
                    List<String> list = new ArrayList<>();   //List of String
                    List<Number> lists = new ArrayList<Integer>();   //Compiler Error
                    List list = new ArrayList<String>();  // List of Objects
                    List list = new ArrayList<>();    // List of Objects
                    List<?> list = new ArrayList<?> ();  // compiler error
                    List<?> l   = new ArrayList<String>(); // ? = ? extends Object – cannot add to list when extends except null
                    List<? extends T> l = new ArrayList<X>(); // X should be a subclass of T, cannot add to list except null
                    List<? super T> l = new ArrayList<X>();  //
                                            X should be a superclass of T
                                            Added objects will have to be T/ subclass of T / null
                                            On retrieval, it has to be Object

        */



        List<Object> list = new ArrayList<Object>();
        list.add("abc");
        list.add(new BigDecimal(1223));
        ASDF asdf = new ASDF();

        asdf.setAaa(getValue(list.get(0)));
        asdf.setBbb(getValue(list.get(1)));
        System.out.println("asdf : " + asdf); //ASDF(aaa=abc, bbb=1223)

        //Generics :: Write a generic list to return a List of passed type
        List<String> list1 = getList("kdkd", "Dkdks", "dkdkm", "3djdjdd");
        System.out.println("List of String :: " + list1);

        List<Integer> list2 = getList(102, 22, 3929, 737);
        System.out.println("List of Integer :: " + list2);

        //Generics :: Write a generic function to accept a list of long/float/double and return a BigDecimal
        BigDecimal bd = getAddedValuesGeneric(List.of(10, 300, 202, 9202));
        System.out.println("getAddedValuesGeneric int :: " + bd);

        bd = getAddedValuesGeneric(List.of(123.23f, 3483.033003f, 9399292.939393f, 93929202.939292f));
        System.out.println("getAddedValuesGeneric float :: " + bd);

        bd = getAddedValuesGeneric(List.of(3931.43d, 928282.9292d, 737473.9292d, 66362.8282d));
        System.out.println("getAddedValuesGeneric double :: " + bd);

        //Generics :: Write a generic function to accept a list of long/float/double, change to int and return only the prime numbers
        List<Integer> primeNos = getPrimesGeneric(List.of(11.23f, 3483.033003f, 131.939393f, 93929202.939292f));
        System.out.println("primeNos float :: " + primeNos);

        primeNos = getPrimesGeneric(List.of(39.43d, 17.9292d, 737473.9292d, 66362.8282d));
        System.out.println("primeNos double :: " + primeNos);

        // Write a function to add list of numbers, float, long and double
        BigDecimal sum = getAddedValues(10, 123.23f, 34349.34d, 20001L, 8782.83f, 3931.43d, 82828L);
        System.out.println("Double sum :: " + sum);

        //Generic Functional Interface
        /*
            @FunctionalInterface
            public interface GenericFunctionalInterface<T, U, R>{
                R addValues(T t, U u);
            }
         */
        GenericFunctionalInterface<Integer, Integer, Integer> gfi1 = (x, y) -> x+y;
        int gfi1Result = gfi1.addValues(5, 10);
        System.out.println("gfi1Result:: " + gfi1Result); //15

        GenericFunctionalInterface<String, String, String> gfi2 = (x, y) -> x.concat(y);
        String gfi2Result = gfi2.addValues("aaa","bbb");
        System.out.println("gfi2Result:: " + gfi2Result); //aaabbb

        GenericFunctionalInterface<String, Integer, String> gfi3 = (x, y) -> x.concat(String.valueOf(y));
        String gfi3Result = gfi3.addValues("aaa",10);
        System.out.println("gfi3Result:: " + gfi3Result); //aaa10

        //Generic Class
        /*
            public class GenericRental<T>{

                private List<T> tList;
            
                public GenericRental(List<T> tList){
                    this.tList = tList;
                }
            
                public T getFirst(){
                    return tList.get(0);
                }
            }
        */
        //Generic Class
        List<String> list3 = Arrays.asList("828292","djjdjd","sgsgd","ndndn");
        GenericRental genericRental = new GenericRental(list3);
        String firstElement = (String) genericRental.getFirst();
        System.out.println("firstElement :: " + firstElement);

        // ---------------------------------- JSON Masking -----------------------------------//

        File file0 = new File("src/main/resources/Input.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, Boolean.TRUE);
        //Json -> Object -> String
        PersistPersonResponse persistPersonResponse = objectMapper.readValue(file0, PersistPersonResponse.class);
        String jsonRequest = objectMapper.writeValueAsString(persistPersonResponse);
        System.out.println(jsonRequest);

        String fieldsToMask = "subOrgTxt,personId";
        List<String> maskedFields = Arrays.stream(fieldsToMask.split(",")).collect(Collectors.toList());

        JsonNode jsonNode = new ObjectMapper().readTree(jsonRequest);
        for (String key : maskedFields) {
            if (Objects.nonNull(jsonNode.findValue(key))) {
                for (JsonNode jsonNode1 : jsonNode.findParents(key)) {
                    Optional.ofNullable(jsonNode1).ifPresent(node -> ((ObjectNode) node).put(key, "XXXXXXX"));
                }
            }
        }

        System.out.println("Masked JSON----------------------");
        System.out.println(jsonNode.toString());

        //------------------------------------- Base 64 encoding and decoding --------------------------------//

        System.out.println("Base64 encoding and decoding----------------------");

        String str0 = "some sample test string";

        //Encode String to Base64 and Decode
        String encodedString = java.util.Base64.getEncoder().encodeToString(str0.getBytes(StandardCharsets.UTF_8));
        System.out.println("encodedString :: " +encodedString); //c29tZSBzYW1wbGUgdGVzdCBzdHJpbmc=
        String decodedString = new String(java.util.Base64.getDecoder().decode(encodedString.getBytes(StandardCharsets.UTF_8)));
        System.out.println("decodedString :: " +decodedString); //some sample test string

        //Encode file to String and Decode
        Path path = Paths.get("src/main/resources/Flight Ticket.pdf");
        String str = java.util.Base64.getEncoder().encodeToString(Files.readAllBytes(path));
        System.out.println("encodedFile :: " + str);  //JVBERi0xLjcNCiW1tbW1DQoxIDAgb2JqDQo8PC9UeXBlL0NhdGFsb2cvUGFnZXMgMiAwIFI

        byte[] bArrFile2 = java.util.Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("src/main/resources/Flight Ticket2.pdf"));
        bufferedOutputStream.write(bArrFile2);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        System.out.println("New file generated...");

        //-------------------------------------- Jasypt Encryption (Java Simplified Encryption) ------------//
        //StandardPBEStringEncryptor
        System.out.println("Jasypt encryption----------------------");
        String plainText = "abcdefghijkl";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("Sdmjsj23#2");
        String result = encryptor.encrypt(plainText);
        System.out.println("Jasypt encryption :: " + result); //oszl/I621hiXrnzxTz3Ed7swxPuCbLrj

        // --------------------------- Functional Interface ----------------------------------//

        Function<Integer, Double> half = a -> a / 2.0;
        System.out.println("half apply 1:: " + half.apply(10)); // 5.0  // [R apply(T t);]

        half = a -> a / 2.0;
        half = half.andThen(a -> a * 3);
        System.out.println("half apply 2:: " + half.apply(10)); //15.0

        half = a -> a / 2.0;
        half = half.compose(a -> a * 3);  //first compose then function
        System.out.println("half apply 3::" + half.apply(5)); //7.5

        Function<Integer, Integer> func = Function.identity();
        System.out.println("function identity :: " + func.apply(10)); //10

        // ------------------------------------ Flatmap -----------------------------------//

        //make individual items in the list a stream

        //Get all words
        List<String> listStr = new ArrayList<>();
        listStr.add("corejava advancedjava oracle");
        listStr.add("nit kit nacre");
        listStr.add("abc sg hk");
        List<String> words = listStr.stream().flatMap(x-> Arrays.asList(x.split(" ")).stream()).toList();
        System.out.println(words); //[corejava, advancedjava, oracle, nit, kit, nacre, abc, sg, hk]

        //Combine lists
        List<Integer> listInt1 = Arrays.asList(1,2,3);
        List<Integer> listInt2 = Arrays.asList(4,5,6);
        List<Integer> listInt3 = Arrays.asList(7,8,9);
        List<Integer> listInt4 = List.of(listInt1, listInt2, listInt3).stream().flatMap(list0-> list0.stream()).toList();
        System.out.println(listInt4); //[1, 2, 3, 4, 5, 6, 7, 8, 9]

        //Combine arrays
        String[] arr1 = new String[] {"red","blue","green","yellow"};
        String[] arr2 = new String[] {"Kite","Marble","Ball","Balloon"};
        List<String> listS = List.of(arr1, arr2).stream().flatMap(array -> Arrays.asList(array).stream()).toList();
        System.out.println(listS); //[red, blue, green, yellow, Kite, Marble, Ball, Balloon]

        //----------------------------------- Reflections -----------------------------//
        //Reflections :: Gets attributes of a class at runtime; also instantiate objects, call methods and set field values using reflection.
        System.out.println("-------------Reflections-----------------------");
        for(Field field : FieldTest.class.getDeclaredFields()){
            System.out.println(field.getName());
        }

        for(Method method : FieldTest.class.getDeclaredMethods()){
            System.out.println(method.getName());
            if (method.isAnnotationPresent(JsonIgnore.class)) {   //@Retention(RetentionPolicy.RUNTIME)
                System.out.println("Annotation present on method :: " + method.getName());
            }
        }

        for(Class class0 : FieldTest.class.getDeclaredClasses()){
            System.out.println(class0.getName());
        }

        Package aPackage = FieldTest.class.getPackage();
        Class[] interfaceArr = FieldTest.class.getInterfaces();
        Class superClass = FieldTest.class.getSuperclass();

         System.out.println("Reflections to invoke private method from outside of class");
         Method method = FieldTest.class.getDeclaredMethod("somePrivateMethod2",String.class); //String is input parameter
         method.setAccessible(true);
         FieldTest fieldTest = new FieldTest();
         method.invoke(fieldTest, "test");

        //-------------------------------- SHA-256 (Standard Hashing Algorithm) ---------------------------------------//
        //It undergoes 64 rounds of hashing and calculates a hash code that is a 64-digit hexadecimal number.

       // String input = new BufferedReader(new InputStreamReader(System.in)).readLine(); //helloWorld
        String input = "helloWorld";

        Pattern pattern = Pattern.compile("[A-Za-z0-9]{6,20}");
        Matcher matcher = pattern.matcher(input);

        if(matcher.matches()) {
            byte[] bArr = MessageDigest.getInstance("SHA-256").digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert byte array into signum representation
            BigInteger number = new BigInteger(1, bArr);

            // Convert message digest into hex value
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros
            while (hexString.length() < 64)
            {
                hexString.insert(0, '0');
            }

            String hexSha256 =  hexString.toString();
            System.out.println("hexSha256 :: " + hexSha256); //11d4ddc357e0822968dbfd226b6e1c2aac018d076a54da4f65e1dc8180684ac3


        }else {
            System.out.println("no match");
        }

        //------------------------------------ Cloning -----------------------------------------------//

        Department deptG = new Department(28383, "Geography", "Associate");
        Employee empG1 = new Employee(111, "John", deptG,new BigDecimal(223)); //prototype object

        //Shallow clone :: referenced Object is shared between clones
        //Deep clone    :: referenced Object is not-shared between clones (exclusive)
        //                 referenced objects has to be set after cloning them

        /*
        Employee empG2 = (Employee) empG1.clone();
        empG2.getDepartment().setDesignation("VP");
        System.out.println("designation empG2 : " + empG2.getDepartment().getDesignation()); //VP
        System.out.println("designation empG1 : " + empG1.getDepartment().getDesignation()); //VP
        */

        //Deep clone
        Employee empG3 = (Employee) empG1.clone();
        empG3.getDepartment().setDesignation("VP");
        System.out.println("designation empG3 : " + empG3.getDepartment().getDesignation()); //VP
        System.out.println("designation empG1 : " + empG1.getDepartment().getDesignation()); //Associate
        //------------------------------------ Inner class -----------------------------------------------//

        /*static class InnerStatic {
        private class Private {
            private int i = 10;
            private static int j = 20;
            private String evenOdd(int num) {
                return ((num%2 == 0) ? "even" : "odd");
            }
         }
       }

        //static Inner class
        Miscellaneous.InnerStatic.Private sip = new Miscellaneous.InnerStatic().new Private();
        String ans = sip.evenOdd(5);
        System.out.println("evenOdd :: " + ans + ":: i="+sip.i +" ::j="+ InnerStatic.Private.j);
        //evenOdd :: odd:: i=10 ::j=20*/

        //Anonymous Inner class
        class ABCD {
            public void print(){
                System.out.println("ABCD...");
            }
        }
        ABCD abcd = new ABCD(){
            @Override
            public void print() {
                System.out.println("ABCD...");
            }
        };
        abcd.print();

        //Inner class variable needs to be final or effectively final
        /*
            final int[] count = {10};
            Thread producer = new Thread(){
                @Override
                public void run() {
                    try {
                        while(count[0]-- >0) {
                            int produced = (int) (Math.random() * 100);
                            priorityBlockingQueue.put(produced);
                            System.out.println("Produced :: " + produced);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
         */

        //------------------------anymatch ,allmatch, nonematch starts ----------------------------------------------------//

        String aa = "abc";

        if(check1(aa)!=null){
            doAction(check1(aa));
        } else if (check2(aa)!=null) {
            doAction(check2(aa));
        } else if (check3(aa) != null) {
            doAction(check3(aa));
        }

       Optional.ofNullable(check1(aa))
             .or(() -> Optional.ofNullable(check2(aa)))
             .or(() -> Optional.ofNullable(check3(aa)))
             .ifPresent(ss -> doAction(ss));


        if(aa.equalsIgnoreCase("abc") || aa.equalsIgnoreCase("djdjjd") || aa.equalsIgnoreCase("kdkdkd")){
            System.out.println("yes...");
        }

        if(Stream.of("abc","dhdhdh","jdjdjd").anyMatch(allowedValues -> allowedValues.equalsIgnoreCase(aa))){
            System.out.println("yes...");
        }

        Optional.ofNullable(aa)
                .filter(val -> Stream.of("abc","dhdhdh","jdjdjd").anyMatch(allowedValues -> allowedValues.equalsIgnoreCase(val)))
                .ifPresent(val -> System.out.println("yes.."));

        Optional.ofNullable(aa)
                .filter(val -> List.of("abc","dhdhdh","jdjdjd").contains(val))
                .ifPresent(val -> System.out.println("yes.."));



        List<String> fruitList = Arrays.asList("mango","apple","guava","litchi","orange","yellow");
        List<String> colourList = Arrays.asList("red","blue","green","yellow");
        List<String> toyList = Arrays.asList("chess","carrom","puzzle","checker", "yellow");

        //check if all elements in list1 not in list2, list3
        boolean matched = fruitList.stream().allMatch(fruit -> !colourList.contains(fruit) && !toyList.contains(fruit));
        System.out.println("allElementsNotInEitherList :: " + matched); //false

        //check if any element in list1 is in list2/list3
        boolean matched2 = fruitList.stream().anyMatch(fruit -> colourList.contains(fruit) || toyList.contains(fruit));
        System.out.println("anyElementNotInEitherList :: " + matched2); //true

        //check if is there any element in list1 which is NOT in list2 but IS in list3
        boolean matched3 = fruitList.stream().anyMatch(fruit -> !colourList.contains(fruit) && toyList.contains(fruit));
        System.out.println("anyElementNotInListButIsInList3 :: " + matched3); //false

        System.out.println("GUAVA found ::" + fruitList.stream().anyMatch(fruit->fruit.equalsIgnoreCase("GUAVA"))); //true
        System.out.println("GUAVA found ::" + fruitList.stream().anyMatch("GUAVA"::equalsIgnoreCase)); //true
        System.out.println("GUA found ::" + fruitList.stream().anyMatch(fruit->fruit.toUpperCase().contains("GUA"))); //true
        System.out.println("No BERRIES found ::" + fruitList.stream().noneMatch("berries"::equalsIgnoreCase)); //true



        //------------------------anymatch ,allmatch, nonematch ends ----------------------------------------------------//

        //Iterate a list and break when found
        Items item1 = new Items("2339282", null, "ART");
        Items item2 = new Items("9393929", null, "SGR");
        Items item3 = new Items("12344", null, "ART");
        Items item4 = new Items("3344445", null, "ART");
        List<Items> itemsList = new ArrayList<>();
        itemsList.addAll(Arrays.asList(item1, item2, item3, item4));

        boolean isCorrectItemsFound = checkCorrectItems(itemsList);
        System.out.println("isCorrectItemsFound :: " + isCorrectItemsFound);

        String[] strArr = new String[]{null, null, "ball", "bed", "chair", "aeroplane", "fan", "toy","kite", "Mobile","car"};

        //whichever not null convert to upper case
        List<String> finalList = Arrays.stream(strArr).filter(Objects::nonNull).map(String::toUpperCase).toList();
        System.out.println(finalList);

        //Check if null is present(anyMatch)
        boolean nullPresent = Arrays.stream(strArr).anyMatch(Objects::isNull);
        String ans = nullPresent?"nullPresent":"nullNotPresent";
        System.out.println(ans);

        //If the word starts with b make the word uppercase
       finalList = Arrays.stream(strArr).filter(s -> Objects.nonNull(s) && s.startsWith("b")).map(String::toUpperCase).toList();
        System.out.println(finalList);

        //If the word starts with b,c,d,e make the word lowercase, if starts with a,f,s,t make the word uppercase
        finalList = Arrays.stream(strArr).filter(Objects::nonNull).map(val -> checkValSwitch(val)).toList();
        System.out.println(finalList);

        //------------------------spliterator ----------------------------------------------------//

        List<Integer> integerList2 = List.of(23, 34, 56,231, 36);
        integerList2.spliterator().tryAdvance(elem -> System.out.println("tryAdvance :: " + elem)); //23
        integerList2.spliterator().tryAdvance(elem -> System.out.println("tryAdvance :: " + elem)); //23
        integerList2.spliterator().forEachRemaining(elem -> System.out.println("forEachRemaining :: " + elem)); //23, 34, 56,231, 36

        Spliterator<Integer> spliterator = integerList2.spliterator();
        Spliterator<Integer> spliterator2 = spliterator.trySplit();
        spliterator.forEachRemaining(System.out::println);
        System.out.println("-------------------------");
        spliterator2.forEachRemaining(System.out::println);
        
        /*
            56
            231
            36
            -------------------------
            23
            34
         */

        //-----------------------Random -----------------------------
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        int randomNum = new Random().nextInt((9000 - 1000) + 1) + 1000;
        System.out.println("randomNum :: " + randomNum);

    }

    private static boolean checkCorrectItems(List<Items> itemsList) {
        boolean artFound = false;
        boolean sgrFound = false;
        for(Items item : itemsList){
            if(!artFound && (item.getItemType().equalsIgnoreCase("ART") && "2339282,33444452".contains(item.getItemNo()))){
                artFound = true;
            }else if (!sgrFound && (item.getItemType().equalsIgnoreCase("SGR") && "9393929,233".contains(item.getItemNo()))){
                sgrFound = true;
            }
            if(artFound && sgrFound){
                return true;
            }
        }
        return false;
    }

    private static String checkValSwitch(String val) {
        return switch (val.charAt(0)){
            case 'b','c','d','e' -> val.toLowerCase();
            case 'a','f','s','t' -> val.toUpperCase();
            default -> val;
        };
    }

    private static String checkVal(String val) {
        if(val.startsWith("b")){
            return val.toUpperCase();
        }
        return val;
    }


    private static void doAction(String aa) {
        System.out.println(aa.concat("ddd"));
    }

    private static String check3(String aa) {
        return aa.toUpperCase();
    }

    private static String check2(String aa) {
        return null;
    }

    private static String check1(String aa) {
        return null;
    }

    private static <T> List<Integer> getPrimesGeneric(List<? extends Number> ts) {
        return ts.stream().map(t -> t.intValue()).filter(t -> IntMath.isPrime(t)).collect(Collectors.toList());
    }

    private static <T> BigDecimal getAddedValuesGeneric(List<? extends Number> ts) {
        return new BigDecimal(ts.stream().collect(Collectors.summingDouble(v -> v.doubleValue()))).setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal getAddedValues(int i, float v, double v1, long l, float v2, double v3, long l1) {
        return new BigDecimal(i + v + v1 + l + v2 + v3 + l1).setScale(2, RoundingMode.HALF_UP);
    }

    private static <T> List<T> getList(T... ts) {
        List<T> tList = new ArrayList<T>();
        Collections.addAll(tList, ts);
        return tList;
    }

    private static <T> T getValue(Object o) {
        return (T) o;
    }

    static class InnerStatic {
        private class Private {
            private int i = 10;
            private static int j = 20;
            private String evenOdd(int num) {
                return ((num%2 == 0) ? "even" : "odd");
            }
        }
    }
}
