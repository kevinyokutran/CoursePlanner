package ca.cmpt213.courseplanner.logic;

import java.util.ArrayList;

public class CourseInformation {

	protected Boolean isUndergraduateCourse;
	protected Boolean isGraduateCourse;
	private static ArrayList<String> departmentList;

	private CSVParser parser;

	public CourseInformation() {
	}

	public static void dump() {
		for (String dept : Course.allDepts.keySet()) {
			System.out.println(dept);
		}
	}



	public static void main(String[] args) {
		dump();
	}
}
