package ru.litsey2.cuttesseract.geometry;


public class Vector4d extends Point4d {

	/**
	 * Constructs vector by subtracting <code>begin</code> coordinates from the
	 * <code>end</code> coordinates
	 * 
	 * @param begin
	 * @param end
	 */
	public Vector4d(Point4d begin, Point4d end) {
		super(end.x - begin.x, end.y - begin.y, end.z - begin.z, end.w - begin.w);
	}

	/**
	 * Constructs vector with the specified point's coordinates 
	 * @param p the specified point
	 */
	public Vector4d(Point4d p) {
		super(p);
	}
	
	/**
	 * Constructs vector by subtracting segment's <code>first</code> coordinates from the
	 * segments <code>second</code> coordinates
	 * 
	 * @param s segment
	 */
	public Vector4d(Segment4d s) {
		this(s.a, s.b);
	}

	/**
	 * Constructs vector with the specified coordinates
	 * @param x the specified <code>x</code> coordinate
	 * @param y the specified <code>y</code> coordinate
	 * @param z the specified <code>z</code> coordinate
	 * @param w the specified <code>w</code> coordinate
	 */
	public Vector4d(double x, double y, double z, double w) {
		super(x, y, z, w);
	}

	/**
	 * @return the length of this vector
	 */
	public double length() {
		return Point4d.ZERO.distance(this);
	}

	/**
	 * @return the copy of this vector with length equal to 1
	 */
	public Vector4d getNormalized() {
		double l = this.length();
		Vector4d ans = new Vector4d(x / l, y / l, z / l, w / l);
		return ans;
	}
	
}