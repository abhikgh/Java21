package com.src.stream;

public record RegularEmp(String name, String department, double salary) implements Employee{
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDepartment() {
        return department;
    }
}
