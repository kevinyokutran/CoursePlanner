package ca.cmpt213.courseplanner.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* Contains all information regarding courses.
 * The course information is initially loaded from a CSV file
 * */
public class Course {

	public static HashMap<String,ArrayList<Course>> courses = new HashMap<>();

	private String semester;
	private String subject;
	private String catalogNumber;
	private String location;
	private String enrollmentCapacity;
	private String enrollmentTotal;
	private ArrayList<String> instructors;
	private String componentCode;


	public Course() {
		this.instructors = new ArrayList<>();
	}

	public static void addToCourseMap(Course course) {
		if (!Course.courses.containsKey(course.getSubject())) {
			Course.courses.put(course.getSubject(), new ArrayList<>());
		}
		else {
			ArrayList<Course> courseList = Course.courses.get(course.getSubject());
			courseList.add(course);
		}
	}

	// Getters
	public String getSubject() {
		return this.subject;
	}
	public String getCatalogNumber() {
		return this.catalogNumber;
	}

	// Setters
	public void setInfo(String header, String columnData) {
		switch(header) {
			case "SEMESTER":
				setSemester(columnData);
				break;
			case "SUBJECT":
				setSubject(columnData);
				break;
			case "CATALOGNUMBER":
				setCatalogNumber(columnData);
				break;
			case "LOCATION":
				setLocation(columnData);
				break;
			case "ENROLLMENTCAPACITY":
				setEnrollmentCapacity(columnData);
				break;
			case "ENROLLMENTTOTAL":
				setEnrollmentTotal(columnData);
			case "INSTRUCTORS":
				setInstructors(columnData);
				break;
			case "COMPONENTCODE":
				setComponentCode(columnData);
				break;
		}
	}
	private void setSemester(String semester) {
		this.semester = semester;
	}
	private void setSubject(String subject) {
		this.subject = subject;
	}
	private void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}
	private void setLocation(String location) {
		this.location = location;
	}
	private void setEnrollmentCapacity(String capacity) {
		this.enrollmentCapacity = capacity;
	}
	private void setEnrollmentTotal(String total) {
		this.enrollmentTotal = total;
	}
	private void setInstructors(String instructors) {
		for (String instructor : instructors.split(",")) {
			instructor = instructor.replace("\"", "");
			instructor = instructor.trim();
			this.instructors.add(instructor);
		}
	}
	private void setComponentCode(String code) {
		this.componentCode = code;
	}

}
