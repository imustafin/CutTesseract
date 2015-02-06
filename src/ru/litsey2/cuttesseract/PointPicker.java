package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import ru.litsey2.cuttesseract.geometry.Point4d;

/**
 * <code>JPanel</code> with two <code>{@link AxisBox}</code> which define point
 * 
 * @author Ilgiz Mustafin
 *
 */
public abstract class PointPicker extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * The first <code>{@link AxisBox}</code>, defines XY coordinates
	 */
	AxisBox axis1;
	/**
	 * The second <code>{@link AxisBox}</code>, defines ZW coordinates
	 */
	AxisBox axis2;
	/**
	 * This panel
	 */
	JPanel panel;

	/**
	 * Constructs point with the selected coordinates
	 * @return the picked point
	 */
	Point4d getPoint4d() {
		return new Point4d(axis1.x1, axis1.y1, axis2.x1, axis2.y1);
	}

	/**
	 * Constructs <code>PointPicker</code> panel with the specified background
	 * @param bgColor background color of the panel
	 */
	public PointPicker(Color bgColor) {
		setPreferredSize(new Dimension(200, 101));

		setBackground(bgColor);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);

		final JFormattedTextField textX = new JFormattedTextField(decimalFormat);
		textX.setColumns(5);

		final JFormattedTextField textY = new JFormattedTextField(decimalFormat);
		textY.setColumns(5);

		final JFormattedTextField textZ = new JFormattedTextField(decimalFormat);
		textZ.setColumns(5);

		final JFormattedTextField textW = new JFormattedTextField(decimalFormat);
		textW.setColumns(5);

		axis1 = new AxisBox(this, Colors.X_COLOR, Colors.Y_COLOR, textX, textY) {
			private static final long serialVersionUID = 1L;

			@Override
			void onCoordChanged() {
				onPointChanged();
			}
		};
		axis2 = new AxisBox(this, Colors.Z_COLOR, Colors.W_COLOR, textZ, textW) {
			private static final long serialVersionUID = 1L;

			@Override
			void onCoordChanged() {
				onPointChanged();
			}
		};

		JButton buttonSet = new JButton("Set");
		buttonSet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				axis1.setX(Double.parseDouble(textX.getText()));
				axis1.setY(Double.parseDouble(textY.getText()));
				axis2.setX(Double.parseDouble(textZ.getText()));
				axis2.setY(Double.parseDouble(textW.getText()));
			}
		});

		panel = new JPanel();

		GroupLayout layout = new GroupLayout(this);

		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createSequentialGroup().addComponent(textX)
								.addComponent(textY).addComponent(textZ)
								.addComponent(textW).addComponent(buttonSet))
				.addGroup(
						layout.createParallelGroup().addComponent(axis1)
								.addComponent(axis2)));
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createSequentialGroup().addComponent(textX)
								.addComponent(textY).addComponent(textZ)
								.addComponent(textW))
				.addGroup(
						layout.createParallelGroup().addComponent(axis1)
								.addComponent(axis2)));
	}

	/**
	 * Is called when new point is picked
	 */
	abstract void onPointChanged();

}
