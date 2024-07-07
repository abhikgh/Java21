package com.src.stream;

enum Day{
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;
}
public class EnumEx2 {

    public static void main(String[] args) {
        Day day = Day.MONDAY;
        String ans = "";
        switch (day){
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> ans = "weekday";
            case SATURDAY, SUNDAY -> ans = "holiday";
            default -> ans = "none";
        }
        System.out.println(ans);
    }
}
