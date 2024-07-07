package com.src.stream;

import com.src.model.Student;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student student1, Student student2) {
        if(student1.getTotalMarks() > student2.getTotalMarks()){
            return -1; // -1 for descending, 1 for ascending
        } else if(student1.getTotalMarks() < student2.getTotalMarks()){
            return 1;
        } else {
            if (student1.getFullName().compareTo(student2.getFullName()) > 0) {
                return 1; //name ascending
            } else if (student1.getFullName().compareTo(student2.getFullName()) < 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
