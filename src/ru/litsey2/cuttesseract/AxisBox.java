package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

/**
 * A <code>JPanel</code> with a <code>MouseInputListener</code>. Provides
 * ability to graphically pick a point in a two dimensional spaыce.
 *
 * @author Ilgiz Mustafin
 */
abstract class AxisBox extends JPanel implements MouseInputListener {

	private static final long serialVersionUID = 1L;
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
	 * The <i>X</i> coordinate of the origin
	 * 
	 */
	private final int originX;
	/**
	 * The <i>Y</i> coordinate of the origin
	 */
	private final int originY;

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
	double absoluteX = 0;
	/**
	 * The real <i>Y</i> coordinate of the currently selected point. Relatively
	 * to the center of the <code>AxisBox</code> and properly scaled
	 */
	double absoluteY = 0;

	/**
	 * A <code>JTextField</code> to put the value of <code>{@link #absoluteX}</code> to
	 */
	JTextField textX;
	/**
	 * A <code>JTextField</code> to put the value of <code>{@link #absoluteY}</code> to
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

		originX = getPreferredSize().width / 2;
		originY = getPreferredSize().height / 2;

		x = originX;
		y = originY;

		absoluteX = 0;
		absoluteY = 0;

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
		absoluteX = x2;
		x = (int) (absoluteX * originX + originX);
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
		absoluteY = -y2;
		y = (int) (absoluteY * originY + originY);
		repaint();
		onCoordChanged();
	}

	/**
	 * Puts the real coordinates to the corresponding text frames
	 */
	void updateTexts() {
		absoluteX = (double) (x - originX) / originX;
		absoluteY = (double) (y - originY) / -originY;
		textX.setText(Double.toString(absoluteX));
		textY.setText(Double.toString(absoluteY));

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