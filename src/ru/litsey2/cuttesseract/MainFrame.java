package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.sun.corba.se.impl.oa.poa.AOMEntry;

import ru.litsey2.cuttesseract.geometry.Vector4d;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	Vector4d planeNormal;

	PointRotator pointRotator;

	
	public MainFrame() {
		super();
		
		pointRotator = new PointRotator();

		setLayout(new SpringLayout());
		
		SpringLayout mainLayout = new SpringLayout();
		
		JPanel mainPanel = new JPanel(mainLayout);
		
		SegmentDrawer4d segmentDrawer = new SegmentDrawer4d(pointRotator);
		
		PointPicker pointSelector = new PointPicker(Color.green, segmentDrawer);
		
		mainPanel.add(segmentDrawer);
		
		mainLayout.putConstraint(SpringLayout.NORTH, segmentDrawer, 0, SpringLayout.NORTH, mainPanel);
		mainLayout.putConstraint(SpringLayout.SOUTH, segmentDrawer, 0, SpringLayout.NORTH, pointSelector);
		mainLayout.putConstraint(SpringLayout.EAST, segmentDrawer, 0, SpringLayout.EAST, mainPanel);
		mainLayout.putConstraint(SpringLayout.WEST, segmentDrawer, 0, SpringLayout.WEST, mainPanel);
		
		mainPanel.add(pointSelector);
		
		mainLayout.putConstraint(SpringLayout.SOUTH, pointSelector, 0, SpringLayout.SOUTH, mainPanel);
		mainLayout.putConstraint(SpringLayout.WEST, pointSelector, 0, SpringLayout.WEST, mainPanel);
		mainLayout.putConstraint(SpringLayout.EAST, pointSelector, 0, SpringLayout.EAST, mainPanel);
		
		
		
		
		setLayout(new GridLayout(1, 1));
		add(mainPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();
		
		setSize(500, 500);
		setVisible(true);
	}

}
