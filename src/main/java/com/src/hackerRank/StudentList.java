package com.src.hackerRank;

import java.util.ArrayList;
import java.util.List;

public class StudentList<Type> {
    List<Type> list = new ArrayList<>();

    public void addElement(Type type) {
        list.add(type);
    }

    public List<String> beginWith(String data) {
        return list.stream().map(Object::toString).filter(value -> value.toUpperCase().startsWith(data.toUpperCase())).toList();
    }

    public List<String> bloodGroupOf(String[] bloodGroups, String group) {
        List<String> students = new ArrayList<>();
        for (int i = 0; i < bloodGroups.length; i++) {
            if (bloodGroups[i].equalsIgnoreCase(group)) {
                students.add(students.get(i));
            }
        }

        return students;
    }

    public int thresholdScore(int score) {
        return (int) list.stream().map(value -> Double.valueOf(String.valueOf(value))).filter(value -> value >= score).count();
    }
}