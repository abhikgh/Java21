package com.src.hackerRank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreList<Type extends Number> {
    List<Type> list = new ArrayList<>();

    public void addElement(Type type) {
        list.add(type);
    }

    public double average() {
        double total = list.stream().collect(Collectors.summingDouble(Number::doubleValue));
        return total / list.size();
    }
}