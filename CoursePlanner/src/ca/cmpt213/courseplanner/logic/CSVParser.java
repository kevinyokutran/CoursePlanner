package ca.cmpt213.courseplanner.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Handle loading data from the CSV
 * Source for RegEx used:
 * http://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
 */
public class CSVParser {

	private final static String SPLIT_REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
	private final static String directory = "data/course_data_2016.csv";

	public void loadCoursesFromCSV() {
		int count = 0;
		try{
			Scanner csv = new Scanner(new File(directory));
			String[] headers = csv.nextLine().split(SPLIT_REGEX, -1);
			while(csv.hasNextLine()){
				Course course = new Course();
				String data = csv.nextLine();
				String[] columns = data.split(SPLIT_REGEX, -1);
				for (int i=0; i<columns.length; i++) {
					course.setInfo(headers[i], columns[i]);
				}
				course.addToCourseMap(course);
				count++;
			}
			System.out.println("Successfully parsed " + count + " entries from CSV");
			csv.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

	/*  Used for testing */
	public static void main(String[] args) {
		CSVParser parser = new CSVParser();
		parser.loadCoursesFromCSV();
		Course.dumpModel();
	}

}
