package ca.cmpt213.courseplanner.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CoursePlanner extends JFrame {

	private static final String TITLE = "FAS Course Planner";
	private static final int PADDING = 8;

	private CoursePlanner model;
//	private Observable model;
	private static ArrayList<CoursePlannerObserver> observers = new ArrayList<>();

	public CoursePlanner() {
		model = this;
		setTitle(TITLE);
		// Use a box layout to create 3 vertical columns
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));

		updateUI();

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

//	public static void main(String[] args) {
//		CSVParser.loadCoursesFromCSV();
//		CoursePlanner planner = new CoursePlanner();
//	}

	// http://www.ugrad.cs.ubc.ca/~cs219/CourseNotes/Swing/swing-LayoutManagers-Box.html
	// Box.createRigidArea is padding between each panel so they don't get squished
	private void updateUI() {
		add(Box.createRigidArea(new Dimension(PADDING, 0)));
		add(setUpWest());
		add(Box.createRigidArea(new Dimension(PADDING, 0)));
		add(setUpCenter());
		add(Box.createRigidArea(new Dimension(PADDING, 0)));
		add(setUpEast());
		add(Box.createRigidArea(new Dimension(PADDING, 0)));
	}

	private Component setUpWest() {
		JPanel container = new JPanel();
		// Same as setUpEast
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

		container.add(Box.createRigidArea(new Dimension(0, PADDING)));
		JPanel courseListFilter = new CourseListFilter(model);
		setComponentResizing(courseListFilter);
		container.add(courseListFilter);

		container.add(Box.createRigidArea(new Dimension(0, PADDING)));
		JPanel courseList = new CourseList(model);
		setComponentResizing(courseList);
		container.add(courseList);

		container.add(Box.createRigidArea(new Dimension(0, PADDING)));

		return container;
	}

	private Component setUpCenter() {
		int minWidth = 300;
		int minHeight = 300;

		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());

		container.add(Box.createRigidArea(new Dimension(0, PADDING)));
		JPanel courseOfferingBySemester = new CourseOfferingBySemester(model);

		Dimension prefSize = new Dimension(minWidth, minHeight);
		courseOfferingBySemester.setPreferredSize(prefSize);
		// Similar to setComponentResizing but the center column needs to stretch in width
		Dimension maxSize = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		courseOfferingBySemester.setMaximumSize(maxSize);

		container.add(courseOfferingBySemester);
		container.add(Box.createRigidArea(new Dimension(0, PADDING)));

		return container;
	}

	private Component setUpEast() {
		JPanel container = new JPanel();
		// This column has a vertical layout
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

		container.add(Box.createRigidArea(new Dimension(0, PADDING)));
		JPanel statistics = new Statistics(model);
		setComponentResizing(statistics);
		container.add(statistics);

		container.add(Box.createRigidArea(new Dimension(0, PADDING)));
		JPanel details = new DetailsOfCourseOffering(model);
		setComponentResizing(details);
		container.add(details);

		container.add(Box.createRigidArea(new Dimension(0, PADDING)));

		return container;
	}
	private void setComponentResizing(JPanel panel) {
		int minWidth = 300;
		int minHeight = 200;

		// Default height and width of the JPanel at startup
		Dimension prefSize = new Dimension(minWidth, panel.getHeight());
		panel.setPreferredSize(prefSize);
		// Keep the same min width but allow the height to change
		Dimension newSize = new Dimension(minWidth, Integer.MAX_VALUE);
		panel.setMaximumSize(newSize);

//		Dimension prefSize = panel.getPreferredSize();
//		Dimension newSize = new Dimension(Integer.MAX_VALUE, (int)prefSize.getHeight());
//		panel.setMaximumSize(newSize);
	}


	/* -------------------
	 * Observer Methods
	 * ------------------- */
	public void addObserver(CoursePlannerObserver observer) {
		observers.add(observer);
	}
	public static ArrayList<CoursePlannerObserver> getObservers() {
		return observers;
	}

}
