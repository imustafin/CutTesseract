package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ru.litsey2.cuttesseract.geometry.Point2d;
import ru.litsey2.cuttesseract.geometry.Segment2d;

@SuppressWarnings("serial")
class SegmentDrawer4d extends JPanel implements MouseMotionListener,
		MouseListener, MouseWheelListener, KeyListener {

	/**
	 * 
	 */

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

	PointRotator pointRotator;
	
	public SegmentDrawer4d(PointRotator pointRotator) {
		this.pointRotator = pointRotator;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		setBackground(Color.gray);
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

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			OstartX = e.getX();
			OstartY = e.getY();
			OoldX = OX;
			OoldY = OY;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			RrotX = e.getX();
			RrotY = e.getY();
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			MrotX = e.getX();
			MrotY = e.getY();
		}

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double scale = NORMAL_SCROLL_SCALE;
		if (e.isShiftDown()) {
			scale = FAST_SCROLL_SCALE;
		}
		if (e.isControlDown()) {
			scale = SLOW_SCROLL_SCALE;
		}
		int nmult = (int) (MULT + scale * e.getWheelRotation());
		if (nmult < 0.001) {
			Toolkit.getDefaultToolkit().beep();
		} else {
			MULT = nmult;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.err.println(e.getKeyChar());
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		double deg = 0.1;
		char ch = e.getKeyChar();
		double a = 0;
		double b = 0;
		double c = 0;
		double d = 0;
		switch (ch) {
		case 'q':
			a -= +deg;
			break;
		case 'w':
			a += deg;
			break;
		case 'a':
			b -= deg;
			break;
		case 's':
			b += deg;
			break;
		case 'e':
			c -= deg;
			break;
		case 'r':
			c += deg;
			break;
		case 'd':
			d -= deg;
			break;
		case 'f':
			d += deg;
//		case 'z':
//			rotNormalToPlane();
		default:
			break;
		}
		pointRotator.addAngles(a, b, c, d);
		repaint();
	}

//	private void rotNormalToPlane() {
//		double a0 = Math.atan(planeNormal.getY() / planeNormal.getX());
//		pointRotator.addAngles(a0, 0, 0, 0);
//		double a1 = Math.atan(a)
//		tan( angles[1])= z/y'
//	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

}