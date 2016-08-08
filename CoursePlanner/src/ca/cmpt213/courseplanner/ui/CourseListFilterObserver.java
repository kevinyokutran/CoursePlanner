package ca.cmpt213.courseplanner.ui;

public interface CourseListFilterObserver {
	void stateChanged(String department, boolean isUndergradChecked, boolean isGradChecked);
}
