package com.src.hackerRank;

import java.util.*;

abstract class Student
{
    public abstract String result(String markOfStudent);
}

class Aided extends Student
{
    private double returnGradePoints(int mark)
    {
        double gradePoints;

        if(mark>=75 && mark<=100)
        {
            gradePoints = 9.0 + ((mark-75)*(10.0-9.0))/(100-25);
        }
        else if(mark>=60 && mark<=74)
        {
            gradePoints = 8.0 + ((mark-60)*(8.9-8.0))/(74-60);
        }
        else if(mark>=50 && mark<=59)
        {
            gradePoints = 7.0 + ((mark-50)*(7.9-7.0))/(59-50);
        }
        else if(mark>=40 && mark<=49)
        {
            gradePoints = 6.0 + ((mark-40)*(6.9-6.0))/(49-40);
        }
        else
        {
            gradePoints = 0;
        }

        return gradePoints;
    }

    public String result(String markOfStudent)
    {
        int totalCredits = 0;
        double totalSum = 0;

        String[] variousmark = markOfStudent.split("|",3);
        String subjectmark = variousmark[0];
        String NCCmark = variousmark[1];
        String sportsmark = variousmark[2];

        String[] variousSubjectsmark = subjectmark.split(",",0);

        for(String individualSubject : variousSubjectsmark)
        {
            System.out.println("subject  "+individualSubject);
            String[] temp = individualSubject.split(" ",2);
            int mark = Integer.parseInt(temp[0]);
            int credits = Integer.parseInt(temp[1]);

            double gradePoints = returnGradePoints(mark);

            totalSum += gradePoints*credits;
            totalCredits += credits;
        }

        if(NCCmark.charAt(0) =='1')
        {
            String[] temp = NCCmark.split(",",3);

            int mark = Integer.parseInt(temp[1]);
            int credits = Integer.parseInt(temp[2]);

            double gradePoints = returnGradePoints(mark);

            totalSum += gradePoints*credits;
            totalCredits += credits;
        }

        if(sportsmark.charAt(0)=='1')
        {
            String[] temp = sportsmark.split(",",3);

            int mark = Integer.parseInt(temp[1]);
            int credits = Integer.parseInt(temp[2]);

            double gradePoints = returnGradePoints(mark);

            totalSum += gradePoints*credits;
            totalCredits += credits;
        }

        double finalGrades = totalSum/totalCredits;

        String answer = String.format("%.2f", finalGrades);

        return answer;
    }
}

class SelfFinance extends Student
{
    private double returnGradePoints(int mark)
    {
        double gradePoints;

        if(mark>=75 && mark<=100)
        {
            gradePoints = 9.0 + ((mark-75)*(10.0-9.0))/(100-25);
        }
        else if(mark>=60 && mark<=74)
        {
            gradePoints = 8.0 + ((mark-60)*(8.9-8.0))/(74-60);
        }
        else if(mark>=50 && mark<=59)
        {
            gradePoints = 7.0 + ((mark-50)*(7.9-7.0))/(59-50);
        }
        else if(mark>=40 && mark<=49)
        {
            gradePoints = 6.0 + ((mark-40)*(6.9-6.0))/(49-40);
        }
        else
        {
            gradePoints = 0;
        }

        return gradePoints;
    }

    public String result(String markOfStudent)
    {
        int totalCredits = 0;
        double totalSum = 0;

        String[] variousmark = markOfStudent.split("|",3);
        String subjectmark = variousmark[0];
        String sportsmark = variousmark[1];

        String[] variousSubjectsmark = subjectmark.split(",",0);

        for(String individualSubject : variousSubjectsmark)
        {
            String[] temp = individualSubject.split(" ",2);
            int mark = Integer.parseInt(temp[0]);
            int credits = Integer.parseInt(temp[1]);

            double gradePoints = returnGradePoints(mark);

            totalSum += gradePoints*credits;
            totalCredits += credits;
        }

        if(sportsmark.charAt(0) =='1')
        {
            String[] temp = sportsmark.split(",",3);

            int mark = Integer.parseInt(temp[1]);
            int credits = Integer.parseInt(temp[2]);

            double gradePoints = returnGradePoints(mark);

            totalSum += gradePoints*credits;
            totalCredits += credits;
        }

        double finalGrades = totalSum/totalCredits;

        String answer = String.format("%.2f", finalGrades);

        return answer;
    }
}

public class AbstractEx2
{
    public static void main(String[] args) {

        Aided Naman = new Aided();
        String markOfNaman = "100 5,100 5,100 5|1,100,5|0,100,5";

        SelfFinance Kaushal = new SelfFinance();
        String markOfKaushal = "100 5,100 5,100 5|1,100,5";

        System.out.println(Naman.result(markOfNaman));
        System.out.println(Kaushal.result(markOfKaushal));

    }
}