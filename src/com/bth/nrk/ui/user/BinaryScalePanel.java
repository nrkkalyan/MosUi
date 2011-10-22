/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BinaryScalePanel.java
 *
 * Created on Jun 5, 2011, 3:45:03 PM
 */
package com.bth.nrk.ui.user;

/**
 * 
 * @author Kalyan
 */
public class BinaryScalePanel extends AbstractScalePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new form BinaryScalePanel */
	public BinaryScalePanel() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		positiveTextField = new javax.swing.JTextField();
		positiveRadioButton = new javax.swing.JRadioButton();
		negativeRadioButton = new javax.swing.JRadioButton();
		negativeTextField = new javax.swing.JTextField();

		positiveTextField.setEditable(false);
		positiveTextField.setText("Acceptable");

		buttonGroup1.add(positiveRadioButton);
		positiveRadioButton.setText("1");

		buttonGroup1.add(negativeRadioButton);
		negativeRadioButton.setText("0");
		negativeRadioButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				negativeRadioButtonActionPerformed(evt);
			}
		});

		negativeTextField.setEditable(false);
		negativeTextField.setText("Un-acceptable");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(positiveRadioButton).addComponent(negativeRadioButton))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(negativeTextField)
										.addComponent(positiveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												layout.createSequentialGroup().addComponent(positiveRadioButton).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(negativeRadioButton))
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(positiveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(negativeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	private void negativeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_negativeRadioButtonActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_negativeRadioButtonActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JRadioButton negativeRadioButton;
	private javax.swing.JTextField negativeTextField;
	private javax.swing.JRadioButton positiveRadioButton;
	private javax.swing.JTextField positiveTextField;

	// End of variables declaration//GEN-END:variables
	@Override
	public int getGrade() {
		if (positiveRadioButton.isSelected()) {
			return 1;
		} else if (negativeRadioButton.isSelected()) {
			return 0;
		}
		return -1;
	}

	@Override
	public void reset() {
		System.out.println("calling reset");
		buttonGroup1.clearSelection();
	}

	@Override
	public void enableOrDisableInputFields(boolean isEnable) {
		reset();
		positiveRadioButton.setEnabled(isEnable);
		negativeRadioButton.setEnabled(isEnable);
	}

}
