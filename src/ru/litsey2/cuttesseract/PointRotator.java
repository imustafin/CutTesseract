package ru.litsey2.cuttesseract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.litsey2.cuttesseract.geometry.Cube4d;
import ru.litsey2.cuttesseract.geometry.Formulas;
import ru.litsey2.cuttesseract.geometry.Geometry;
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
public class PointRotator {

	/**
	 * Rotated 4d segments
	 */
	ArrayList<Segment4d> segments4d;
	/**
	 * A projection of <code>segments4d</code> to 2d. Contains segments which
	 * are ready to be drawn on the screen
	 */
	ArrayList<Segment2d> segments2d;

	/**
	 * Angles to rotate all point by
	 * <ul>
	 * <li><code>[0]</code>: XY angle
	 * <li><code>[1]</code>: YZ angle
	 * <li><code>[2]</code>: ZW angle
	 * <li><code>[3]</code>: WX angle
	 * <li><code>[4]</code>: XZ angle
	 * </ul>
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
	public PointRotator(Cube4d cube) {
		segments4d = new ArrayList<Segment4d>();
		segments2d = new ArrayList<Segment2d>();
		addCoordVectors();
		addAll(cube.getSegments());
		recalc(true, null);
	}

	/**
	 * Sets new segments to rotate
	 * 
	 * @param segments
	 *            new segments to rotate
	 * @param planeNormal
	 *            new planeNormal, it shouldn't be in the segments
	 */
	void setNewSection(ArrayList<Segment4d> segments, Vector4d planeNormal,
			Cube4d cube, boolean rotateToUs) {

		Vector4d[] oldVectors = getCoordVectors();

		segments4d = segments;
		segments4d.add(new Segment4d(Vector4d.ZERO, planeNormal,
				Colors.NORMAL_COLOR));
		addAll(cube.getSegments());
		if (rotateToUs) {
			rotateNormalToUs();
		}
		addCoordVectors();
		if (oldVectors != null) {
			recalc(false, oldVectors);
		}
		recalc(true, null);
	}

