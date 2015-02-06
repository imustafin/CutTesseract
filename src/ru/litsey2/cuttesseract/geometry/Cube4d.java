package ru.litsey2.cuttesseract.geometry;

import java.awt.Color;
import java.util.Set;
import java.util.TreeSet;

public class Cube4d {

	private final Point4d[] vertices;
	/**
	 * if <code>hasEdges[i][j]</code> is true, then there is an edge between
	 * i<sup>th</sup> and j<sup>th</sup> vertex
	 */
	public final boolean[][] hasEdge;
	/**
	 * Set of all edges as <code>Segment4d</code>
	 */
	public final Set<Segment4d> segments;

	/**
	 * Constructs <code>Cube4d</code> with coordinates
	 * <code>(-x, -x, -x, -x)</code> through <code>(x, x, x, x)</code> and
	 * specified color
	 * 
	 * @param coord cube vertex coordinate
	 * @param edgeColor color of <code>{@link #segments}</code>
	 */
	public Cube4d(double coord, Color edgeColor) {
		Point4d[] v = { new Point4d(-coord, -coord, -coord, -coord),
				new Point4d(-coord, -coord, -coord, coord),
				new Point4d(-coord, -coord, coord, -coord),
				new Point4d(-coord, -coord, coord, coord),
				new Point4d(-coord, coord, -coord, -coord),
				new Point4d(-coord, coord, -coord, coord),
				new Point4d(-coord, coord, coord, -coord),
				new Point4d(-coord, coord, coord, coord),
				new Point4d(coord, -coord, -coord, -coord),
				new Point4d(coord, -coord, -coord, coord),
				new Point4d(coord, -coord, coord, -coord),
				new Point4d(coord, -coord, coord, coord),
				new Point4d(coord, coord, -coord, -coord),
				new Point4d(coord, coord, -coord, coord),
				new Point4d(coord, coord, coord, -coord),
				new Point4d(coord, coord, coord, coord) };
		vertices = v;
		segments = new TreeSet<Segment4d>();
		hasEdge = new boolean[16][16];
		for (int i = 0; i < getVertices().length; i++) {
			for (int j = i + 1; j < getVertices().length; j++) {
				Point4d a = getVertices()[i];
				Point4d b = getVertices()[j];
				int sameness = 0;
				if (a.x== b.x) {
					sameness++;
				}
				if (a.y == b.y) {
					sameness++;
				}
				if (a.z == b.z) {
					sameness++;
				}
				if (a.w == b.w) {
					sameness++;
				}
				if (sameness == 3) {
					getHasEdge()[i][j] = true;
					getHasEdge()[j][i] = true;
					getSegments().add(new Segment4d(a, b, edgeColor));
				} else {
					getHasEdge()[i][j] = false;
					getHasEdge()[j][i] = false;
				}
			}
		}
	}

	/**
	 * @return a copy of {@link #segments}
	 */
	public Set<Segment4d> getSegments() {
		return new TreeSet<Segment4d>(segments);
	}

	/**
	 * 
	 * @return a copy of {@link #hasEdge}
	 */
	public boolean[][] getHasEdge() {
		return hasEdge.clone();
	}

	/**
	 * 
	 * @return a copy of {@link #vertices}
	 */
	public Point4d[] getVertices() {
		return vertices.clone();
	}

}