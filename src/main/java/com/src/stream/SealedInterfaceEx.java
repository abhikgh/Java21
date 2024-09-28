package com.src.stream;

public class SealedInterfaceEx {

    public static void main(String[] args) {
        Employee regularEmp = new RegularEmp("Reg","ADM", 233.40);
        Employee freeLancer = new Freelancer("Reg","ADM", 12.40);
        processEmployee(regularEmp);
    }

    private static void processEmployee(Employee employee) {
        if(employee instanceof RegularEmp(String name, String department, double salary)){
            System.out.println("RegularEmp :: " + name + "," + department + "," + salary);
        } else if(employee instanceof Freelancer(String name, String department, double hourlyRate)){
            System.out.println("Freelancer :: " + name + "," + department + "," + hourlyRate);
            //RegularEmp :: Reg,ADM,233.4
        }
    }
}
