package ru.litsey2.cuttesseract.geometry;

public class Vector2d  extends Point2d {

	public Vector2d(double x, double y) {
		super(x, y);
	}
	
	public Vector2d(Point2d p, Point2d q) {
		super(q.x - p.x, q.y - p.y);
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y);
	}
	
}
