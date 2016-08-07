package ca.cmpt213.courseplanner.ui;

import ca.cmpt213.courseplanner.logic.Course;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Observable;

public class CourseListFilter extends BasePanel{

	private static final String TITLE = "Course List Filter";
	private JPanel panel;

	public CourseListFilter (Observable model) {
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

		final JComboBox<String> departmentList = new JComboBox(departments);
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
		updateCourseListButton.setLayout(new BoxLayout(updateCourseListButton,
				BoxLayout.LINE_AXIS));
		updateCourseListPanel.add(updateCourseListButton);

		return updateCourseListPanel;
	}

}
