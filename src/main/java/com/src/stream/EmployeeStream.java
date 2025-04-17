package com.src.stream;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EmployeeStream {

	public static void main(String[] args) {
		
		List<Employee1> employeeList= getEmployeeList();
		
		//All employee whose age is greater than 20 and print the employee names
		List<String> resultEmployeeList = employeeList.stream().filter(employee -> employee.getAge()>20).map(Employee1::getName).toList();
		resultEmployeeList.forEach(System.out::println); //John,Mary,Gary 
		
		//Count number of employees with age 25
		long countEmpAgeMT25 = employeeList.stream().filter(employee -> employee.getAge() > 25).count();
		System.out.println(countEmpAgeMT25);  //2
		
		//Sort employees based on age
		List<Employee1> reverseAgeList = employeeList.stream().sorted(Comparator.comparing(Employee1::getAge, Comparator.reverseOrder())).toList();
		System.out.println("reverseAgeList :: " + reverseAgeList);
		// [Employee Name: Mary age: 31 Salary : 20000.0, Employee Nay : 15000.0, Employee Name: John age: 21 Salary : 10000.0, Employee Name: Martin ag
		
		//find the employee with name �Mary�
		List<Employee1> nameMaryList = employeeList.stream().filter(employee -> employee.getName().equals("Mary")).collect(Collectors.toList());
		nameMaryList.forEach(x -> System.out.println(x.toString())); //Employee Name: Mary age: 31
		
		//find maximum age of employee
		AtomicInteger maxAge2 = new AtomicInteger();
		employeeList.stream().max(Comparator.comparing(Employee1::getAge)).stream().findFirst().ifPresent(employee1 -> {
			maxAge2.set(employee1.getAge());
		});
		System.out.println("maxAge :: " + maxAge2.get()); //31

		//second highest salary
		int secondHighestSal = employeeList.stream().map(Employee1::getSalary).distinct().sorted(Comparator.reverseOrder()).skip(1).toList().getFirst();
		System.out.println("secondHighestSal :: " + secondHighestSal);

		//find maximum aged employee
		Employee1 maxAgedEmployee =
		employeeList.stream().max(Comparator.comparing(Employee1::getAge)).stream().findFirst().orElse(null);
		System.out.println("maxAgedEmployee :: " + maxAgedEmployee); //maxAgedEmployee :: Employee Name: Mary age: 31 Salary : 20000.0

		//find employee with highest salary
		Employee1 empMaxSalary =
				employeeList.stream().max(Comparator.comparing(Employee1::getSalary)).stream().findFirst().orElse(null);
		System.out.println("empMaxSalary :: " + empMaxSalary);

		//find employee with nth(5th) highest salary
		int n = 5-1;
		int fifthHighestSalary = employeeList.stream().map(Employee1::getSalary).distinct().sorted(Comparator.reverseOrder()).toList().get(n);
		System.out.println("fifthHighestSalary :: " + fifthHighestSalary);

		//Join all employee names
		String allEmployeeNamesStr = employeeList.stream().map(Employee1::getName).collect(Collectors.joining(","));
		System.out.println(allEmployeeNamesStr);  //John,Martin,Mary,Stephan,Gary
		
		//Group employee by employee name
		employeeList.add(new Employee1("John",31,22000));
		employeeList.add(new Employee1("Stephan",38,44000));
		Map<String, Long> map = employeeList.stream().collect(Collectors.groupingBy(Employee1::getName,Collectors.counting()));
		map.forEach((key,value) -> System.out.println(key + "-" + value) );
		/*
		 * Gary-1 Stephan-2 John-2 Martin-1 Mary-1
		 */
														
		
		//the first employee with the salary greater than 10000 is returned.
		//If no such employee exists, then null is returned.
		Employee1 empSalaryAbove10K = employeeList
									.stream()
									.filter(e->e.getSalary()>10000)
									.findFirst()
									.orElse(null);
		System.out.println(empSalaryAbove10K);  //Employee Name: Mary age: 31 Salary : 20000.0

		//check if all employees are above age 20
		boolean allEmpsAreAbove20 = employeeList.stream()
									.map(employee -> employee.getAge()>20)
									.reduce(Boolean::logicalAnd)
									.orElse(false);
		System.out.println("allEmpsAreAbove20 :: " + allEmpsAreAbove20);

		List<String> designations = List.of("ADMIN","CODER","TESTER");
		Lists.partition(employeeList,2).stream().map(partitionedList -> {
			partitionedList.listIterator().forEachRemaining(employee -> employee.setName(designations.get((int)(Math.random()*designations.size()))));
			return partitionedList;
		}).flatMap(partitionedList -> partitionedList.stream().filter(employee -> employee.getName().equals("ADMIN"))).toList().forEach(System.out::println);

		
	
	}
	
	
	public static List<Employee1> getEmployeeList()
	{
		List<Employee1> employeeList=new LinkedList<>();
 
		Employee1 e1=new Employee1("John",21,10000);
		Employee1 e2=new Employee1("Martin",19,8000);
		Employee1 e3=new Employee1("Mary",31,20000);
		Employee1 e4=new Employee1("Stephan",18, 6000);
		Employee1 e5=new Employee1("Gary",26, 15000);
		Employee1 e6=new Employee1("Kara",26, 15000);
 
		employeeList.add(e1);
		employeeList.add(e2);
		employeeList.add(e3);
		employeeList.add(e4);
		employeeList.add(e5);
		employeeList.add(e6);
 
		return employeeList;
	}
}
