package com.bth.nrk.ui.user;

import javax.swing.JPanel;

public abstract class AbstractScalePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract int getGrade();

	public abstract void reset();

	public abstract void enableOrDisableInputFields(boolean isEnable);

	public boolean isGradeSelected() {
		return getGrade() != -1;
	}

}
