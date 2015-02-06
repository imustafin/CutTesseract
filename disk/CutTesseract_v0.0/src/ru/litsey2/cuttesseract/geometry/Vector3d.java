package ru.litsey2.cuttesseract.geometry;

public class Vector3d extends Point3d {

	Vector3d(Point3d a, Point3d b) {
		super(b.x - a.x, b.y - a.y, b.z - a.z);
	}

	Vector3d(Point3d p) {
		super(p);
	}

	public Vector3d(double x, double y, double z) {
		super(x, y, z);
	}

	Vector3d multiply(double k) {
		return new Vector3d(x * k, y * k, z * k);
	}

	double length() {
		return ZERO.distance(this);
	}

	@Override
	public String toString() {
		return "{" + x + ", " + y + ", " + z + "}";
	}

}