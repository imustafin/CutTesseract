package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

import ru.litsey2.cuttesseract.geometry.Point2d;

import com.sun.corba.se.impl.interceptors.PICurrent;

@SuppressWarnings("serial")
public class PointSelector extends JPanel implements MouseMotionListener,
		MouseListener {

	AxisBox axis1;
	AxisBox axis2;
	JPanel control;

	class AxisBox extends JPanel implements MouseInputListener {

		private Color colorX;
		private Color colorY;

		private int x;
		private int y;
		private final int x0;
		private final int y0;
		private double x1 = 0;
		private double y1 = 0;

		JTextField textX;
		JTextField textY;

		AxisBox(Color colorX, Color colorY, JTextField textX, JTextField textY) {
			setPreferredSize(new Dimension(100, 100));

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
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			x = e.getX();
			y = e.getY();
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
		public void mouseDragged(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}

	public PointSelector(Color bgcolor) {
		setBackground(bgcolor);

		setPreferredSize(new Dimension(300, 150));
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);

		JFormattedTextField textX = new JFormattedTextField(decimalFormat);
		textX.setColumns(5);

		JFormattedTextField textY = new JFormattedTextField(decimalFormat);
		textY.setColumns(5);

		JFormattedTextField textZ = new JFormattedTextField(decimalFormat);
		textZ.setColumns(5);

		JFormattedTextField textW = new JFormattedTextField(decimalFormat);
		textW.setColumns(5);

		axis1 = new AxisBox(Colors.X_COLOR, Colors.Y_COLOR, textX, textY);
		axis2 = new AxisBox(Colors.Z_COLOR, Colors.W_COLOR, textZ, textW);

		control = new JPanel();

		GroupLayout layout = new GroupLayout(this);

		control.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		JButton buttonSet = new JButton("Set");

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(textX).addComponent(textY).addComponent(textZ)
				.addComponent(textW).addComponent(buttonSet).addComponent(axis1).addComponent(axis2));


	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
