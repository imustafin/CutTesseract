package ru.litsey2.cuttesseract;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;

import ru.litsey2.cuttesseract.geometry.Segment4d;

/**
 * A control panel with common, not specific to any input mode controls
 * 
 * @author Ilgiz Mustafin
 */
public class CommonControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param segmentDrawer a <code>SegmentDrawer</code> to control
	 */
	CommonControlPanel(final SegmentDrawer4d segmentDrawer) {
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
