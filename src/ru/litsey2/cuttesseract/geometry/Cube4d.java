package ru.litsey2.cuttesseract.geometry;

import java.awt.Color;
import java.util.Set;
import java.util.TreeSet;

public class Cube4d {

	private final Point4d[] vertices;
	private final boolean[][] hasEdge;
	private final Set<Segment4d> segments;

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
				if (a.getX() == b.getX()) {
					sameness++;
				}
				if (a.getY() == b.getY()) {
					sameness++;
				}
				if (a.getZ() == b.getZ()) {
					sameness++;
				}
				if (a.getW() == b.getW()) {
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

	public Set<Segment4d> getSegments() {
		return segments;
	}

	public boolean[][] getHasEdge() {
		return hasEdge;
	}

	public Point4d[] getVertices() {
		return vertices;
	}

}