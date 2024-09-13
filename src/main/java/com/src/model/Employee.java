package com.src.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Employee implements Cloneable{

    private int employeeId;
    private String employeeName;
    private Department department;
    private BigDecimal salary;


    //clone() has protected access in java.lang.Object
    @Override
    public Object clone() throws CloneNotSupportedException {
        //Shallow clone
        //Employee employee = (Employee) super.clone();
        //return employee;

        //Deep clone = Shallow clone + set()
        Employee employee = (Employee) super.clone();
        employee.setDepartment((Department) employee.getDepartment().clone());
        return employee;
    }
}
