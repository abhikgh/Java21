package com.src.hackerRank;

import java.util.List;

public class StudentClass<Type> {

    public void getStudents(String studentData, String query) {
        String[] arr = query.split("\\,");
        int queryType = Integer.parseInt(arr[0]);

        if (queryType == 1) {
            StudentList<String> studentList = new StudentList<>();
            String[] names = studentData.split(" ");
            for (int i = 0; i < names.length; i++) {
                studentList.addElement(names[i]);
            }
            List<String> namesList = studentList.beginWith(arr[1]);
            for (String name : namesList) {
                System.out.println(name);
            }
            System.out.println();
        } else if (queryType == 2) {
            StudentList<String> studentList = new StudentList<>();
            String[] names = studentData.split(" ");
            for (int i = 0; i < names.length; i++) {
                studentList.addElement(names[i]);
            }
            String[] groups = arr[1].split(" ");
            List<String> namesList = studentList.bloodGroupOf(groups, "B+");
            for (String name : namesList) {
                System.out.println(name);
            }
            System.out.println();
        } else if (queryType == 3) {
            StudentList<Double> studentList = new StudentList<>();
            String[] scores = studentData.split(" ");
            for (String score : scores) {
                studentList.addElement(Double.parseDouble(score));
            }
            int count = studentList.thresholdScore(Integer.parseInt(arr[1]));
            System.out.println(count);
        } else if (queryType == 4 || queryType == 5) {
            ScoreList<Double> scoreList = new ScoreList<>();
            String[] scores = studentData.split(" ");
            for (String score : scores) {
                scoreList.addElement(Double.parseDouble(score));
            }
            double average = scoreList.average();
            System.out.println(average);
        }
    }
}
