package ru.litsey2.cuttesseract.geometry;

import java.awt.Color;

public class Segment2d implements Comparable<Segment2d> {
	private final Point2d a;
	private final Point2d b;
	private final Color color;

	public Segment2d(Point2d a, Point2d b, Color color) {
		if (a.compareTo(b) < 0) {
			this.a = a;
			this.b = b;
		} else {
			this.a = b;
			this.b = a;
		}
		this.color = color;
	}

	@Override
	public int compareTo(Segment2d z) {
		if (a.compareTo(z.a) == 0) {
			if (b.compareTo(z.b) == 0) {
				return Integer.compare(this.color.getRGB(), z.color.getRGB());
			} else {
				return b.compareTo(z.b);
			}
		} else {
			return a.compareTo(z.a);
		}
	}
	
	public Segment2d(Point2d a, Point2d b) {
		this(a, b, Color.BLACK);
	}

	public Color getColor() {
		return color;
	}

	public Point2d getA() {
		return a;
	}

	public Point2d getB() {
		return b;
	}

}