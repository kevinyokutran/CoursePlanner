package ca.cmpt213.courseplanner;

import ca.cmpt213.courseplanner.logic.CSVParser;
import ca.cmpt213.courseplanner.ui.CoursePlanner;


public class Main {
	public static void main(String[] args) {
		CSVParser.loadCoursesFromCSV();
		new CoursePlanner();
	}
}
