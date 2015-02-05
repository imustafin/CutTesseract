package ru.litsey2.cuttesseract;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import ru.litsey2.cuttesseract.geometry.Geometry;
import ru.litsey2.cuttesseract.geometry.Point2d;
import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment2d;
import ru.litsey2.cuttesseract.geometry.Segment4d;
import ru.litsey2.cuttesseract.geometry.Vector4d;

/**
 * Provides a method to store and rotate segments
 * 
 * @author Ilgiz Mustafin
 *
 */
public class PointRotater {

	/**
	 * Rotated 4d segments
	 */
	Set<Segment4d> segments4d;
	/**
	 * A projection of <code>segments4d</code> to 2d. Contains segments which
	 * are ready to be drawn on the screen
	 */
	Set<Segment2d> segments2d;

	// TODO comment on #angles indexes
	/**
	 * Angles to rotate segments by
	 * 
	 */
	private double[] angles = new double[5];

	/**
	 * Adds axis lines to the {@link #segments4d}
	 */
	private void addCoordVectors() {
		add(new Segment4d(Point4d.ZERO, new Point4d(1, 0, 0, 0), Colors.X_COLOR));
		add(new Segment4d(Point4d.ZERO, new Point4d(0, 1, 0, 0), Colors.Y_COLOR));
		add(new Segment4d(Point4d.ZERO, new Point4d(0, 0, 1, 0), Colors.Z_COLOR));
		add(new Segment4d(Point4d.ZERO, new Point4d(0, 0, 0, 1), Colors.W_COLOR));
	}

	/**
	 * Constructs a PointRotator with only axis segments in it
	 */
	public PointRotater() {
		segments4d = new TreeSet<Segment4d>();
		segments2d = new TreeSet<Segment2d>();
		addCoordVectors();
		recalc();
	}

	/**
	 * Sets new segments to rotate
	 * 
	 * @param segments
	 *            new segments to rotate
	 * @param planeNormal
	 *            new planeNormal, it shouldn't be in the segments
	 */
	void setNewCut(Set<Segment4d> segments, Vector4d planeNormal) {
		segments4d = segments;
		segments4d.add(new Segment4d(Vector4d.ZERO, planeNormal,
				Colors.NORMAL_COLOR));
		addCoordVectors();
		recalc();
	}

	/**
	 * @return plane normal segment
	 */
	Segment4d getNormalSegment() {
		for (Segment4d s : segments4d) {
			if (s.getColor().equals(Colors.NORMAL_COLOR)) {
				return new Segment4d(s);
			}
		}
		return null;
	}

	/**
	 * Rotates the specified point by angles specified in <code>angles[]</code>
	 * 
	 * @param a
	 *            point to rotate
	 * @return rotated copy of the specified point
	 */
	public Point4d getRotated(Point4d a) {
		double x = a.getX();
		double y = a.getY();
		double z = a.getZ();
		double w = a.getW();

		double nx = x;
		double ny = y;
		double nz = z;
		double nw = w;

		nx = x * Math.cos(angles[0]) - y * Math.sin(angles[0]);
		ny = x * Math.sin(angles[0]) + y * Math.cos(angles[0]);

		x = nx;
		y = ny;
		z = nz;
		w = nw;

		ny = y * Math.cos(angles[1]) - z * Math.sin(angles[1]);
		nz = y * Math.sin(angles[1]) + z * Math.cos(angles[1]);

		x = nx;
		y = ny;
		z = nz;
		w = nw;

		nz = z * Math.cos(angles[2]) - w * Math.sin(angles[2]);
		nw = z * Math.sin(angles[2]) + w * Math.cos(angles[2]);

		x = nx;
		y = ny;
		z = nz;
		w = nw;

		nw = w * Math.cos(angles[3]) - x * Math.sin(angles[3]);
		nx = w * Math.sin(angles[3]) + x * Math.cos(angles[3]);

		x = nx;
		y = ny;
		z = nz;
		w = nw;

		nx = x * Math.cos(angles[4]) - z * Math.sin(angles[4]);
		nz = x * Math.sin(angles[4]) + z * Math.cos(angles[4]);

		return new Point4d(nx, ny, nz, nw);
	}

