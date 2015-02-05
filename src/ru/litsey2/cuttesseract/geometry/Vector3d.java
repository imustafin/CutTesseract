package ru.litsey2.cuttesseract.geometry;

public class Vector3d extends Point3d {

	/**
	 * Constructs vector by subtracting <code>begin</code> coordinates from the
	 * <code>end</code> coordinates
	 * 
	 * @param begin
	 * @param end
	 */
	Vector3d(Point3d begin, Point3d end) {
		this(end.x - begin.x, end.y - begin.y, end.z - begin.z);
	}

	/**
	 * Constructs vector with the specified point's coordinates 
	 * @param p the specified point
	 */
	Vector3d(Point3d p) {
		this(p.x, p.y, p.z);
	}

	/**
	 * Constructs vector with the specified coordinates
	 * @param x the specified <code>x</code> coordinate
	 * @param y the specified <code>y</code> coordinate
	 * @param z the specified <code>z</code> coordinate
	 */
	public Vector3d(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Returns new vector which is a result of multiplication of this vector by a scalar
	 * @param k scalar to multiply by
	 * @return result of multiplication of this vector by scalar
	 */
	Vector3d multiply(double k) {
		return new Vector3d(x * k, y * k, z * k);
	}

	/**
	 * @return the length of this vector 
	 */
	double length() {
		return ZERO.distance(this);
	}

	@Override
	public String toString() {
		return "{" + x + ", " + y + ", " + z + "}";
	}

}