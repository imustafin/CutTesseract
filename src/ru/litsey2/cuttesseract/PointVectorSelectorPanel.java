package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Vector4d;

@SuppressWarnings("serial")
public abstract class PointVectorSelectorPanel extends JPanel {

	Point4d point;
	Vector4d vector;

	abstract void pointVectorChanged();

	PointVectorSelectorPanel(SegmentDrawer4d segmentDrawer) {

		
		setPreferredSize(new Dimension(200, 300));
		
		setBackground(Color.green);

		SpringLayout layout = new SpringLayout();

		PointPicker pSelector = new PointPicker(Color.LIGHT_GRAY) {

			@Override
			void onPointChanged() {
				point = getPoint4d();
				pointVectorChanged();
			}
		};
		
		point = pSelector.getPoint4d();
		
		PointPicker vSelector = new PointPicker(Color.CYAN) {

			@Override
			void onPointChanged() {
				vector = new Vector4d(getPoint4d());
				pointVectorChanged();
			}
		};

		vector = new Vector4d(vSelector.getPoint4d());
		
		add(pSelector);
		add(vSelector);

		layout.putConstraint(SpringLayout.SOUTH, pSelector, 0, SpringLayout.NORTH,
				vSelector);
		layout.putConstraint(SpringLayout.WEST, pSelector, 0, SpringLayout.WEST,
				this);
		layout.putConstraint(SpringLayout.EAST, pSelector, 0, SpringLayout.EAST,
				this);

		layout.putConstraint(SpringLayout.WEST, vSelector, 0, SpringLayout.WEST,
				this);
		layout.putConstraint(SpringLayout.EAST, vSelector, 0, SpringLayout.EAST,
				this);
		layout.putConstraint(SpringLayout.SOUTH, vSelector, 0, SpringLayout.SOUTH, this);
		setLayout(layout);
	}
}