	/**
	 * Adds the specified segment to this <code>PointRotater</code>
	 * 
	 * @param e segment to add
	 */
	void add(Segment4d e) {
		segments4d.add(e);
		recalc();
	}

	/**
	 * Adds all of the segments from the specified <code>Collection</code> to
	 * this <code>PointRotater</code>
	 * 
	 * @param c <code>Collection</code> to add
	 */
	void addAll(Collection<? extends Segment4d> c) {
		segments4d.addAll(c);
		recalc();
	}

	/**
	 * Recalculates {@link #segments2d} and {@link #segments4d}
	 */
	void recalc() {
		segments2d.clear();
		Set<Segment4d> nSegments4d = new TreeSet<Segment4d>();
		for (Segment4d s : segments4d) {
			Point4d a4 = getRotated(s.getA());
			Point4d b4 = getRotated(s.getB());
			nSegments4d.add(new Segment4d(a4, b4, s.getColor()));
			Point2d a2 = new Point2d(a4.getX(), a4.getY());
			Point2d b2 = new Point2d(b4.getX(), b4.getY());
			segments2d.add(new Segment2d(a2, b2, s.getColor()));
		}
		segments4d = nSegments4d;
		for (int i = 0; i < angles.length; i++) {
			angles[i] = 0;
		}
	}

	public Set<Segment2d> getSegments2d() {
		return new TreeSet<Segment2d>(segments2d);
	}

	/**
	 * Adds the specified angles and recalculates new positions
	 * @param r0 XY angle
	 * @param r1 YZ angle
	 * @param r2 ZW angle
	 * @param r3 WX angle
	 * @param r4 XZ angle
	 */
	public void addAngles(double r0, double r1, double r2, double r3, double r4) {
		angles[0] += r0;
		angles[1] += r1;
		angles[2] += r2;
		angles[3] += r3;
		angles[4] += r4;
		recalc();
	}

	/**
	 * Rotates all segments so that plane normal points to us
	 */
	void rotateNormalToUs() {
		Vector4d n = new Vector4d(getNormalSegment()).getNormalized();
		// x!=0,y=0   =>  a0=90
		if (Geometry.compareEps(0, n.getX()) != 0
				&& Geometry.compareEps(0, n.getY()) == 0) {
			addAngles(Math.PI / 2, 0, 0, 0, 0);
		}
		n = new Vector4d(getNormalSegment()).getNormalized();
		if (Geometry.compareEps(0, n.getY()) != 0) {
			double a0 = Math.atan(n.getX() / n.getY());
			addAngles(a0, 0, 0, 0, 0);
		}

		n = new Vector4d(getNormalSegment()).getNormalized();
		if (Geometry.compareEps(0, n.getY()) != 0
				&& Geometry.compareEps(0, n.getZ()) == 0) {
			addAngles(0, Math.PI / 2, 0, 0, 0);
		}
		n = new Vector4d(getNormalSegment()).getNormalized();
		if (Geometry.compareEps(0, n.getZ()) != 0) {
			double a1 = Math.atan(n.getY() / n.getZ());
			addAngles(0, a1, 0, 0, 0);
		}

		n = new Vector4d(getNormalSegment()).getNormalized();
		if (Geometry.compareEps(0, n.getZ()) != 0
				&& Geometry.compareEps(0, n.getW()) == 0) {
			addAngles(0, 0, Math.PI / 2, 0, 0);
		}
		n = new Vector4d(getNormalSegment()).getNormalized();
		if (Geometry.compareEps(0, n.getW()) != 0) {
			double a2 = Math.atan(n.getZ() / n.getW());
			addAngles(0, 0, a2, 0, 0);
		}
	}

}
