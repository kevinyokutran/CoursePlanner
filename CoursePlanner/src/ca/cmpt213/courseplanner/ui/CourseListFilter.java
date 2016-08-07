package ca.cmpt213.courseplanner.ui;

import ca.cmpt213.courseplanner.logic.Course;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseListFilter extends BasePanel{

	private static final String TITLE = "Course List Filter";
	private JPanel panel;
	private JComboBox<String> departmentList;

	public CourseListFilter (CoursePlanner model) {
		super(model);
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
		//panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,
				Color.BLACK, Color.GRAY));
		panel.add(departmentList());
		panel.add(undergraduateCheckbox());
		panel.add(graduateCheckbox());
		panel.add(updateCourseList());
		return panel;
	}

	private JPanel departmentList() {
		String[] departments = Course.getAlphabeticalDepartmentList().stream().toArray(String[]::new);
		JPanel departmentPanel = new JPanel();
		departmentPanel.add(new JLabel("Department"));

		this.departmentList = new JComboBox(departments);
		departmentPanel.add(departmentList);

		return departmentPanel;
	}

	private JPanel undergraduateCheckbox() {
		JPanel undergraduateCheckboxPanel = new JPanel();
		final JCheckBox checkBox = new JCheckBox();
		undergraduateCheckboxPanel.setLayout(new BoxLayout(undergraduateCheckboxPanel,
				BoxLayout.LINE_AXIS));

		undergraduateCheckboxPanel.add(checkBox);
		undergraduateCheckboxPanel.add(new JLabel("Include undergrad courses"));

		return undergraduateCheckboxPanel;
	}

	private JPanel graduateCheckbox() {
		JPanel graduateCheckboxPanel = new JPanel();
		final JCheckBox checkBox = new JCheckBox();
		graduateCheckboxPanel.setLayout(new BoxLayout(graduateCheckboxPanel,
				BoxLayout.LINE_AXIS));

		graduateCheckboxPanel.add(checkBox);
		graduateCheckboxPanel.add(new JLabel("Include undergrad courses"));

		return graduateCheckboxPanel;
	}

	private JPanel updateCourseList() {
		JPanel updateCourseListPanel = new JPanel();
		final JButton updateCourseListButton = new JButton("Update Course List");
		final JComboBox departmentList = this.departmentList;
		updateCourseListButton.setLayout(new BoxLayout(updateCourseListButton,
				BoxLayout.LINE_AXIS));
		updateCourseListButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String department = departmentList.getSelectedItem().toString();
				notifyObservers(department);
			}
		});
		updateCourseListPanel.add(updateCourseListButton);

		return updateCourseListPanel;
	}

	/* -------------------
	 * Observer Methods
	 * ------------------- */
	private void notifyObservers(String department) {
		for (CoursePlannerObserver observer : CoursePlanner.getObservers()) {
			observer.stateChanged(department);
		}
		revalidate();
	}
}
