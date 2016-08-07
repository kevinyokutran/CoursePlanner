package ca.cmpt213.courseplanner.ui;

import ca.cmpt213.courseplanner.logic.Course;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Observable;

public class CourseList extends BasePanel {

	private static final String TITLE = "Course List";
	private JPanel panel;
	private String department = "CMPT";

	public CourseList (CoursePlanner model) {
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
//		panel.add(new JList(
//				Course.getCoursesInDepartment(this.department).toArray()
//		));

		return panel;
	}

	private void updateList(String department) {
		System.out.println(this.department);
	}

	/* -------------------
	 * Observer Methods
	 * ------------------- */
	private void registerAsObserver() {
		model.addObserver( department -> updateList(department));
	}
}
