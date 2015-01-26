package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import ru.litsey2.cuttesseract.geometry.Vector4d;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	Vector4d planeNormal;

	PointRotator pointRotator;

	
	public MainFrame() {
		super();
		
		pointRotator = new PointRotator();

		setLayout(new SpringLayout());
		
		SpringLayout controlLayout = new SpringLayout();
		
		JPanel controlPanel = new JPanel(controlLayout);
		
		PointSelector yellow = new PointSelector(Color.yellow);
		PointSelector green = new PointSelector(Color.green);
		
		controlPanel.add(yellow);
		controlPanel.add(green);
		
		controlLayout.putConstraint(SpringLayout.NORTH, yellow, 0, SpringLayout.NORTH, controlPanel);
		controlLayout.putConstraint(SpringLayout.WEST, yellow, 0, SpringLayout.WEST, controlPanel);
		controlLayout.putConstraint(SpringLayout.EAST, yellow, 0, SpringLayout.EAST, controlPanel);
		
		controlLayout.putConstraint(SpringLayout.SOUTH, green, 0, SpringLayout.SOUTH, controlPanel);
		controlLayout.putConstraint(SpringLayout.WEST, green, 0, SpringLayout.WEST, controlPanel);
		controlLayout.putConstraint(SpringLayout.EAST, green, 0, SpringLayout.EAST, controlPanel);
		
		
		
		
		setLayout(new GridLayout(1, 1));
		add(controlPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();
		
		setSize(500, 500);
		setVisible(true);
	}

}
