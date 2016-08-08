package ca.cmpt213.courseplanner.ui;

import ca.cmpt213.courseplanner.logic.Course;

/**
 * Created by Kevin on 8/8/2016.
 */
public interface CourseOfferingObserver {
	void stateChanged(Course course);
}
