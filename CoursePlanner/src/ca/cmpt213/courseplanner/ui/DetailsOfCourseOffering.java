package ca.cmpt213.courseplanner.ui;

import ca.cmpt213.courseplanner.logic.Course;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class DetailsOfCourseOffering extends BasePanel {

	private static final String TITLE = "Details of Course Offering";
	private JPanel panel;

	public DetailsOfCourseOffering(CoursePlanner model) {
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

	private void updatePanel(Course course) {
		// Remove existing components
		panel.removeAll();

		// Set up Panel
		JPanel courseOfferingPanel = new JPanel();
		courseOfferingPanel.setLayout(new GridBagLayout());
		JPanel detailPanel = new JPanel();
		detailPanel.setLayout(new GridBagLayout());
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(gridBagLayout);

		// Fill Panel
		fillLabels(detailPanel, c, course);
		fillTextField(detailPanel, c, course);

		// Refresh with changes
		GridBagConstraints outerC = new GridBagConstraints();
		outerC.weightx = 1;
		outerC.weightx = 1;
		courseOfferingPanel.add(detailPanel, outerC);
		panel.add(courseOfferingPanel, BoxLayout.X_AXIS);
		panel.revalidate();
		panel.repaint();

	}

	private void fillTextField(JPanel detailPanel, GridBagConstraints c, Course course) {
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 4;
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		JTextArea textArea = new JTextArea(Integer.MAX_VALUE,4);
		textArea.append(course.getSubject() + " " + course.getCatalogNumber() + "\n");
		textArea.append(course.getSemester() + "\n");
		textArea.append(course.getLocation() + "\n");
		textArea.append(course.getInstructorsAsString());
		textArea.setEditable(false);
		panel.add(textArea, c);
		detailPanel.add(textArea, c);
	}

	private void fillLabels(JPanel detailPanel, GridBagConstraints c, Course course) {
		String enrollment = course.getEnrollmentTotal()+"/"+course.getEnrollmentCapacity();
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		detailPanel.add(new Label("Course Name:"), c);
		c.gridy = 1;
		detailPanel.add(new Label("Semester:"), c);
		c.gridy = 2;
		detailPanel.add(new Label("Location:"), c);
		c.gridy = 3;
		detailPanel.add(new Label("Instructors:"), c);
		c.gridy = 4;
		detailPanel.add(new Label("Section Type") ,c);
		c.gridx = 1;
		detailPanel.add(new Label("Enrollment (filled/cap)") ,c);
		c.gridy = 5;
		c.gridx = 0;
		detailPanel.add(new Label("LAB") ,c);
		c.gridx = 1;
		detailPanel.add(new Label(enrollment), c);
		c.gridx = 0;
		c.gridy = 6;
		detailPanel.add(new Label("LEC") ,c);
		c.gridx = 1;
		detailPanel.add(new Label(enrollment) ,c);
	}

	/* -------------------
	 * Observer Methods
	 * ------------------- */

	private void registerAsObserver() {
		model.addCourseOfferingObserver( course -> updatePanel(course));
	}

}
