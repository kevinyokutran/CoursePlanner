package ca.cmpt213.courseplanner.ui;

/**
 * Created by Kevin on 8/7/2016.
 */
public interface CourseListObserver {
	void stateChanged(String department, String courseNumber);
}
