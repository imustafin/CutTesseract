package ru.litsey2.cuttesseract;

import javax.swing.JFrame;

import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;
import ru.litsey2.cuttesseract.geometry.Vector4d;

public class Panel4d {

	JFrame frame;

	Vector4d planeNormal;
	
	PointRotator pointRotator = new PointRotator();

	void createAndShowGUI() {

		pointRotator.add(new Segment4d(Point4d.ZERO, new Point4d(1, 0, 0, 0),
				Constants.X_COLOR));
		pointRotator.add(new Segment4d(Point4d.ZERO, new Point4d(0, 1, 0, 0),
				Constants.Y_COLOR));
		pointRotator.add(new Segment4d(Point4d.ZERO, new Point4d(0, 0, 1, 0),
				Constants.Z_COLOR));
		pointRotator.add(new Segment4d(Point4d.ZERO, new Point4d(0, 0, 0, 1),
				Constants.W_COLOR));

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new SegmentDrawer4d(pointRotator));
		frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
}
