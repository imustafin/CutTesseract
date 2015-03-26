package ru.litsey2.cuttesseract;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import ru.litsey2.cuttesseract.geometry.Cube4d;
import ru.litsey2.cuttesseract.geometry.Geometry;
import ru.litsey2.cuttesseract.geometry.Plane4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;

/**
 * A control panel with controls only specific to point-vector input mode
 * 
 * @author Ilgiz Mustafin
 */
public class PointVectorControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param segmentDrawer
	 *            a <code>SegmentDrawer</code> to control
	 */
	public PointVectorControlPanel(final SegmentDrawer4d segmentDrawer) {
		super();

		SpringLayout layout = new SpringLayout();

		PointVectorSelectorPanel pvSelector = new PointVectorSelectorPanel(
				segmentDrawer) {

			private static final long serialVersionUID = 1L;

			@Override
			void pointVectorChanged() {
				Plane4d plane = new Plane4d(point, vector);
				Cube4d cube = new Cube4d(1, Colors.CUBE_COLOR);
				ArrayList<Segment4d> list = Geometry.makeSection(plane, cube);
				list.addAll(cube.getSegments());
				segmentDrawer.pointRotator.setNewSection(list, plane.getNormal(), cube, true);
//				segmentDrawer.pointRotator.rotateNormalToUs();
				segmentDrawer.repaint();
			}

		};

		CommonControlPanel commonControls = new CommonControlPanel(
				segmentDrawer);

		add(pvSelector);
		add(commonControls);

		layout.putConstraint(SpringLayout.NORTH, commonControls, 0,
				SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, commonControls, 0,
				SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, commonControls, 0,
				SpringLayout.SOUTH, this);

		layout.putConstraint(SpringLayout.SOUTH, pvSelector, 0,
				SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, pvSelector, 0,
				SpringLayout.EAST, commonControls);
		layout.putConstraint(SpringLayout.EAST, pvSelector, 0,
				SpringLayout.EAST, this);

		setPreferredSize(new Dimension(100, 200));

		setLayout(layout);
	}
}
