package com.src.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.model.NewPerson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class NewPerson2 {
    private String name;
    private String address;
}

public class StringEx {

    public static void main(String[] args) throws JsonProcessingException {

        String string1 = "string1";
        String string2 = "string2";

        //StringJoiner (joiner, prefix, suffix)
        String stringJoiner = new StringJoiner("|", "prefix", "suffix")
                .add(string1)
                .add(string2)
                .toString();
        System.out.println("stringJoiner :: " + stringJoiner); //prefixstring1|string2suffix

        String stringJoiner2 = new StringJoiner("|", "", "")
                .add(string1)
                .add(string2)
                .toString();
        System.out.println("stringJoiner2 :: " + stringJoiner2); //string1|string2

        //String.join (joiner)
        String stringJoin = String.join("|", string1, string2);
        System.out.println("stringJoin :: " + stringJoin); // string1|string2

        //String :: split
        String str = "Welcome-to-Java-Hello-World";

        String ans = str.split("-")[1];
        System.out.println(ans); //to

        //split(splitter, limit) : limit - how many Strings the original String will be split into
        ans = str.split("-", 2)[1];
        System.out.println(ans); //to-Java-Hello-World

        ans = str.split("-", 3)[1];
        System.out.println(ans); //to

        ans = str.split("-", 3)[2];
        System.out.println(ans); //Java-Hello-World

        //String :: isEmpty/isBlank
        String strTest = " ";
        System.out.println("isEmpty :: " + strTest.isEmpty()); //false
        System.out.println("isBlank :: " + strTest.isBlank()); //true

        //All words in a paragraph
        strTest = """
                Hi All.
                This is a new line.
                This is another line.
                Thank you.
                """;

        String words = strTest.lines().collect(Collectors.joining());
        System.out.println("All words in paragraph are :: ");
        System.out.println(words);
        //Hi All. This is a new line.This is another line. Thank you.

        //Count of words in a paragraph
        long noOfWordsInParagraph = Arrays.asList(strTest.lines().collect(Collectors.joining()).split(" ")).stream().count();
        System.out.println("noOfWordsInParagraph :: " + noOfWordsInParagraph);

        //Count of words in a line
        str = "Welcome to Java Hello World";
        long noOfWordsInLine = Arrays.asList(str.split(" ")).stream().count();
        System.out.println("noOfWordsInLine :: " + noOfWordsInLine);

        //String :: strip
        strTest = "   Hi   ";
        ans = strTest.strip();
        System.out.println("after strip :: " + ans); //Hi

        //String :: repeat
        String abc = "abc";
        abc = abc.repeat(10);
        System.out.println(abc);  //abcabcabcabcabcabcabcabcabcabc

        //String :: format
        strTest = String.format("This is the %s statement which has %d parameters of which 1 is float value %f and 1 boolean %s", "first", 2, 32.23f, true);
        System.out.println("formatted string :: " + strTest);
        //This is the first statement which has 2 parameters of which 1 is float value 32.230000 and 1 boolean true

        strTest = String.format("This is the %s statement which has %d parameters of which 1 is float value %.2f and 1 boolean %s", "first", 2, 32.23f, true);
        System.out.println("formatted string :: " + strTest);
        //This is the first statement which has 2 parameters of which 1 is float value 32.23 and 1 boolean true

        //Remove all null and blanks and join the strings by ;
        str = "abhik, rahul, joy, ,1,  ";
        ans = Arrays.asList(str.split(",")).stream().map(String::trim).filter(StringUtils::isNotBlank).collect(Collectors.joining(";"));
        System.out.println(ans); //abhik;rahul;joy;1

        //StringUtils :: org.apache.commons.lang3.StringUtils
        str = "  ";
        System.out.println("isBlank :: " + StringUtils.isBlank(str)); //true
        System.out.println("isNotBlank :: " + StringUtils.isNotBlank(str)); //false
        System.out.println("isEmpty :: " + StringUtils.isEmpty(str)); //false
        System.out.println("empty :: " + StringUtils.isNotEmpty(str)); //true

        //StringUtils :: defaultIfBlank
        String inputStr = " ";
        ans = StringUtils.defaultIfBlank(inputStr, "test");
        System.out.println("defaultIfBlank :: " + ans); //test

        //StringUtils :: rightPad(str,length, padChar)
        inputStr = "abcd";
        int lengthInput = inputStr.length();

        if (lengthInput > 10) {
            ans = ans.substring(0, 10);
        } else {
            ans = StringUtils.rightPad(ans, 10, "1");
        }
        System.out.println("rightPad :: " + ans); //abcd111111

        //StringUtils :: leftPad(str,length,padChar)
        inputStr = "abcd";
        ans = StringUtils.leftPad(inputStr, 10, "0");
        System.out.println("leftPad :: " + ans); //000000abcd

        //StringUtils :: substringBetween
        inputStr = "aljldsdljdajd<tt>kaskdhakjdshkdhakd</tt>sddjlajd<TransactionId>abc123</TransactionId>kasdkdkddjd";
        ans = StringUtils.substringBetween(inputStr, "<TransactionId>", "</TransactionId>");
        System.out.println("substringBetween :: " + ans);  //abc123

        //substring
        inputStr = "abcdefghijklmnop";
        ans = inputStr.substring(2, 10); //cdefghij {2,9}
        System.out.println("substring :: " + ans);

        //switch-case
        inputStr = "test";
        switch (inputStr.toUpperCase()) {
            case null -> throw new RuntimeException("null input");
            case "XYZ", "ABC" -> ans = inputStr;
            case "TEST" -> ans = "testing";
            default -> ans = "none";
        }
        System.out.println("switch-case 2 :: " + ans);  //testing

        //switch-case-when
        final String inputStr2 = "yes";
        switch(inputStr2){
            case null -> throw new RuntimeException("its null value");
            case String s when "Yes".equalsIgnoreCase(inputStr2) -> ans = "Yes its a yes";
            case String s when "No".equalsIgnoreCase(inputStr2) -> ans = "No its a no";
            case String s -> ans = inputStr2.toUpperCase(); //default
        }
        System.out.println("switch-case-when :: " + ans);  //Yes its a yes

        ans = getAnsSwitch(inputStr);
        System.out.println("switch-case 3 :: " + ans);

        //check palindrome (one string is reverse of another)
        String str1 = "abcde";
        String str2 = "edcba";
        boolean palindrome = new StringBuilder(str1).reverse().toString().equals(str2);
        System.out.println("palindrome :: " + palindrome); //true

        // append
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            builder.append("value").append(i);
        }
        String result = builder.toString();

