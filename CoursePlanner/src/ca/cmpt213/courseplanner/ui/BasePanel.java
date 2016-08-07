package ca.cmpt213.courseplanner.ui;

import javax.swing.*;
import java.awt.*;

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

	protected abstract Component setTitle();

	protected abstract Component setPanel();

	//When we have a model done
//	protected Observable getModel() {
//		return model;
//	}
}