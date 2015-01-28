package ru.litsey2.cuttesseract;

import java.awt.Dimension;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import ru.litsey2.cuttesseract.geometry.Cube4d;
import ru.litsey2.cuttesseract.geometry.Geometry;
import ru.litsey2.cuttesseract.geometry.Plane4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel{
	public ControlPanel(final SegmentDrawer4d segmentDrawer, JFrame mainFrame) {
		super();
		
		SpringLayout layout = new SpringLayout();
		
		PointVectorSelectorPanel pvSelector = new PointVectorSelectorPanel(segmentDrawer){
			
			@Override
			void pointVectorChanged() {
				Plane4d plane = new Plane4d(point, vector);
				Cube4d cube = new Cube4d(1, Colors.CUBE_COLOR);
				Set<Segment4d> set = Geometry.makeCut(plane, cube);
				set.addAll(cube.getSegments());
				segmentDrawer.pointRotator.setNewCut(set);
				segmentDrawer.repaint();
			}
			
		};
		
		CommonControlsPanel commonControls = new CommonControlsPanel(segmentDrawer);
		
		add(pvSelector);
		add(commonControls);
		
		layout.putConstraint(SpringLayout.NORTH, commonControls, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, commonControls, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, commonControls, 0, SpringLayout.SOUTH, this);
		
		layout.putConstraint(SpringLayout.SOUTH, pvSelector, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, pvSelector, 0, SpringLayout.EAST, commonControls);
		layout.putConstraint(SpringLayout.EAST, pvSelector, 0, SpringLayout.EAST, this);
		
		setPreferredSize(new Dimension(100, 200));
		
		setLayout(layout);
	}
}
