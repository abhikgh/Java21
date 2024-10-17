package com.src.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DoubleArrayEx {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int marks = scanner.nextInt();
        int students = scanner.nextInt();
        int[][] marksOfStudents = new int[students][marks];

        for(int marksCount=0;marksCount<marks;marksCount++){
            for(int studentsCount=0;studentsCount<students;studentsCount++){
                marksOfStudents[studentsCount][marksCount] = scanner.nextInt();
            }
        }

        //get Grade of each student
        List<String> gradeList = Arrays.stream(marksOfStudents).map(DoubleArrayEx::getGradeOfStudent).toList();
        System.out.println(gradeList);
    }

    private static String getGradeOfStudent(int[] arr) {
        int total = Arrays.stream(arr).sum();
        if(total>=90)
            return "A";
        else if(total >=80 && total <90)
            return "B";
        else if(total >=70 && total < 80)
            return "C";
        else
            return "D";
    }
}
