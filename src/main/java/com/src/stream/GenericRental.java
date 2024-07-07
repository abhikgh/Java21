package com.src.stream;

import java.util.List;

public class GenericRental<T>{

    private List<T> tList;

    public GenericRental(List<T> tList){
        this.tList = tList;
    }

    public T getFirst(){
        return tList.get(0);
    }
}
