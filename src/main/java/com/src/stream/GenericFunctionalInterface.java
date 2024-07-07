package com.src.stream;

@FunctionalInterface
public interface GenericFunctionalInterface<T, U, R>{
    R addValues(T t, U u);
}
