package com.src.stream;

public sealed interface Employee permits RegularEmp, Freelancer{

    String getName();
    String getDepartment();
}
