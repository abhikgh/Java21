package com.src.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//get all employees from all departments in a Company using Dependency Inversion Principle
interface Company {
    List<DEmployee> getAllEmployees();
}

interface CDepartment {
    List<DEmployee> getEmployees();
}

@AllArgsConstructor
@Data
class DEmployee {
    private int employeeId;
    private String employeeName;
}

class Admin implements CDepartment {
    @Override
    public List<DEmployee> getEmployees() {
        return List.of(new DEmployee(1, "Aaa"), new DEmployee(2, "Bbb"));
    }
}

class IT implements CDepartment {
    @Override
    public List<DEmployee> getEmployees() {
        return List.of(new DEmployee(3, "Ccc"), new DEmployee(4, "Ddd"));
    }
}

class IBM implements Company{

    private List<CDepartment> departmentList;

    public IBM(List<CDepartment> departmentList){
        this.departmentList = departmentList;
    }

    private List<DEmployee> employees = new ArrayList<>();

    @Override
    public List<DEmployee> getAllEmployees() {
        for(CDepartment cDepartment : departmentList){
            employees.addAll(cDepartment.getEmployees());
        }
        return employees;
    }
}

public class DependencyInversionPEx {
    public static void main(String[] args) {
        List<DEmployee> employees = new ArrayList<>();

        CDepartment admin = new Admin();
        employees.addAll(admin.getEmployees());
        CDepartment it = new IT();
        employees.addAll(it.getEmployees());

        Company ibm = new IBM(List.of(admin, it));
        ibm.getAllEmployees().forEach(System.out::println);
    }
}
