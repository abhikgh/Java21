package com.src.stream;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static com.src.stream.CollectorEx.getItemsWithPrice;

public class MaxEx {

    public static void main(String[] args) {

        //.max(Integer::compareTo), .max(Double::compareTo), .max(BigDecimal::compareTo)

        List<Integer> integerList = List.of(23, 34, 56,231, 36);
        Integer highestInt = integerList.stream().max(Integer::compareTo).get();
        System.out.println("highestInt :: "  +highestInt);  //231

        List<Number> numberList = List.of(23.09f, 23L, 123.34d);
        Number highestNumber = numberList.stream().map(Number::doubleValue).max(Double::compareTo).get();
        System.out.println("highestNumber :: " + highestNumber);  //123.34

        List<Item> itemList = getItemsWithPrice();
        BigDecimal highestPrice = itemList.stream().map(Item::getPrice).max(BigDecimal::compareTo).orElse(null);
        System.out.println("highestPrice :: " + highestPrice);

        //max(Comparator.comparing(Item::getPrice))
        String itemNoWithHighestPrice =
                itemList.stream().max(Comparator.comparing(Item::getPrice)).get().getItemNo();
        System.out.println("itemNoWithHighestPrice :: " + itemNoWithHighestPrice); //45646
    }
}
