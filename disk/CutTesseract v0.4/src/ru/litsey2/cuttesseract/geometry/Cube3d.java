package ru.litsey2.cuttesseract.geometry;

public class Cube3d {

	private final Point3d[] vertices;
	/**
	 * If <code>hasEdges[i][j]</code> is true, then there is an edge between
	 * i<sup>th</sup> and j<sup>th</sup> vertex
	 */
	private final boolean[][] hasEdge;

	public Cube3d(Point3d[] vertices, boolean[][] hasEdge) {
		this.vertices = vertices;
		this.hasEdge = hasEdge;
	}

	/**
	 * @return copy of {@link #vertices}
	 */
	public Point3d[] getVertices() {
		return vertices.clone();
	}

	/**
	 * @return copy of {@link #hasEdge}
	 */
	public boolean[][] getHasEdge() {
		return hasEdge.clone();
	}
}