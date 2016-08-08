package ca.cmpt213.courseplanner.ui;

public interface CourseListObserver {
	void stateChanged(String department, boolean isUndergradChecked, boolean isGradChecked);
}
