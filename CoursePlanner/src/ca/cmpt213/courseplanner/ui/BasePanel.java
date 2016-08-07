package ca.cmpt213.courseplanner.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Observable;

public abstract class BasePanel extends JPanel{

	public static CoursePlanner model;

	public BasePanel(CoursePlanner model) {
		this.model = model;
		setLayout(new BorderLayout());
		setTitle();
		setPanel();
		add(setTitle(), BorderLayout.NORTH);
		add(setPanel(), BorderLayout.CENTER);
	}

	protected Component setTitle() {
		JLabel title = new JLabel();
		return title;
	}

	protected Component setPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,
				Color.BLACK, Color.GRAY));
		return panel;
	}

	//When we have a model done
//	protected Observable getModel() {
//		return model;
//	}
}