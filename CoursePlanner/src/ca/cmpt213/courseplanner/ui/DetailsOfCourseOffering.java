package ca.cmpt213.courseplanner.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class DetailsOfCourseOffering extends BasePanel {

	private static final String TITLE = "Details of Course Offering";
	private JPanel panel;

	public DetailsOfCourseOffering(CoursePlanner model) {
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
}
