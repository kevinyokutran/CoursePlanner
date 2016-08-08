package ca.cmpt213.courseplanner.logic;

import java.util.*;

/* Contains all information regarding courses.
 * The course information is initially loaded from a CSV file
 * */
public class Course {

	public static final HashMap<String, HashMap<String, ArrayList<Course>>> allDepts = new HashMap<>();
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
		String subject = course.getSubject();
		String catalogNum = course.getCatalogNumber();
		// Initialize new HashMap for department if it doesn't exist
		if (!Course.allDepts.containsKey(subject)) {
			Course.allDepts.put(course.getSubject(), new HashMap<>());
		}
		HashMap<String, ArrayList<Course>> deptList = Course.allDepts.get(subject);
		// Initialize new ArrayList for catalog number if it doesn't exist
		if (!deptList.containsKey(catalogNum)) {
			deptList.put(catalogNum, new ArrayList<>());
		}
		// Add the course
		ArrayList<Course> courseList = getCourseOfferings(subject, catalogNum);
		courseList.add(course);
	}

	public static void dumpModel() {
		for (String dept : getAlphabeticalDepartmentList()) {
			HashMap<String, ArrayList<Course>> deptMap = Course.allDepts.get(dept);
			for (String catalogNum : deptMap.keySet()) {
				ArrayList<Course> courseList = getCourseOfferings(dept, catalogNum);
				System.out.println(dept + " " + catalogNum);
				for (Course course : courseList) {
					System.out.println(String.format(
							"    %s in %s by %s", course.getSemester(), course.getLocation(), course.getInstructorsAsString()
					));
					System.out.println(String.format(
							"        Type=%s, %s/%s", course.getComponentCode(), course.getEnrollmentCapacity(), course.getEnrollmentTotal()
					));
				}
				System.out.println();
			}
		}
	}

	// Getters
	public static ArrayList<Course> getCourseOfferings(String dept, String catalogNum) {
		return allDepts.get(dept).get(catalogNum);
	}

	public static ArrayList<String> getCoursesInDepartment(String department, boolean undergrad, boolean grad) {
		ArrayList<String> courses = new ArrayList<>();
		try {
			SortedSet<String> sortedCourses = new TreeSet<>(allDepts.get(department).keySet());
			for (String course : sortedCourses) {

				String filterCourseOfLetters = filterCourseOfLetters(course);
				System.out.println(filterCourseOfLetters);

				if (Integer.parseInt(filterCourseOfLetters) < 500 && undergrad) {
					courses.add(department + " " + course);
				} else if (Integer.parseInt(filterCourseOfLetters) >= 500 && grad) {
					courses.add(department + " " + course);
				}
			}
		} catch (Exception E) {
		}
		return courses;
	}

	private static String filterCourseOfLetters(String course) {
		course = course.replaceAll("[^\\d.]", "");
		return course;
	}

	public static ArrayList<String> getAlphabeticalDepartmentList() {
		ArrayList<String> deptList = new ArrayList<>();
		SortedSet<String> sortedDepts = new TreeSet<>(allDepts.keySet());
		for (String dept : sortedDepts) {
			deptList.add(dept);
		}
		return deptList;
	}

	public String getSemester() {
		return this.semester;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getCatalogNumber() {
		return this.catalogNumber;
	}

	public String getLocation() {
		return this.location;
	}

	public String getEnrollmentCapacity() {
		return this.enrollmentCapacity;
	}

	public String getEnrollmentTotal() {
		return this.enrollmentTotal;
	}

	public String getInstructorsAsString() {
		String instructorString = "";
		for (String instructor : this.instructors) {
			instructorString = instructorString + instructor + " ";
		}
		return instructorString;
	}

	public String getComponentCode() {
		return this.componentCode;
	}

	// Setters
	public void setInfo(String header, String columnData) {
		switch (header) {
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
			case "ENROLMENTCAPACITY":
				setEnrollmentCapacity(columnData);
				break;
			case "ENROLMENTTOTAL":
				setEnrollmentTotal(columnData);
				break;
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
