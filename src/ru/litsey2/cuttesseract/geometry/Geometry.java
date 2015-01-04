package ru.litsey2.cuttesseract.geometry;

public class Geometry {

	public static final double EPS = 1E-6;

	public static int compareEps(double a, double b) {
		if (Math.abs(a - b) <= EPS) {
			return 0;
		}
		if (a < b) {
			return -1;
		} else {
			return 1;
		}
	}

}
