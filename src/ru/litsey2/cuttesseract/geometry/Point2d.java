package ru.litsey2.cuttesseract.geometry;


public class Point2d implements Comparable<Point2d> {
	protected final double x;
	protected final double y;

	public Point2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Point2d b) {
		if (Geometry.compareEps(x, b.x) == 0) {
			if (Geometry.compareEps(y, b.y) == 0) {
				return 0;
			} else {
				return Double.compare(y, b.y);
			}
		} else {
			return Double.compare(x, b.x);
		}
	}

}