package ca.cmpt213.courseplanner.logic;

import java.util.*;

/* Contains all information regarding courses.
 * The course information is initially loaded from a CSV file
 * */
public class Course {

	public static final HashMap<String, HashMap<String, ArrayList<Course>>> ALL_DEPTS = new HashMap<>();
	private static final int BASE_YEAR = 2000;
	private static final int SPRING = 1;
	private static final int SUMMER = 4;
	private static final int FALL = 7;
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
		if (!Course.ALL_DEPTS.containsKey(subject)) {
			Course.ALL_DEPTS.put(course.getSubject(), new HashMap<>());
		}
		HashMap<String, ArrayList<Course>> deptList = Course.ALL_DEPTS.get(subject);
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
			HashMap<String, ArrayList<Course>> deptMap = Course.ALL_DEPTS.get(dept);
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

	/* -----------------------
	 *  Getters
	 * --------------------- */
	public static ArrayList<Course> getCourseOfferings(String dept, String catalogNum) {
		return ALL_DEPTS.get(dept).get(catalogNum);
	}

	public static ArrayList<String> getCoursesInDepartment(String department, boolean undergrad, boolean grad) {
		ArrayList<String> courses = new ArrayList<>();
		try {
			SortedSet<String> sortedCourses = new TreeSet<>(ALL_DEPTS.get(department).keySet());
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
		SortedSet<String> sortedDepts = new TreeSet<>(ALL_DEPTS.keySet());
		for (String dept : sortedDepts) {
			deptList.add(dept);
		}
		return deptList;
	}

//	public static int[] getYearRangeOfCourseOfferings(String department, String courseNumber) {
//		int[] range = new int[2];
//		range[0] = Integer.MAX_VALUE;
//		range[1] = Integer.MIN_VALUE;
//		for (Course course : getCourseOfferings(department, courseNumber)) {
//			String semester = course.getSemester();
//			int year = Integer.parseInt(semester.substring(1, semester.length()-1));
//			if (year < range[0]) {
//				range[0] = year;
//			}
//			else if (year > range[1]) {
//				range[1] = year;
//			}
//		}
//		range[0] += BASE_YEAR;
//		range[1] += BASE_YEAR;
//		return range;
//	}

	public static int[] getYearRangeOfAllCourses() {
		int[] range = new int[2];
		range[0] = Integer.MAX_VALUE;
		range[1] = Integer.MIN_VALUE;
		for (String dept : getAlphabeticalDepartmentList()) {
			HashMap<String, ArrayList<Course>> deptMap = Course.ALL_DEPTS.get(dept);
			for (String catalogNum : deptMap.keySet()) {
				ArrayList<Course> courseList = getCourseOfferings(dept, catalogNum);
				System.out.println(dept + " " + catalogNum);
				for (Course course : courseList) {
					String semester = course.getSemester();
					int year = Integer.parseInt(semester.substring(1, semester.length()-1));
					if (year < range[0]) {
						range[0] = year;
					}
					else if (year > range[1]) {
						range[1] = year;
					}
				}
			}
		}
		range[0] += BASE_YEAR;
		range[1] += BASE_YEAR;
		return range;
	}

	public String getSeasonOfCourse() {
		String semester = this.getSemester();
		int seasonCode = Integer.parseInt(semester.substring(semester.length()-1));
		if (seasonCode == SPRING) {
			return "Spring";
		}
		else if (seasonCode == SUMMER) {
			return "Summer";
		}
		else if (seasonCode == FALL) {
			return "Fall";
		}
		return "";
	}

	public int getYearOfCourse() {
		String semester = this.getSemester();
		String year = semester.substring(1, semester.length()-1);
		return Integer.parseInt(year) + BASE_YEAR;

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
			instructorString = instructorString + instructor + ", ";
		}
		return instructorString.substring(0, instructorString.length()-2);
	}

	public String getComponentCode() {
		return this.componentCode;
	}

	/* -----------------------
	 *  Setters
	 * --------------------- */
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
