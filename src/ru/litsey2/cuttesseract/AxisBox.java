package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

/**
 * Provides a <code>JPanel</code> with a <code>MouseInputListener</code>. Lets
 * us to select a point in a <i>2d</i> space.
 *
 * @author Ilgiz Mustafin
 */
@SuppressWarnings("serial")
abstract class AxisBox extends JPanel implements MouseInputListener {

	/**
	 * Size of of all <code>AxisBoxes</code>
	 */
	static final int SIZE = 100;
	/**
	 * The color of the selection cursor
	 */
	public static final Color POINT_COLOR = Color.WHITE;

	/**
	 * The color of the <i>X</i>-axis line
	 */
	private final Color colorX;
	/**
	 * The color of the <i>Y</i>-axis line
	 */
	private final Color colorY;

	/**
	 * The <i>X</i> coordinate of the center of the <code>AxisBox</code>
	 */
	private final int x0;
	/**
	 * The <i>Y</i> coordinate of the center of the <code>AxisBox</code>
	 */
	private final int y0;

	/**
	 * The in-box <i>X</i> coordinate of the currently selected point.
	 * relatively to the <code>AxisBox</code>
	 */
	private int x;
	/**
	 * The in-box <i>Y</i> coordinate of the currently selected point.
	 * Relatively to the <code>AxisBox</code>
	 */
	private int y;

	/**
	 * The real <i>X</i> coordinate of the currently selected point. Relatively
	 * to the center of the <code>AxisBox</code> and properly scaled
	 */
	double x1 = 0;
	/**
	 * The real <i>Y</i> coordinate of the currently selected point. Relatively
	 * to the center of the <code>AxisBox</code> and properly scaled
	 */
	double y1 = 0;

	/**
	 * A <code>JTextField</code> to put the value of <code>{@link #x1}</code> to
	 */
	JTextField textX;
	/**
	 * A <code>JTextField</code> to put the value of <code>{@link #y1}</code> to
	 */
	JTextField textY;

	/**
	 * A parent <code>{@link PointPicker}</code>
	 */
	PointPicker pointPicker;

	/**
	 * Executed on any <code>x1</code> or <code>y1</code> change
	 */
	abstract void onCoordChanged();

	/**
	 * @param pointPicker
	 *            parent <code>PointPicker</code>
	 * @param colorX
	 *            color of the <i>X</i>-axis line
	 * @param colorY
	 *            color of the <i>Y</i>-axis line
	 * @param textX
	 *            text field to put the <i>X</i> coordinate to
	 * @param textY
	 *            text field to put the <i>X</i> coordinate to
	 */
	AxisBox(PointPicker pointPicker, Color colorX, Color colorY,
			JTextField textX, JTextField textY) {
		setMinimumSize(new Dimension(SIZE, SIZE));
		setPreferredSize(new Dimension(SIZE, SIZE));
		setMaximumSize(new Dimension(SIZE, SIZE));

		this.pointPicker = pointPicker;

		setBackground(Colors.BACKGROUND_COLOR);

		this.colorX = colorX;
		this.colorY = colorY;

		this.textX = textX;
		this.textY = textY;

		addMouseListener(this);
		addMouseMotionListener(this);

		x0 = getPreferredSize().width / 2;
		y0 = getPreferredSize().height / 2;

		x = x0;
		y = y0;

		x1 = 0;
		y1 = 0;

		updateTexts();
	}

	/**
	 * Sets the real <i>X</i> coordinate, updates the in-box coordinates
	 * accordingly
	 * 
	 * @param x2
	 *            new real <i>X</i> value
	 */
	void setX(double x2) {
		x1 = x2;
		x = (int) (x1 * x0 + x0);
		repaint();
		onCoordChanged();
	}

	/**
	 * Sets the real <i>Y</i> coordinate, updates the in-box coordinates
	 * accordingly
	 * 
	 * @param y2
	 *            new real <i>Y</i> value
	 */
	void setY(double y2) {
		y1 = -y2;
		y = (int) (y1 * y0 + y0);
		repaint();
		onCoordChanged();
	}

	/**
	 * Puts the real coordinates to the corresponding text frames
	 */
	void updateTexts() {
		x1 = (double) (x - x0) / x0;
		y1 = (double) (y - y0) / -y0;
		textX.setText(Double.toString(x1));
		textY.setText(Double.toString(y1));

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Color oldColor = g.getColor();

		g.setColor(colorX);
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);

		g.setColor(colorY);
		g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

		g.setColor(POINT_COLOR);
		int r = 5;
		g.fillOval(x - r, y - r, 2 * r, 2 * r);

		g.setColor(oldColor);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		updateTexts();
		repaint();
		onCoordChanged();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		updateTexts();
		repaint();
		onCoordChanged();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
}