package ca.cmpt213.courseplanner.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public abstract class BasePanel extends JPanel{

	private static Observable model;

	public BasePanel(Observable model) {
		this.model = model;
		setLayout(new BorderLayout());
		setTitle();
		setPanel();
		add(setTitle(), BorderLayout.NORTH);
		add(setPanel(), BorderLayout.CENTER);
	}

	abstract protected Component setTitle();

	abstract protected Component setPanel();

	//When we have a model done
//	protected Observable getModel() {
//		return model;
//	}
}