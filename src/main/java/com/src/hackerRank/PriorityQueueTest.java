package com.src.hackerRank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Student0{
	private Integer id;
	private String name;
	private Double cgpa;
}

class Student0Comparator implements Comparator<Student0> {

	@Override
	public int compare(Student0 studentEx1, Student0 studentEx2) {
		if (studentEx1.getCgpa().compareTo(studentEx2.getCgpa()) > 0) {
			return -1;
		} 
		if (studentEx1.getCgpa().compareTo(studentEx2.getCgpa()) < 0) {
			return 1;
		} 
		if (studentEx1.getName().compareTo(studentEx2.getName()) > 0) {
			return 1;
		}
		if (studentEx1.getName().compareTo(studentEx2.getName()) < 0) {
			return -1;
		} 
		if (studentEx1.getId().compareTo(studentEx2.getId()) > 0) {
			return 1;
		} 
		if (studentEx1.getId().compareTo(studentEx2.getId()) < 0) {
			return -1;
		} 
		return 0;
	}

}

class Priorities{
	public List<Student0> getStudents(List<String> events){
		int initialCapacity = events.size();
		List<Student0> students = new LinkedList<Student0>();
	    PriorityQueue<Student0> priorityQueue = new PriorityQueue<Student0>(initialCapacity, new Student0Comparator());
		events.forEach(event -> {
			StringTokenizer st = new StringTokenizer(event);
			String token = st.nextToken();
			if(token.equalsIgnoreCase("ENTER")) {
				Student0 student = new Student0();
				student.setName(st.nextToken());
				student.setCgpa(Double.parseDouble(st.nextToken()));
				student.setId(Integer.parseInt(st.nextToken()));
				priorityQueue.add(student);
			}else if(token.equalsIgnoreCase("SERVED")) {
				priorityQueue.poll();
			}
		});
		while(!priorityQueue.isEmpty()) {
			students.add(priorityQueue.poll());
		}
		return students;
	}
}

public class PriorityQueueTest {
	private final static Scanner scan = new Scanner(System.in);
	private final static Priorities priorities = new Priorities();

	public static void main(String[] args) {
		int totalEvents = Integer.parseInt(scan.nextLine());
		List<String> events = new ArrayList<>();

		while (totalEvents-- != 0) {
			String event = scan.nextLine();
			events.add(event);
		}

		List<Student0> students = priorities.getStudents(events);

		if (students.isEmpty()) {
			System.out.println("EMPTY");
		} else {
			for (Student0 st : students) {
				System.out.println(st.getName());
			}
		}
	}
}