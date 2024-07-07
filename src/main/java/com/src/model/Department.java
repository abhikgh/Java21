package com.src.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Department implements Cloneable{

    private int departmentId;
    private String departmentName;
    private String designation;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
