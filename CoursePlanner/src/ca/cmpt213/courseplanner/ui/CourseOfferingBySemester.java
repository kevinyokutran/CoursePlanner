package ca.cmpt213.courseplanner.ui;

import ca.cmpt213.courseplanner.logic.Course;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CourseOfferingBySemester extends BasePanel{

	private static final String TITLE = "Course Offerings by Semester";

	private JPanel panel;

	public CourseOfferingBySemester(CoursePlanner model) {
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

	private void setXBorder(GridBagLayout gbl, GridBagConstraints c) {
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 0;
		c.gridx = 1;
		c.weightx = 1;
//		c.weighty = 1;
		panel.add(new JLabel("Spring"), c);
		c.gridx = 2;
		panel.add(new JLabel("Summer"), c);
		c.gridx = 3;
		panel.add(new JLabel("Fall"), c);
	}

	private void setYBorder(GridBagLayout gbl, GridBagConstraints c, int[] range) {
		for (int i=range[0], y = 1; i<=range[1]; i++, y++) {
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = y;
			c.weightx = 1;
//			c.weighty = 1;
			JLabel label = new JLabel(Integer.toString(i));
			gbl.setConstraints(label, c);
			panel.add(label,c);
		}
	}

	private void setInnerGrid(GridBagLayout gbl, GridBagConstraints c, int[] range, ArrayList<Course> courses) {
		int recentYear = range[1];
		for (Course course : courses) {
			JPanel btnPanel = new JPanel();
			btnPanel.setLayout(new BorderLayout());
			int year = course.getYearOfCourse();
			String semester = course.getSeasonOfCourse();
			c.weightx = 1;
//			c.weighty = 1;
			c.fill = GridBagConstraints.BOTH;
			c.gridx = getXPositionBySemester(semester);
			c.gridy = recentYear - year + 1; // +1 to adjust for border
			String btnText = course.getSubject() + " "
					+ course.getCatalogNumber() + " - "
					+ course.getLocation();
			JButton btn = new JButton(btnText);
			btn.addActionListener(courseOfferingActionListener(course));
			btnPanel.add(btn, BorderLayout.NORTH);
			gbl.setConstraints(btnPanel, c);

			panel.add(btnPanel,c);
		}
	}

	private void fillAllGridsWithBlankJPanels(GridBagLayout gbl, GridBagConstraints c, int[] range) {
		for (int i=range[0], y = 1; i<=range[1]; i++, y++) {
			for (int x=1; x <= 3; x++) {
				JPanel btnPanel = new JPanel();
				c.fill = GridBagConstraints.BOTH;
				c.gridx = x;
				c.gridy = y;
				gbl.setConstraints(btnPanel, c);
				panel.add(btnPanel);
			}
		}
	}

	private int getXPositionBySemester(String semester) {
		if (semester.equals("Spring")) {
			return 1;
		}
		else if (semester.equals("Summer")) {
			return 2;
		}
		else if (semester.equals("Fall")) {
			return 3;
		}
		throw new Error(String.format("Unknown semester: %s", semester));
	}

	private void updateGrid(String department, String courseNumber) {
		// Remove existing components
		panel.removeAll();
		// Set up GridBagLayout
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
//		c.weighty = 1;
		panel.setLayout(gridBagLayout);
		// Fill GridBagLayout with JPanels
		int[] range = Course.getYearRangeOfCourseOfferings(department, courseNumber);
		setXBorder(gridBagLayout, c);
		setYBorder(gridBagLayout, c, range);
		setInnerGrid(gridBagLayout, c, range, Course.getCourseOfferings(department, courseNumber));
		fillAllGridsWithBlankJPanels(gridBagLayout, c, range);
		// Refresh with changes
		panel.revalidate();
		panel.repaint();
	}

	private ActionListener courseOfferingActionListener(Course course) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyCourseOfferingObservers(course);
			}
		};
	}

	/* -------------------
	 * Observer Methods
	 * ------------------- */

	private void registerAsObserver() {
		model.addCourseListObserver( (department, courseNumber) ->
				updateGrid(department, courseNumber));
	}

	private void notifyCourseOfferingObservers(Course course) {
		for (CourseOfferingObserver observer : CoursePlanner.getCourseOfferingObservers()) {
			observer.stateChanged(course);
		}
		revalidate();
	}

}
