package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

//*@SuppressWarnings("serial")
abstract class AxisBox extends JPanel implements MouseInputListener {

	private static final long serialVersionUID = 1L;
	
	static final int SIZE = 100;

	private Color colorX;
	private Color colorY;

	private int x;
	private int y;
	private final int x0;
	private final int y0;
	double x1 = 0;
	double y1 = 0;

	JTextField textX;
	JTextField textY;

	PointPicker pointPicker;

	abstract void coordChanged();
	
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

	void setX(double x2) {
		x1 = x2;
		x = (int) (x1 * x0 + x0);
		repaint();
		coordChanged();
	}

	void setY(double y2) {
		y1 = -y2;
		y = (int) (y1 * y0 + y0);
		repaint();
		coordChanged();
	}

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

		g.setColor(Color.WHITE);
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
		coordChanged();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		updateTexts();
		repaint();
		coordChanged();
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