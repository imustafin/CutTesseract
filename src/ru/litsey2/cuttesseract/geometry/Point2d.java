package ru.litsey2.cuttesseract.geometry;


public class Point2d implements Comparable<Point2d> {
	private final double x;
	private final double y;

	public Point2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Point2d b) {
		if (Geometry.compareEps(getX(), b.getX()) == 0) {
			if (Geometry.compareEps(getY(), b.getY()) == 0) {
				return 0;
			} else {
				return Double.compare(getY(), b.getY());
			}
		} else {
			return Double.compare(getX(), b.getX());
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}