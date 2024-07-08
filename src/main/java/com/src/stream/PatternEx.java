package com.src.stream;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    ?   : 0/1
    *   : 0/more
    +   : 1/more
    {n} : n
    {n+} : n+
    {n,m} : n to m

    \d : numeric
    \D : NOT numeric
    \s : space
    \S : NOT space
    \w : word char [a-zA-Z0-9]
    \W : NOT word char
    \b : word boundary
    \B : NOT word boundary

    [a-z-[bc]] 	a through z, except for b and c
    . 	Any 1 character
    ^ 	negate / start of line
    $ 	end of line


 */
public class PatternEx {

    public static void main(String[] args) throws IOException {

        //1. if(pattern.matcher(input).matches()) - checks if pattern Fully Matches the string

        String input = "lkd22sA@3";
        Pattern pattern = Pattern.compile("[0-9a-zA-Z!@#]{4,10}");
        if (pattern.matcher(input).matches())
            System.out.println("input matches pattern 1"); //true

        // Only 3,4 or 5 allowed
        input = "5";
        pattern = Pattern.compile("3|4|5");
        if (pattern.matcher(input).matches())
            System.out.println("input matches pattern 2");

        input = "Hello World";
        pattern = Pattern.compile("Hello.*");
        if (pattern.matcher(input).matches()) {
            System.out.println("matched");
        } else {
            System.out.println("Non matched");
        }

        //Only 0-9 , min 8
        input = "59393939393";
        pattern = Pattern.compile("[0-9]{8,}");
        if (pattern.matcher(input).matches())
            System.out.println("input matches pattern 3"); //true

        //firstName lastName/firstName
        input = "andn ndnd";
        pattern = Pattern.compile("[a-z]+\\s{0,1}[a-z]*");
        if (pattern.matcher(input).matches())
            System.out.println("input matches pattern name 4");

        //contains at least 1 number
        input = "dhdhdjdjdj2jdjdjd";
        pattern = Pattern.compile(".*[0-9]+.*");
        if (pattern.matcher(input).matches())
            System.out.println("input matches pattern 5");

        //2. if(pattern.asPredicate().test(input)) - checks if pattern Is Found in the String

        input = "aaeebbeecceeddee3223ddew23ddedsd";
        pattern = Pattern.compile("\\d{4}[a-z]{4}\\d{2}");
        if (pattern.asPredicate().test(input))
            System.out.println("input contains pattern 1"); //true

        //3. pattern.asMatchPredicate().test(input) :: check if pattern Fully Matches the string

        input = "aaeebbeecceeddee3223ddew23ddedsd";
        pattern = Pattern.compile("\\d{4}[a-z]{4}\\d{2}");
        if (pattern.asMatchPredicate().test(input))
            System.out.println("input matches pattern 11"); //false

        input = "aaeebbeecceeddee3223ddew23ddedsd";
        pattern = Pattern.compile(".*\\d{4}[a-z]{4}\\d{2}.*");
        if (pattern.asMatchPredicate().test(input))
            System.out.println("input matches pattern 12"); //true


        //4. while(matcher.find()) - matcher.group(0)

        //check if input String contains the pattern and get the group
        input = "93939skksk3000202kd23-23-34ipAddress:1203023fkfkfkkfk";
        pattern = Pattern.compile(".*(ipAddress:\\d{7}).*");
        if (pattern.asMatchPredicate().test(input)) {
            System.out.println("input contains ipAddress");
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                System.out.println("ipAddress is :: " + matcher.group(1).split(":")[1]);
            }
        }

