package ru.litsey2.cuttesseract.geometry;

import java.awt.Color;

public class Segment2d {
	protected final Point2d a;
	protected final Point2d b;

	Color color;

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

	public Segment2d(Point2d a, Point2d b) {
		this(a, b, Color.BLACK);
	}

}