package ru.litsey2.cuttesseract.geometry;

public class Cube3d {

	final Point3d[] vertices;
	/**
	 * If <code>hasEdges[i][j]</code> is true, then there is an edge between
	 * i<sup>th</sup> and j<sup>th</sup> vertex
	 */
	final boolean[][] hasEdge;

	public Cube3d(Point3d[] vertices, boolean[][] hasEdge) {
		this.vertices = vertices;
		this.hasEdge = hasEdge;
	}
}