        //permutation
        inputStr = "abcd";
        permutation("", inputStr);

        //String.chars() - returns IntStream of character codes
        //get first duplicate character in a String
        System.out.println("firstNonUniqueChar");
        inputStr = "camelsl";
        Set<Character> uniqueSetOfChars = new HashSet<>();
        inputStr.chars().mapToObj(i -> (char) i).toList().stream().filter(character -> !uniqueSetOfChars.add(character)).findFirst().ifPresent(System.out::println);

        //check if 2 strings are Anagrams
        str1 = "Silent";
        str2 = "Listen";
        boolean isAnagram = (str1.toUpperCase().chars().mapToObj(i -> (char) i).sorted().toList()).equals(str2.toUpperCase().chars().mapToObj(i -> (char) i).sorted().toList());
        System.out.println("isAnagram :: " + isAnagram);

        //count of a letter in a String
        str1 = "This is a test string, check count of letter a";
        long count = str1.chars().mapToObj(i -> (char) i).filter(c -> c == 'a').count();
        System.out.println("count of a ::" + count);

        //check if a String has all the characters between [a-z]
        str1 = "abcdefghijklmnopqrstuvwxyzjdjdj2k2k2sr9ndsnjdjd";
        boolean isStringHavingAllCharacters = str1.toLowerCase().chars().mapToObj(i -> (char) i).filter(c -> c >= 'a' && c <= 'z').distinct().count() == 26;
        System.out.println("isStringHavingAllCharacters :: " + isStringHavingAllCharacters);

        //public record NewPerson(String name, String address) {}
        //before Record pattern
        System.out.println("before Record pattern");
        NewPerson newPerson = new NewPerson("John", "little avenue");
        System.out.println(newPerson.name());
        System.out.println(newPerson.address());

        //after Record pattern
        System.out.println("after Record pattern");
        NewPerson newPerson2 = new NewPerson("John", "little avenue");
        if(newPerson2 instanceof NewPerson(String name, String address)){
            System.out.println(name);
            System.out.println(address);
        }

        //String :: Text block (start from next line)
        System.out.println("----Text block-----");
        String json = """
                 {
                  "name": "John",
                  "address": "little avenue"
                 }
                """;
        ObjectMapper objectMapper = new ObjectMapper();
        NewPerson2 np2 = objectMapper.readValue(json, NewPerson2.class);
        System.out.println(np2.getName() + "--" + np2.getAddress());

        inputStr = """
                This is the first line
                This is the second line
                This is the third line      
                This is the fourth line
                This is the %s line
                """.formatted("fifth");
        System.out.println(inputStr);
        /*
            This is the first line
            This is the second line
            This is the third line
            This is the fourth line
            This is the fifth line
        */


        /*
        String Pool
        =======================
        Pool of unique Strings
        String literals go to String Pool
        JVM first checks the pool. If the string is already present in the pool, a reference to the pooled instance returns.
        If the string does not exist in the pool, then String is placed in pool and its reference is returned.

        String s = "abc";   //recommended , s is String literal which goes to String Pool
        String s = new String("abc"); //here 2 objects are created , 1 in Heap and 1 in String Pool.
                                s refers to the Heap object,
                                to have s refer to the String Pool object use s.intern()



        Advantages of String being immutable
        ===========================================
        Thread-safe
        Hashmap-key
        Class.forName(“X”).newInstance()

        String          vs      StringBuffer            vs     StringBuilder
        ======================================================================================
        immutable               synchronized                    fastest
          SCP                    heap                           heap                                                      

         */


    }

    private static String getAnsSwitch(String inputStr) {
        return switch (inputStr.toUpperCase()) {
            case "XYZ", "ABC" -> inputStr;
            case "TEST" -> "testing";
            default -> "none";
        };
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
        }
    }
}
