package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ru.litsey2.cuttesseract.geometry.Point2d;
import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment2d;
import ru.litsey2.cuttesseract.geometry.Segment4d;

public class CubeCut4dFrame {

	JFrame frame;

	PointRotator pointRotator = new PointRotator();

	Color X_COLOR = Color.RED;
	Color Y_COLOR = Color.GREEN;
	Color Z_COLOR = Color.BLUE;
	Color W_COLOR = Color.ORANGE;

	void createAndShowGUI() {

		pointRotator.add(new Segment4d(Point4d.ZERO, new Point4d(1, 0, 0, 0),
				X_COLOR));
		pointRotator.add(new Segment4d(Point4d.ZERO, new Point4d(0, 1, 0, 0),
				Y_COLOR));
		pointRotator.add(new Segment4d(Point4d.ZERO, new Point4d(0, 0, 1, 0),
				Z_COLOR));
		pointRotator.add(new Segment4d(Point4d.ZERO, new Point4d(0, 0, 0, 1),
				W_COLOR));

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new MyPanel());
		frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

	@SuppressWarnings("serial")
	class MyPanel extends JPanel {

		double MULT = 150;

		double SLOW_SCROLL_SCALE = 1;
		double NORMAL_SCROLL_SCALE = 10;
		double FAST_SCROLL_SCALE = 100;

		int OX = (int) MULT;
		int OY = (int) MULT;

		int OstartX = 0;
		int OstartY = 0;
		int OoldX = 0;
		int OoldY = 0;

		int MrotX = 0;
		int MrotY = 0;

		int RrotX = 0;
		int RrotY = 0;

		double rotK = 0.01;

		public MyPanel() {
			setBorder(BorderFactory.createLineBorder(Color.black));

			addMouseMotionListener(new MouseMotionAdapter() {

				@Override
				public void mouseDragged(MouseEvent e) {
					if (SwingUtilities.isLeftMouseButton(e)) {
						OX = OoldX + (e.getX() - OstartX);
						OY = OoldY + (e.getY() - OstartY);
						repaint();
					}
					if (SwingUtilities.isRightMouseButton(e)) {
						double deltaX = (e.getX() - RrotX) * rotK;
						double deltaY = (e.getY() - RrotY) * rotK;
						RrotX = e.getX();
						RrotY = e.getY();
						pointRotator.addAngles(0, 0, deltaY, deltaX);
						repaint();
					}
					if (SwingUtilities.isMiddleMouseButton(e)) {
						double deltaX = (e.getX() - MrotX) * rotK;
						double deltaY = (e.getY() - MrotY) * rotK;
						MrotX = e.getX();
						MrotY = e.getY();
						pointRotator.addAngles(deltaX, deltaY, 0, 0);
						repaint();
					}
				}
			});

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent arg0) {
					super.mousePressed(arg0);
					if (SwingUtilities.isLeftMouseButton(arg0)) {
						OstartX = arg0.getX();
						OstartY = arg0.getY();
						OoldX = OX;
						OoldY = OY;
					}
					if (SwingUtilities.isRightMouseButton(arg0)) {
						RrotX = arg0.getX();
						RrotY = arg0.getY();
					}
					if (SwingUtilities.isMiddleMouseButton(arg0)) {
						MrotX = arg0.getX();
						MrotY = arg0.getY();
					}
				}
			});

			addMouseWheelListener(new MouseWheelListener() {

				@Override
				public void mouseWheelMoved(MouseWheelEvent arg0) {
					double scale = NORMAL_SCROLL_SCALE;
					if (arg0.isShiftDown()) {
						scale = FAST_SCROLL_SCALE;
					}
					if (arg0.isControlDown()) {
						scale = SLOW_SCROLL_SCALE;
					}
					int nmult = (int) (MULT + scale * arg0.getWheelRotation());
					if (nmult < 0.001) {
						Toolkit.getDefaultToolkit().beep();
					} else {
						MULT = nmult;
					}
					repaint();
				}

			});

		}

		public Dimension getPreferredSize() {
			return new Dimension(300, 300);
		}

		int xc(double x) {
			return (int) (OX + x * MULT);
		}

		int yc(double y) {
			return (int) (OY - y * MULT);
		}

		void drawSegment2d(Segment2d s, Graphics g) {
			Color oldColor = g.getColor();
			g.setColor(s.getColor());
			
			Point2d a = s.getA();
			Point2d b = s.getB();
			
			int x1 = xc(a.getX());
			int y1 = yc(a.getY());
			int x2 = xc(b.getX());
			int y2 = yc(b.getY());
			
			g.drawLine(x1, y1, x2, y2);
			
			g.setColor(oldColor);
			
		}
		
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Set<Segment2d> segments = pointRotator.getSegments2d();
			
			for (Segment2d s : segments) {
				drawSegment2d(s, g);
			}
		}

	}
}