        //Groups
        //Find groups of 5-6 digit numbers in a String
        input = "ladslj12ldjalsd13mla678882ldsllkskdl907128j12345";
        pattern = Pattern.compile("\\d{5,6}");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println("matcher group : " + matcher.group()); //678882 907128 12345
        }

        input = "93939skksk3000202kd23-23-34fkfkfkkfk";
        pattern = Pattern.compile("\\d{2}-\\d{2}-\\d{2}");
        if(pattern.asPredicate().test(input))
            System.out.println("input contains pattern 2"); //true

        input = "39838sstorejkfjfjjfstoresjfjfjfproductjfjjddj";
        pattern = Pattern.compile(".*store.*stores.*product.*");
        if(pattern.asMatchPredicate().test(input))
             System.out.println("input contains pattern 4 :: "); //true
        if(pattern.asPredicate().test(input))
             System.out.println("input contains pattern 4"); //true

        //read the file line-by-line and check each line if the line contains the pattern and if so return the group
        pattern = Pattern.compile(".*(ipaddress:\\d{7}).*");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/abcNew.txt"))) {
            String line = bufferedReader.readLine();
            while (StringUtils.isNotBlank(line)) {
                System.out.println("Line :: " + line);
                if (pattern.asPredicate().test(line)) {
                    //here both pattern.asPredicate().test(input) and
                    // pattern.asMatchPredicate().test(input) will work because the patttern has .*...*
                    matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        System.out.println("ipAddress from file is :: " + matcher.group(1).split(":")[1]);
                    }
                }
                // read next line
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //read the file line-by-line and check each line if the line contains the pattern and if so return the group
        pattern = Pattern.compile(".*(apple=\\w+\\s).*");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/applesNew.txt"))) {
            String line = bufferedReader.readLine();
            while (StringUtils.isNotBlank(line)) {
                if (pattern.asMatchPredicate().test(line)) {
                    matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        System.out.println("Apple Colour :: " + matcher.group(1).split("=")[1]);
                    }
                }
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //difference between Pattern.asMatchPredicate and Pattern.asPredicate
        pattern = Pattern.compile("abc");
        System.out.printf("asPredicate: 123abc123: %s\n", pattern.asPredicate().test("123abc123")); //true
        System.out.printf("asMatchPredicate: 123abc123: %s\n", pattern.asMatchPredicate().test("123abc123")); //false

        // Check date with Pattern yyyyMMMdd
        input = "2001Nov05";
        pattern = Pattern.compile("\\d{4}\\w{3}\\d{2}");
        matcher = pattern.matcher(input);
        if (matcher.matches()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMMdd");
            simpleDateFormat.setLenient(false);
            try {
                Date date2 = simpleDateFormat.parse(input);
                System.out.println(date2);  //Mon Nov 05 00:00:00 IST 2001
            } catch (Exception e) {
                System.out.println("Invalid date :: date");
            }
        } else {
            System.out.println("Invalid date :: format");
        }

        //Find word after a given word (case-insensitive)
        input = "John writes and John plays and John sings and John dances and JOHN sleeps";
        pattern = Pattern.compile("(?i)(John) (\\w+\\b)");
        matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println(matcher.group(2)); //writes plays sings dances sleeps
        }

        //5. matcher.results().count()

        //Count of a word in a sentence
        input = "John writes and John plays and john sings and John dances and JOHN sleeps";
        pattern = Pattern.compile("(?i)(John)");
        matcher = pattern.matcher(input);
        long count = matcher.results().count();
        System.out.println("count of words John:: " + count); //5

        pattern = Pattern.compile("(John)");
        matcher = pattern.matcher(input);
        count = matcher.results().count();
        System.out.println("count of words case sensitive :: " + count); //3

        //Greedy match (Longest match) (.*)
        input = "<>kdkdkdkd</><>jjrjdjdj</><>ridjdjd</>";
        pattern = Pattern.compile("<>(.*)</>");
        matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println("Greedy match :: " + matcher.group(1)); // kdkdkdkd</><>jjrjdjdj</><>ridjdjd
        }

        //Lazy match (Shortest match) (.*?)
        input = "<>kdkdkdkd</><>jjrjdjdj</><>ridjdjd</>";
        pattern = Pattern.compile("<>(.*?)</>");
        matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println("Lazy match :: " + matcher.group(1)); //kdkdkdkd jjrjdjdj ridjdjd
        }

        //replace occurence
        input = "{\"familyId\":\"fam123\",\"customerId\": \"XXXX,John\",\"customerName\":\"cusName123\",\"customerType\":\"XXXX,Regular\"}";
        pattern = Pattern.compile("(XXXX,)(\\w+\\b)"); //group-0 is entire String
        matcher = pattern.matcher(input);
        while (matcher.find()) {
            input = input.replaceFirst(matcher.group(0), matcher.group(2));
        }
        System.out.println("replaced string :: " + input);
        //{"familyId":"fam123","customerId": "John","customerName":"cusName123","customerType":"Regular"}

    }
}

