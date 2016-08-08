package ca.cmpt213.courseplanner.ui;

import ca.cmpt213.courseplanner.logic.Course;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class Statistics extends BasePanel{

	private static final String TITLE = "Statistics";
	private static final String[] SEMESTERS = {"Spring,", "Summer", "Fall"};
	private static final String[] CAMPUSES = {"Bby", "Sry", "Van","Other" };
	private static final int WIDTH = 250;
	private static final int HEIGHT = 150;

	private JPanel panel;
	private static BarGraphModel semesterGraphModel;
	private static BarGraphModel campusGraphModel;
	private static JLabel semesterHistogram;
	private static JLabel campusHistogram;
	private static JLabel courseName;


	public Statistics (CoursePlanner model) {
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		courseName = new JLabel("Course: ");
		panel.add(courseName);
		panel.add(new JLabel("Semester Offerings:"));
		panel.add(semesterHistogram());
		panel.add(new JLabel("Campus Offerings:"));
		panel.add(campusHistogram());
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,
				Color.BLACK, Color.GRAY));
		return panel;
	}

	private JLabel semesterHistogram() {
		int[] semestersOffered = {0, 0, 0};
		semesterGraphModel = new BarGraphModel(semestersOffered, SEMESTERS);
		BarGraphIcon graphIcon = new BarGraphIcon(semesterGraphModel, WIDTH, HEIGHT);
		semesterHistogram = new JLabel(graphIcon);
		return semesterHistogram;
	}

	private JLabel campusHistogram() {
		int[] campusesOffered = {0, 0, 0, 0};
		campusGraphModel = new BarGraphModel(campusesOffered, CAMPUSES);
		BarGraphIcon graphIcon = new BarGraphIcon(campusGraphModel, WIDTH, HEIGHT);
		campusHistogram = new JLabel(graphIcon);
		return campusHistogram;
	}

	private void updateHistograms(String department, String courseNumber) {
		courseName.setText("Course " + department + " " + courseNumber);
		semesterGraphModel.setData(Course.findSeasonOfferings(department, courseNumber));
		campusGraphModel.setData(Course.findCampusOfferings(department, courseNumber));
		semesterHistogram.repaint();
		campusHistogram.repaint();
	}

	private void registerAsObserver() {
		model.addCourseListObserver( (department, courseNumber) ->
				updateHistograms(department, courseNumber));
	}
}
