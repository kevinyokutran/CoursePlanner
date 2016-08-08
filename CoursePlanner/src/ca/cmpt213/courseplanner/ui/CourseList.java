package ca.cmpt213.courseplanner.ui;

import ca.cmpt213.courseplanner.logic.Course;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

public class CourseList extends BasePanel {

	private static final String TITLE = "Course List";
	private JPanel panel;
	private String department = "CMPT";
	private JList<String> courseList;
	private JScrollPane scrollPane;

	public CourseList(CoursePlanner model) {
		super(model);
		registerAsObserver();
	}

	@Override
	protected JLabel setTitle() {
		JLabel title = new JLabel(TITLE);
		title.setForeground(Color.BLUE);
		return title;
	}

	@Override
	protected JPanel setPanel() {
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,
				Color.BLACK, Color.GRAY));

		return panel;
	}

	private void updateList(String department, boolean isUndergradChecked, boolean isGradChecked) {
		this.department = department;
		System.out.println(this.department);

		panel.removeAll();

		courseList = new JList(Course.getCoursesInDepartment(department, isUndergradChecked, isGradChecked).toArray());
		courseList.setLayoutOrientation(courseList.HORIZONTAL_WRAP);
		courseList.setVisibleRowCount(-1);
//		courseList.setPreferredSize(new Dimension(0, panel.getHeight()));
		courseList.addMouseListener(updateCourseOfferings());

		scrollPane = new JScrollPane(courseList);
		scrollPane.setPreferredSize(new Dimension(panel.getWidth() - 10, panel.getHeight() - 10));

		panel.add(scrollPane);

//		for (String coursestring : Course.getCoursesInDepartment(this.department, isUndergradChecked, isGradChecked))) {
//			System.out.println(coursestring);
//		}

		panel.revalidate();

	}

	private MouseListener updateCourseOfferings() {
		MouseListener courseListListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					String selectedItem = courseList.getSelectedValue();
					String department = selectedItem.split(" ")[0];
					String courseNumber = selectedItem.split(" ")[1];
					notifyCourseListObservers(department, courseNumber);
			}
		};
		return courseListListener;
	}

	/* -------------------
	 * Observer Methods
	 * ------------------- */
	private void registerAsObserver() {
		model.addCourseListFilterObserver( (department, isUndergradChecked, isGradChecked) ->
				updateList(department, isUndergradChecked, isGradChecked));
	}

	private void notifyCourseListObservers(String department, String courseNumber) {
		for (CourseListObserver observer : CoursePlanner.getCourseListObservers()) {
			observer.stateChanged(department, courseNumber);
		}
		revalidate();
	}
}
