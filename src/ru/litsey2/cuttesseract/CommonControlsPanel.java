package ru.litsey2.cuttesseract;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CommonControlsPanel extends JPanel {

	CommonControlsPanel(final SegmentDrawer4d segmentDrawer) {
		super();

		setPreferredSize(new Dimension(150, 200));
		
		GridLayout layout = new GridLayout(0, 1);
		
		setLayout(layout);
		
		JButton toggleCubeButton = new JButton("Toggle Cube");
		toggleCubeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				segmentDrawer.toggleCube();
			}
		});
		
		add(toggleCubeButton);
		
	}

}
