package ru.litsey2.cubecut3d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

import ru.litsey2.cubecut3d.MyGeom.Point2d;
import ru.litsey2.cubecut3d.MyGeom.Point3d;
import ru.litsey2.cubecut3d.MyGeom.Segment3d;

public class Draw3d {

	JFrame frame;

	Set<Segment3d> segments = new TreeSet<Segment3d>();

	Color X_COLOR = Color.RED;
	Color Y_COLOR = Color.GREEN;
	Color Z_COLOR = Color.BLUE;

	void createAndShowGUI() {

		Point3d pointO = new Point3d(0, 0, 0);

		segments.add(new Segment3d(pointO, new Point3d(1, 0, 0), X_COLOR));
		segments.add(new Segment3d(pointO, new Point3d(0, 1, 0), Y_COLOR));
		segments.add(new Segment3d(pointO, new Point3d(0, 0, 1), Z_COLOR));

		frame = new JFrame("CubeCut3d");
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

		int OrotX = 0;
		int OrotY = 0;
		double rotK = 0.01;
		
		
		public MyPanel() {
			setBorder(BorderFactory.createLineBorder(Color.black));

			addMouseMotionListener(new MouseMotionAdapter() {

				@Override
				public void mouseDragged(MouseEvent arg0) {
					if (SwingUtilities.isRightMouseButton(arg0)) {
						OX = arg0.getX();
						OY = arg0.getY();
						repaint();
						return;
					}
					if (SwingUtilities.isLeftMouseButton(arg0)) {
						OX = OoldX + (arg0.getX() - OstartX);
						OY = OoldY + (arg0.getY() - OstartY);
						repaint();
					}
					if (SwingUtilities.isMiddleMouseButton(arg0)) {
						double deltaX = (arg0.getX() - OrotX) * rotK;
						double deltaY = (arg0.getY() - OrotY) * rotK;
						OrotX = arg0.getX();
						OrotY = arg0.getY();
						rotateAll(deltaX, deltaY);
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
						OX = arg0.getX();
						OY = arg0.getY();
						repaint();
					}
					if (SwingUtilities.isMiddleMouseButton(arg0)) {
						OrotX = arg0.getX();
						OrotY = arg0.getY();
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

		Point3d getRotated(Point3d a, double dx, double dy) {
			Point3d n = new Point3d(a);

			double x = a.x;
			double y = a.y;
			double z = a.z;

			n.x = x * Math.cos(dx) - y * Math.sin(dx);
			n.y = x * Math.sin(dx) + y * Math.cos(dx);

			x = n.x;
			y = n.y;
			z = n.z;

			n.y = y * Math.cos(dy) - z * Math.sin(dy);
			n.z = y * Math.sin(dy) + z * Math.cos(dy);

			return n;

		}

		void rotateAll(double dx, double dy) {
			for (Segment3d s : segments) {
				s.a = getRotated(s.a, dx, dy);
				s.b = getRotated(s.b, dx, dy);
			}
		}

		void drawSegment3d(Segment3d s, Graphics g) {
			Color oldColor = g.getColor();
			g.setColor(s.color);

			Point3d a = s.a;
			Point3d b = s.b;

			int x1 = xc(a.x);
			int y1 = yc(a.z);
			int x2 = xc(b.x);
			int y2 = yc(b.z);
			g.drawLine(x1, y1, x2, y2);
			int r = 2;
			g.fillOval(x1 - r, y1 - r, 2 * r, 2 * r);
			g.fillOval(x2 - r, y2 - r, 2 * r, 2 * r);
			g.setColor(oldColor);
		}


		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			for (Segment3d s : segments) {
				drawSegment3d(s, g);
			}
		}

	}
}
