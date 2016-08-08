package ca.cmpt213.courseplanner.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class CourseOfferingBySemester extends BasePanel{

	private static final String TITLE = "Course Offerings by Semester";

	private JPanel panel;

	public CourseOfferingBySemester(CoursePlanner model) {
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
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,
				Color.BLACK, Color.GRAY));
		return panel;
	}

	private void updateGrid() {
		GridBagLayout courseOfferings = new GridBagLayout();
		panel.removeAll();

		panel.revalidate();
	}

//	private void registerAsObserver() {
//		model.addObserver( (department, isUndergradChecked, isGradChecked) ->
//				updateGrid(department, isUndergradChecked, isGradChecked));
//	}
}