	/**
	 * @return plane normal segment
	 */
	Segment4d getNormalSegment() {
		for (Segment4d s : segments4d) {
			if (s.color.equals(Colors.NORMAL_COLOR)) {
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
	public Point4d getRotatedByAngles(Point4d a) {
		double x = a.x;
		double y = a.y;
		double z = a.z;
		double w = a.w;

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

	public Point4d getRotatedByVectors(Point4d p, Vector4d[] coordVectors) {
		Vector4d[] v = coordVectors;

		double ox = p.x;
		double oy = p.y;
		double oz = p.z;
		double ow = p.w;

		double x = p.x;
		double y = p.y;
		double z = p.z;
		double w = p.w;

		x = ox * v[0].x + oy * v[1].x + oz * v[2].x + ow * v[3].x;
		y = ox * v[0].y + oy * v[1].y + oz * v[2].y + ow * v[3].y;
		z = ox * v[0].z + oy * v[1].z + oz * v[2].z + ow * v[3].z;
		w = ox * v[0].w + oy * v[1].w + oz * v[2].w + ow * v[3].w;

		return new Point4d(x, y, z, w);

	}

	Vector4d[] getCoordVectors() {
		Vector4d[] vectors = new Vector4d[4];
		int num = 0;
		for (Segment4d s : segments4d) {
			if (s.color.equals(Colors.X_COLOR)) {
				vectors[0] = new Vector4d(s);
				num++;
			}
			if (s.color.equals(Colors.Y_COLOR)) {
				vectors[1] = new Vector4d(s);
				num++;
			}
			if (s.color.equals(Colors.Z_COLOR)) {
				vectors[2] = new Vector4d(s);
				num++;
			}
			if (s.color.equals(Colors.W_COLOR)) {
				vectors[3] = new Vector4d(s);
				num++;
			}
		}
		if (num != 4) {
			return null;
		}
		return vectors;
	}

	/**
	 * Adds the specified segment to this <code>PointRotator</code>
	 * 
	 * @param e
	 *            segment to add
	 */
	void add(Segment4d e) {
		segments4d.add(e);
		recalc(true, null);
	}

	/**
	 * Adds all of the segments from the specified <code>Collection</code> to
	 * this <code>PointRotator</code>
	 * 
	 * @param c
	 *            <code>Collection</code> to add
	 */
	void addAll(Collection<? extends Segment4d> c) {
		segments4d.addAll(c);
		recalc(true, null);
	}

	void recalc(boolean byAngles, Vector4d[] coordVectors) {
		segments2d.clear();
		ArrayList<Segment4d> nSegments4d = new ArrayList<Segment4d>();
		for (Segment4d s : segments4d) {
			Point4d a4 = byAngles ? getRotatedByAngles(s.a)
					: getRotatedByVectors(s.a, coordVectors);
			Point4d b4 = byAngles ? getRotatedByAngles(s.b)
					: getRotatedByVectors(s.b, coordVectors);
			nSegments4d.add(new Segment4d(a4, b4, s.color));
		}
		segments4d = nSegments4d;
		drawOrderSort(segments4d);
		for (Segment4d s : segments4d) {
			segments2d.add(s.projection2d());
		}
		for (int i = 0; i < angles.length; i++) {
			angles[i] = 0;
		}
	}

	/**
	 * 
	 * @return a copy of {@link #segments2d}
	 */
	public ArrayList<Segment2d> getSegments2d() {

		return new ArrayList<Segment2d>(segments2d);
	}

	/**
	 * Adds the specified angles and recalculates new positions
	 * 
	 * @param r0
	 *            XY angle
	 * @param r1
	 *            YZ angle
	 * @param r2
	 *            ZW angle
	 * @param r3
	 *            WX angle
	 * @param r4
	 *            XZ angle
	 * 
	 * @see #angles
	 */
	public void addAngles(double r0, double r1, double r2, double r3, double r4) {
		angles[0] += r0;
		angles[1] += r1;
		angles[2] += r2;
		angles[3] += r3;
		angles[4] += r4;
		recalc(true, null);
	}

	/**
	 * Rotates all segments so that plane normal points to us
	 */
	void rotateNormalToUs() {
		Vector4d n = new Vector4d(getNormalSegment()).getNormalized();
		// x!=0,y=0   =>  a0=90
		if (Geometry.compareEps(0, n.x) != 0
				&& Geometry.compareEps(0, n.y) == 0) {
			addAngles(Math.PI / 2, 0, 0, 0, 0);
		}
		n = new Vector4d(getNormalSegment()).getNormalized();
		if (Geometry.compareEps(0, n.y) != 0) {
			double a0 = Math.atan(n.x / n.y);
			addAngles(a0, 0, 0, 0, 0);
		}

		n = new Vector4d(getNormalSegment()).getNormalized();
		if (Geometry.compareEps(0, n.y) != 0
				&& Geometry.compareEps(0, n.z) == 0) {
			addAngles(0, Math.PI / 2, 0, 0, 0);
		}
		n = new Vector4d(getNormalSegment()).getNormalized();
		if (Geometry.compareEps(0, n.z) != 0) {
			double a1 = Math.atan(n.y / n.z);
			addAngles(0, a1, 0, 0, 0);
		}

		n = new Vector4d(getNormalSegment()).getNormalized();
		if (Geometry.compareEps(0, n.z) != 0
				&& Geometry.compareEps(0, n.w) == 0) {
			addAngles(0, 0, Math.PI / 2, 0, 0);
		}
		n = new Vector4d(getNormalSegment()).getNormalized();
		if (Geometry.compareEps(0, n.w) != 0) {
			double a2 = Math.atan(n.z / n.w);
			addAngles(0, 0, a2, 0, 0);
		}
	}

	static final int MAX_ITERATIONS_HACK =  6;
	
	public static void drawOrderSort(ArrayList<Segment4d> edges) {
	
		//TODO: we really need to use TopSort instead of random shuffle

		int iterations = 0;
		
		Collections.shuffle(edges);
		
		boolean swapped = true;

		while (swapped && iterations < MAX_ITERATIONS_HACK) {
			swapped = false;
			iterations++;
			for (int i = 0; i < edges.size(); i++) {
				for (int j = i + 1; j < edges.size(); j++) {
					if (i == j) {
						continue;
					}
					Segment4d a = edges.get(i);
					Segment4d b = edges.get(j);

					int cmp = Formulas.intersection2dComparator.compare(a, b);

					if (cmp > 0) {
						swapped = true;
						Collections.swap(edges, i, j);
					}
				}
			}
		}
	}
}
