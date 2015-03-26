package ru.litsey2.cuttesseract;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ru.litsey2.cuttesseract.geometry.Geometry;
import ru.litsey2.cuttesseract.geometry.Point2d;
import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment2d;
import ru.litsey2.cuttesseract.geometry.Segment4d;
import ru.litsey2.cuttesseract.geometry.Vector2d;
import ru.litsey2.cuttesseract.geometry.Vector4d;

/**
 * A <code>JPanel</code> which can draw 4d segments on itself
 * 
 * @author Ilgiz Mustafin
 *
 */
class SegmentDrawer4d extends JPanel implements MouseMotionListener,
		MouseListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Image upscale coefficient
	 */
	double imageScale = 150;

	/**
	 * Line width
	 */
	static final float LINE_WIDTH = 3;

	/**
	 * Mouse wheel scroll multiplier
	 */
	static final double SLOW_SCROLL_SPEED = 1;
	/**
	 * Mouse wheel scroll multiplier
	 */
	static final double NORMAL_SCROLL_SPEED = 10;
	/**
	 * Mouse wheel scroll multiplier
	 */
	static final double FAST_SCROLL_SPEED = 100;

	/**
	 * <code>X</code> coordinate of the origin
	 */
	int originX = (int) imageScale;
	/**
	 * <code>Y</code> coordinate of the origin
	 */
	int originY = (int) imageScale;

	/**
	 * The <code>X</code> coordinate of the ending of the <i>move origin</i>
	 * motion
	 */
	int moveX1 = 0;
	/**
	 * The <code>Y</code> coordinate of the ending of the <i>move origin</i>
	 * motion
	 */
	int moveY1 = 0;
	/**
	 * The <code>X</code> coordinate of the beginning of the <i>move origin</i>
	 * motion
	 */
	int moveX0 = 0;
	/**
	 * The <code>Y</code> coordinate of the beginning of the <i>move origin</i>
	 * motion
	 */
	int moveY0 = 0;

	/**
	 * The <code>X</code> coordinate of the beginning of the <i>middle mouse
	 * button rotation</i>
	 */
	int middleRotationX0 = 0;
	/**
	 * The <code>Y</code> coordinate of the beginning of the <i>middle mouse
	 * button rotation</i>
	 */
	int middleRotationY0 = 0;

	/**
	 * The <code>X</code> coordinate of the beginning of the <i>right mouse
	 * button rotation</i>
	 */
	int rightRotationX0 = 0;
	/**
	 * The <code>Y</code> coordinate of the beginning of the <i>right mouse
	 * button rotation</i>
	 */
	int rightRotationY0 = 0;

	/**
	 * Mouse motion multiplier, used when the image is rotated
	 */
	static final double ROTATION_SPEED = 0.01;

	/**
	 * <code>PointRotater</code> to get segments to draw from
	 */
	final PointRotator pointRotator;

	/**
	 * If set to <code>false</code> segments with color equal to
	 * {@link Colors#CUBE_COLOR} are not drawn
	 */
	boolean drawCube = true;

	/**
	 * Constructs <code>SegmentDrawer</code> with the specified
	 * <code>{@link PointRotator}</code>
	 * 
	 * @param pointRotater
	 */
	public SegmentDrawer4d(PointRotator pointRotater) {
		this.pointRotator = pointRotater;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		setBackground(Colors.BACKGROUND_COLOR);
		repaint();
	}

	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}

	/**
	 * Returns on-screen <code>X</code> coordinate
	 * 
	 * @param x
	 *            absolute <code>X</code> coordinate
	 * @return on-screen <code>X</code> coordinate
	 */
	double xc(double x) {
		return (int) (originX + x * imageScale);
	}

	/**
	 * Returns on-screen <code>Y</code> coordinate
	 * 
	 * @param y
	 *            absolute <code>Y</code> coordinate
	 * @return on-screen <code>Y</code> coordinate
	 */
	double yc(double y) {
		return (int) (originY - y * imageScale);
	}

	/**
	 * Draws the specified 2d segment on the specified <code>Graphics</code>
	 * 
	 * @param s
	 *            segment to draw
	 * @param g
	 *            <code>Graphics</code> to draw on
	 */
	void drawSegment2d(Segment2d s, Graphics2D g) {
		Color oldColor = g.getColor();
		Stroke oldStroke = g.getStroke();

		g.setColor(s.color);
		g.setStroke(new BasicStroke(LINE_WIDTH));

		Point2d a = s.first;
		Point2d b = s.second;

		double x1 = xc(a.x);
		double y1 = yc(a.y);
		double x2 = xc(b.x);
		double y2 = yc(b.y);

		g.draw(new Line2D.Double(x1, y1, x2, y2));

		g.setColor(oldColor);
		g.setStroke(oldStroke);
	}

	/**
	 * Toggles the {@link #drawCube} flag
	 */
	void toggleCube() {
		drawCube = !drawCube;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		Set<Segment2d> segments = pointRotator.getSegments2d();

		for (Segment2d s : segments) {
			if (s.color == Colors.CUBE_COLOR && !drawCube) {
				continue;
			}
			drawSegment2d(s, g2);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			originX = moveX0 + (e.getX() - moveX1);
			originY = moveY0 + (e.getY() - moveY1);
			repaint();
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			// double deltaX = (e.getX() - RrotX) * rotK;
			// double deltaY = (e.getY() - RrotY) * rotK;
			// RrotX = e.getX();
			// RrotY = e.getY();
			// pointRotator.addAngles(0, 0, deltaY, deltaX, 0);
			// repaint();
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			double deltaX = -(e.getX() - rightRotationX0) * ROTATION_SPEED;
			double deltaY = (e.getY() - rightRotationY0) * ROTATION_SPEED;
			rightRotationX0 = e.getX();
			rightRotationY0 = e.getY();
			pointRotator.addAngles(0, deltaY, 0, 0, deltaX);
			repaint();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			moveX1 = e.getX();
			moveY1 = e.getY();
			moveX0 = originX;
			moveY0 = originY;
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			// RrotX = e.getX();
			// RrotY = e.getY();
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			rightRotationX0 = e.getX();
			rightRotationY0 = e.getY();
		}

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double scrollScale = NORMAL_SCROLL_SPEED;
		if (e.isShiftDown()) {
			scrollScale = FAST_SCROLL_SPEED;
		}
		if (e.isControlDown()) {
			scrollScale = SLOW_SCROLL_SPEED;
		}
		int nmult = (int) (imageScale + scrollScale * e.getWheelRotation());
		if (nmult < 0.001) {
			Toolkit.getDefaultToolkit().beep();
		} else {
			imageScale = nmult;
		}
		repaint();
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