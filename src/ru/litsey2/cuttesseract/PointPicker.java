package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

import ru.litsey2.cuttesseract.geometry.Cube4d;
import ru.litsey2.cuttesseract.geometry.Geometry;
import ru.litsey2.cuttesseract.geometry.Plane4d;
import ru.litsey2.cuttesseract.geometry.Point2d;
import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;
import ru.litsey2.cuttesseract.geometry.Vector4d;

import com.sun.corba.se.impl.interceptors.PICurrent;

@SuppressWarnings("serial")
public class PointPicker extends JPanel implements MouseMotionListener,
		MouseListener {

	AxisBox axis1;
	AxisBox axis2;
	JPanel control;

	SegmentDrawer4d segmentDrawer;

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

			updateTexts();

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
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			updateTexts();
			repaint();
			drawCut();
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
			updateTexts();
			repaint();
			drawCut();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}

	Point4d getPoint4d() {
		return new Point4d(axis1.x1, axis1.y1, axis2.x1, axis2.y1);
	}

	void drawCut() {
		Cube4d cube = new Cube4d(1, Colors.CUBE_COLOR);
		Plane4d plane = new Plane4d(Point4d.ZERO, new Vector4d(getPoint4d()));
		Set<Segment4d> set = Geometry.makeCut(plane, cube);
		set.addAll(cube.getSegments());
		set.add(new Segment4d(Point4d.ZERO, plane.getNormal(),
				Colors.NORMAL_COLOR));


		set.add(new Segment4d(Point4d.ZERO, new Point4d(1, 0, 0, 0),
				Colors.X_COLOR));
		set.add(new Segment4d(Point4d.ZERO, new Point4d(0, 1, 0, 0),
				Colors.Y_COLOR));
		set.add(new Segment4d(Point4d.ZERO, new Point4d(0, 0, 1, 0),
				Colors.Z_COLOR));
		set.add(new Segment4d(Point4d.ZERO, new Point4d(0, 0, 0, 1),
				Colors.W_COLOR));

		segmentDrawer.pointRotator.segments4d = set;
		segmentDrawer.pointRotator.rotateNormalToUs();
		segmentDrawer.repaint();
	}

	public PointPicker(Color bgcolor, SegmentDrawer4d segmentDrawer) {
		setBackground(bgcolor);

		this.segmentDrawer = segmentDrawer;

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

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(textX).addComponent(textY).addComponent(textZ)
				.addComponent(textW).addComponent(axis1).addComponent(axis2));

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
