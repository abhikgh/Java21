package com.src.stream;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.model.Marks;
import com.src.model.ReportCard;
import com.src.model.Sport;
import com.src.model.Student;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoEx {

    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/reportCard.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, Boolean.TRUE);
        ReportCard reportCard = objectMapper.readValue(file, ReportCard.class);

        //1. Students who have Karate in sports
        List<Student> studentList1 =
                reportCard.getStudents().stream().filter(student -> student.getSports().stream().map(Sport::getSportName).toList().contains("karate")).toList();

         System.out.println("studentList1");
         studentList1.forEach(student -> System.out.println(student.getFullName()));
         System.out.println("aaaaaaaaaaaabbbbbbbbbbb");

        //2. Students of standard>=8 who have Karate in sports and have brown/black belt
        List<Student> studentList2
           =  reportCard.getStudents().stream().filter(student -> student.getStandard()>=8 &&
                        student.getSports().stream().map(Sport::getSportName).toList().contains("karate") &&
                        Stream.of("brown-belt", "black-belt").anyMatch(allowedValues -> student.getSports().stream().map(Sport::getStatus).toList().contains(allowedValues)))
                        .toList();
        System.out.println("studentList2");
        studentList2.forEach(student -> System.out.println(student.getFullName()));
        System.out.println("cccccccccccccdddddddddddddddddd");

        //3. Student(s) of standard=10 who has the highest totalMarks
        List<Student> studentList3  =
                reportCard.getStudents().stream().filter(student -> student.getStandard()==10).max(Comparator.comparing(Student::getTotalMarks)).stream().toList();
        System.out.println("studentList3");
        studentList3.forEach(student -> System.out.println(student.getFullName() + student.getTotalMarks()));
        System.out.println("eeeeeeeeeeeeeeeefffffffffffffffffff");

        //4. Count of each student from each standard
        Map<Integer, Long> map4 = reportCard.getStudents().stream().collect(Collectors.groupingBy(Student::getStandard, Collectors.counting()));
        System.out.println("map4");
        System.out.println(map4);
        System.out.println("gggggggggghhhhhhhhhhhhhhhhhhhh");

        //5.Students in each standard
        //If map Value has >1 values which has to be transformed - use Collectors.groupingBy , Collectors.mapping
        Map<Integer, List<String>> map5 = reportCard.getStudents().stream()
                .collect(Collectors.groupingBy(Student::getStandard, Collectors.mapping(Student::getFullName, Collectors.toList())));
        //System.out.println("map5");
        //System.out.println(map5);

        //8. Student(s) with highest totalMarks from each standard

       /* reportCard.getStudents().stream()
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(Student::getStandard),
                        map -> {
                                map.forEach((key, value) -> {
                                    int maxPerStd = value.stream().map(Student::getTotalMarks).max(Integer::compareTo).orElse(0);
                                    map6.put(key, value.stream().filter(student -> student.getTotalMarks() == maxPerStd).toList());
                                });
                            return null;
                        }));
        System.out.println("map6");
        System.out.println(map6);*/
        Map<Integer, List<Student>> map6 =
        reportCard.getStudents().stream()
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(Student::getStandard), map -> {
                            map.forEach((key, value) -> {
                                map.put(key, value.stream().max(Comparator.comparing(Student::getTotalMarks)).stream().toList());
                            });
                            return map;
                        }));
        System.out.println("map6");
        System.out.println(map6);



        // Student with highest totalMarks from each standard
        Map<Integer, Student> map7 = reportCard.getStudents().stream()
                .collect(Collectors.toMap(Student::getStandard, Function.identity(), BinaryOperator.maxBy(Comparator.comparingInt(Student::getTotalMarks))));
        System.out.println("map7");
        System.out.println(map7);

        //highest total marks per standard
        Map<Integer, Integer> map16 = new HashMap<>();
        reportCard.getStudents().stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(Student::getStandard),
            map -> {
                    map.forEach( (key,value) -> {
                        map16.put(key, value.stream().map(Student::getTotalMarks).max(Integer::compareTo).orElse(0));
                    });
                return null;
            }));
        System.out.println("map16");
        System.out.println(map16);

        //10. Get count of students from each standard who has programming in hobby
        Map<Integer, Long> map10 = new HashMap<>();
                reportCard.getStudents().stream()
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(Student::getStandard),
                       map -> {
                            map.forEach( (key,value) -> {
                                map10.put(key, value.stream().filter(student -> student.getHobbies().contains("programming")).count());
                            });
                            return map10;
                       }));
        System.out.println("map10");
        map10.forEach((key, value) -> System.out.println(key + "-" + value));

        //Print all the house in each standard
        Map<Integer, String> map101 = reportCard.getStudents().stream()
                .collect(Collectors.groupingBy(Student::getStandard, Collectors.mapping(Student::getHouse, Collectors.joining(","))));
        System.out.println("map101");
        map101.forEach((key, value) -> System.out.println(key + "-" + value));

        //Print all the hobbies in each standard
        Map<Integer, String> map102 = reportCard.getStudents().stream()
                .collect(Collectors.groupingBy(Student::getStandard, Collectors.mapping(student -> student.getHobbies().stream().collect(Collectors.joining(",")),Collectors.joining(","))));
        System.out.println("map102");
        map102.forEach((key, value) -> System.out.println(key + "-" + value));

        //for each standard get the hobbies as Set
        Map<Integer, Set<String>> map104 = reportCard.getStudents().stream()
                .collect(Collectors.groupingBy(Student::getStandard,
                        Collectors.mapping(student -> student.getHobbies().stream().filter(StringUtils::isNotBlank).collect(Collectors.joining(",")), Collectors.toSet())));
        System.out.println("map104");
        map104.forEach((key, value) -> System.out.println(key + "-" + value));

        //11. Count of Students in each belt of Karate
        Map<String, Long> map11 =
        reportCard.getStudents().stream().flatMap(student -> student.getSports().stream()).filter(sport -> sport.getSportName().equalsIgnoreCase("Karate"))
                .collect(Collectors.groupingBy(Sport::getStatus, Collectors.counting()));
        System.out.println("map11");
        map11.forEach((key, value) -> System.out.println(key + "-" + value));

        //12. Students in each belt of Karate
        /*MultiValuedMap<String, String> map12 = new ArrayListValuedHashMap<>();
        reportCard.getStudents().forEach(student -> {
            if (student.getSports().stream().anyMatch(sport -> sport.getSportName().equalsIgnoreCase("Karate"))) {
                student.getSports().stream().filter(sport -> sport.getSportName().equalsIgnoreCase("Karate")).map(Sport::getStatus).forEach(status -> map12.put(status, student.getFullName()));
            }
        });*/
        Map<String, String> map105 = new HashMap<>();
        reportCard.getStudents().forEach(student -> {
            if(student.getSports().contains("Karate")){
               student.getSports().stream().filter(sport -> sport.getSportName().equalsIgnoreCase("Karate")).map(Sport::getStatus).forEach(status -> {
                   map105.put(status, Optional.ofNullable(map105.get(status)).orElse("").concat(student.getFullName()+","));
               });
            }
        });
        System.out.println("map105");
        System.out.println(map105);

        //7. Students who have at least 1 hobby and have Karate in sports
        List<String> studentList7 =
        reportCard.getStudents().stream().filter(student -> !(CollectionUtils.isEmpty(student.getHobbies())) &&
                                                           student.getSports().stream().anyMatch(sport -> sport.getSportName().equalsIgnoreCase("Karate")))
                                                            .map(Student::getFullName).toList();
        System.out.println("studentList7");
        studentList7.forEach(System.out::println);
        //Student 1, Student 3, Student 4, Student 5, Student 6, Student 7, Student 11

        //13. Students of each standard who has totalMarks>=360
        Map<Integer, List<Student>> map13 = new HashMap<>();
        reportCard.getStudents().stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(Student::getStandard),
                                                    map -> {
                                                            map.forEach((key,value) -> {
                                                                map13.put(key, value.stream().filter(student -> student.getTotalMarks()>=360).toList());
                                                            });
                                                            return map;
                                                    }));
        System.out.println("map13");
        map13.forEach((key, value) -> System.out.println(key + "-" + value));

        //9. For each Student, print the hobbies, if no hobby is present show NA in a map
        Map<String, String> map9 =
                reportCard.getStudents().stream().collect(Collectors.groupingBy(Student::getFullName,
                        Collectors.mapping(student -> CollectionUtils.isEmpty(student.getHobbies())? "NA" : student.getHobbies().stream().collect(Collectors.joining(",")), Collectors.joining(","))));
        System.out.println("map9");
        System.out.println(map9);

        //get grade of each student
        Map<String, String> map15 =
        reportCard.getStudents().stream().collect(Collectors.groupingBy(Student::getFullName,
                Collectors.mapping(student -> getGrade(student.getTotalMarks()), Collectors.joining())))
                .entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n) -> n, LinkedHashMap::new));
        System.out.println("map15");
        map15.forEach((key, value) -> System.out.println(key + "-" + value));

        //19. Get the marks in Maths for all students
        Map<String, Integer> map19 = new HashMap<>();

        reportCard.getStudents().stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(Student::getFullName),
                map -> {
                            map.forEach((key, value) -> {
                                map19.put(key, value.get(0).getMarks().getMaths());
                            });
                            return null;
                }));

            //reportCard.getStudents().stream().collect(Collectors.toMap(Student::getFullName, student -> student.getMarks().getMaths()));
        System.out.println("map19");
        System.out.println(map19);
        //{Student 11=98, Student 2=90, Student 1=90, Student 4=90, Student 3=90, Student 6=90, Student 5=90, Student 8=100, Student 7=90, Student 9=100}

        //Get the marks in Maths+Science for all students
        Map<String, Integer> map20 = new HashMap<>();
        reportCard.getStudents().stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(Student::getFullName),
            map -> {
                    map.forEach((fullName,studentList) -> {
                        map20.put(fullName, studentList.get(0).getMarks().getMaths()+studentList.get(0).getMarks().getScience());
                    });
                    return null;
            }));
        System.out.println("map20");
        System.out.println(map20);
        //{Student 11=197, Student 2=180, Student 1=170, Student 4=180, Student 3=170, Student 6=180, Student 5=180, Student 8=190, Student 7=180, Student 9=190}

        //6.Students sorted by standard ascending,totalMarks descending and name ascending
        List<Student> studentList6 =
        reportCard.getStudents().stream().sorted(Comparator.comparing(Student::getStandard).thenComparing(Student::getTotalMarks, Comparator.reverseOrder()).thenComparing(Student::getFullName)).toList();
        System.out.println("studentList6");
        studentList6.forEach(System.out::println);

        //16. Get hobbies of each student , sorted by name ascending
        Map<String, String> map18 =
                reportCard.getStudents().stream().collect(Collectors.groupingBy(Student::getFullName,
                        Collectors.mapping(student -> student.getHobbies().stream().collect(Collectors.joining(",")), Collectors.joining()))).entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n)->n, LinkedHashMap::new));
        System.out.println("map18");
        map18.forEach((key, value) -> System.out.println(key + "-" + value));

        //17. count of each hobby , sorted by count descending
        Map<String, Long> map17 =
        reportCard.getStudents().stream().flatMap(student -> student.getHobbies().stream()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))   .entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n)->n, LinkedHashMap::new));
        System.out.println("map17");
        map17.forEach((key, value) -> System.out.println(key + "-" + value));

        //18. Get the distinct hobbies of all students
        Set<String> set18 =
        reportCard.getStudents().stream().flatMap(student -> student.getHobbies().stream()).distinct().filter(StringUtils::isNotBlank).sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println("set18");
        System.out.println(set18);
        //[chess, gaming, music, photography, programming, puzzles, reading, science]

        //14. Get house with all its students
        Map<String, String> map14 =
        reportCard.getStudents().stream().collect(Collectors.groupingBy(Student::getHouse,
                        Collectors.mapping(Student::getFullName, Collectors.joining(","))));
        System.out.println("map14");
        map14.forEach((key, value) -> System.out.println(key + "-" + value));

        //20.List of students having duplicate sports
        List<String> studentHavingDuplicateSports = new ArrayList<>();
        reportCard.getStudents().forEach(student -> {

            if(student.getSports().stream().map(Sport::getSportName).toList().size() !=
                    student.getSports().stream().map(Sport::getSportName).distinct().toList().size()){
                studentHavingDuplicateSports.add(student.getFullName());
            }
        });
        System.out.println("studentHavingDuplicateSports");
        System.out.println(studentHavingDuplicateSports);

        //get students who has brown-belt in karate
        List<String> studentHavingKarateBrownBelt = new ArrayList<>();
        studentHavingKarateBrownBelt =
                reportCard.getStudents().stream().filter(student -> student.getSports().stream().map(Sport::getSportName).toList().contains("karate") &&
                                                            student.getSports().stream().map(Sport::getStatus).toList().contains("brown-belt"))
                                                 .map(Student::getFullName).toList();
        System.out.println("studentHavingKarateBrownBelt");
        System.out.println(studentHavingKarateBrownBelt); //[Student 3, Student 4, Student 5, Student 11]

        //get count of each hobby
        Map<String, Long> map21 =
        reportCard.getStudents().stream().flatMap(student -> student.getHobbies().stream()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("map21");
        System.out.println(map21);

        //check if all students have at least 1 hobby
        boolean allStudentsHaveHobbies =
        reportCard.getStudents().stream()
                        .map(student -> !CollectionUtils.isEmpty(student.getHobbies()))
                                .reduce(Boolean::logicalAnd)
                                        .orElse(false);
        System.out.println("allStudentsHaveHobbies :: " + allStudentsHaveHobbies);

        //get students by karate belt
        Function<Student, String> compositeKey = student -> student.getSports().stream()
                .filter(sport -> sport.getSportName().equalsIgnoreCase("karate"))
                .map(sport -> sport.getSportName().concat("-").concat(sport.getStatus())).collect(Collectors.joining());
        Map<String, List<String>> map22 =
        reportCard.getStudents().stream().collect(Collectors.groupingBy(compositeKey, Collectors.mapping(Student::getFullName, Collectors.toList())))
                        .entrySet().stream().filter(entry -> StringUtils.isNotBlank(entry.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n)->n, LinkedHashMap::new));
        System.out.println("map22");
        System.out.println(map22);
        //{karate-brown-belt=[Student 3, Student 4, Student 5, Student 11], karate-purple-beltkarate-green-belt=[Student 1], karate-blue-belt=[Student 6, Student 7]}

        //average marks in English for std=10
        Integer avgEnglishMarksInX =
                reportCard.getStudents().stream().filter(student -> student.getStandard()==10)
                .map(student -> student.getMarks().getEnglish()).collect(Collectors.averagingInt(v -> v)).intValue();
        System.out.println("avgEnglishMarksInX :: " + avgEnglishMarksInX); //92

        //all hobbies sorted
        List<String> allHobbiesSorted =
        reportCard.getStudents().stream().flatMap(student -> student.getHobbies().stream()).distinct().sorted().toList();
        System.out.println("allHobbiesSorted :: " + allHobbiesSorted);
        //[chess, gaming, music, photography, programming, puzzles, reading, science]

        //get Maths marks for Student 6
        Integer mathsMarks  = reportCard.getStudents().stream().filter(student -> student.getFullName().equalsIgnoreCase("Student 6")).map(Student::getMarks).mapToInt(Marks::getMaths).sum();
        System.out.println("mathsMarks :: " + mathsMarks); //90

        //get total maths marks of all students of std=10
        int totalMaths10 = reportCard.getStudents().stream().filter(student -> student.getStandard()==10).map(Student::getMarks).mapToInt(Marks::getMaths).sum();
        System.out.println("totalMaths10 :: " + totalMaths10); //90

        //get the total maths marks of each standard
        Map<Integer, Integer> map23 =
        reportCard.getStudents().stream().collect(Collectors.groupingBy(Student::getStandard,
                Collectors.reducing(0, student -> student.getMarks().getMaths(), Integer::sum)));
        System.out.println("map23");
        System.out.println(map23);

        //get total maths+science marks per standard
        Map<Integer, Integer> map24 =
        reportCard.getStudents().stream().collect(Collectors.groupingBy(Student::getStandard,
                Collectors.reducing(0, student -> student.getMarks().getMaths() + student.getMarks().getScience() ,Integer::sum)))
                .entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n)->n, LinkedHashMap::new));
        System.out.println("map24");
        System.out.println(map24);
        
        //highest marks in history
        int highestHistoryMarks =
                reportCard.getStudents().stream().map(Student::getMarks).map(Marks::getHistory).filter(Objects::nonNull)
                .max(Comparator.comparing(v ->v)).get();
        System.out.println("highestHistoryMarks :: " + highestHistoryMarks);

    }

    private static String getGrade(Integer totalMarks) {
        return Optional.ofNullable(totalMarks).map(marks -> marks >= 360 ? "A" : marks >= 320 && marks < 360 ? "B" : marks >= 280 && marks < 320 ? "C" : "D").orElseGet(() -> "NA");
    }
}
