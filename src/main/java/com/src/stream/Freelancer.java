package com.src.stream;

public record Freelancer(String name, String department, double hourlyRate) implements Employee{
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDepartment() {
        return department;
    }
}
