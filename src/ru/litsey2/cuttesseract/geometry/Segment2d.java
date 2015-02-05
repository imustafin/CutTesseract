package ru.litsey2.cuttesseract.geometry;

import java.awt.Color;

public class Segment2d implements Comparable<Segment2d> {
	/**
	 * The first point of the segment. It is not greater than the second point.
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	private final Point2d first;
	/**
	 * The second point of the segment. It is not less than the first point.
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	private final Point2d second;
	/**
	 * The color of the segment
	 */
	private final Color color;

	/**
	 * Constructs segment with the specified two points and color. The second
	 * point should not be less than the first point if it is not so then they
	 * are swapped.
	 * 
	 * @param a
	 *            first point
	 * @param b
	 *            second point
	 * @param color
	 *            color of segment
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	public Segment2d(Point2d a, Point2d b, Color color) {
		if (a.compareTo(b) <= 0) {
			this.first = a;
			this.second = b;
		} else {
			this.first = b;
			this.second = a;
		}
		this.color = color;
	}

	/**
	 * Constructs segment with the specified two points only. The color is
	 * considered to be {@link Color#BLACK} The second point should not be less
	 * than the first point if it is not so then they are swapped.
	 * 
	 * @param a
	 *            first point
	 * @param b
	 *            second point
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	public Segment2d(Point2d a, Point2d b) {
		this(a, b, Color.BLACK);
	}
	
	/**
	 * Compares this segment to the other segment.
	 * <p>
	 * Segments are first compared by the <code>first</code> point then by the
	 * <code>second</code> point then by the color {@link Color#getRGB()}
	 * 
	 * @param z the segment to compare
	 */
	@Override
	public int compareTo(Segment2d z) {
		if (first.compareTo(z.first) == 0) {
			if (second.compareTo(z.second) == 0) {
				return Integer.compare(this.color.getRGB(), z.color.getRGB());
			} else {
				return second.compareTo(z.second);
			}
		} else {
			return first.compareTo(z.first);
		}
	}

	public Color getColor() {
		return color;
	}

	public Point2d getFirst() {
		return first;
	}

	public Point2d getSecond() {
		return second;
	}

}