package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Vector4d;

/**
 * A panel with two <code>PointPickers</code>. The first one is used to select a
 * point while the other is used to select a vector.
 * 
 * @author Ilgiz Mustafin
 */

public abstract class PointVectorSelectorPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * The currently selected point
	 */
	Point4d point;
	/**
	 * The currently selected vector
	 */
	Vector4d vector;

	/**
	 * Is called when the {@link #point} or the {@link #vector} is changed
	 */
	abstract void pointVectorChanged();

	/**
	 * 
	 * @param segmentDrawer
	 *            a <code>SegmentDrawer</code> to control
	 */
	PointVectorSelectorPanel(SegmentDrawer4d segmentDrawer) {

		setPreferredSize(new Dimension(200, 300));

		setBackground(Color.green);

		SpringLayout layout = new SpringLayout();

		PointPicker pSelector = new PointPicker(Color.LIGHT_GRAY) {

			private static final long serialVersionUID = 1L;

			@Override
			void onPointChanged() {
				point = getPoint4d();
				pointVectorChanged();
			}
		};

		point = pSelector.getPoint4d();

		PointPicker vSelector = new PointPicker(Color.CYAN) {

			private static final long serialVersionUID = 1L;

			@Override
			void onPointChanged() {
				vector = new Vector4d(getPoint4d());
				pointVectorChanged();
			}
		};

		vector = new Vector4d(vSelector.getPoint4d());

		add(pSelector);
		add(vSelector);

		layout.putConstraint(SpringLayout.SOUTH, pSelector, 0,
				SpringLayout.NORTH, vSelector);
		layout.putConstraint(SpringLayout.WEST, pSelector, 0,
				SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, pSelector, 0,
				SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.WEST, vSelector, 0,
				SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, vSelector, 0,
				SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, vSelector, 0,
				SpringLayout.SOUTH, this);
		setLayout(layout);
	}
}
