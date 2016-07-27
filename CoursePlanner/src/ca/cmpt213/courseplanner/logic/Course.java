package ca.cmpt213.courseplanner.logic;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/* Contains all information regarding courses.
 * The course information is initially loaded from a CSV file */
public class Course {

	public Course() {

	}

	public static void loadFromCSV(String directory) {
		try{
			Scanner csv = new Scanner(new File(directory));
			while(csv.hasNextLine()){
				String data = csv.nextLine();
				String[] columns = data.split(",");
				for (int i=0; i<columns.length; i++) {
					System.out.print(columns[i]);
				}
				System.out.println();
			}
			csv.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String directory = "data/course_data_2016.csv";
		loadFromCSV(directory);
	}

}
