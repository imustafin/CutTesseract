package ru.litsey2.work;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.text.StyleContext.SmallAttributeSet;

import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;

public class Graph {

	boolean[][] e;
	Set<Segment4d> segments;

	int[] deg;

	int n;
	int m;

	String hash;

	public Graph(Collection<Segment4d> segments) {
		this.segments = new TreeSet<Segment4d>();
		this.segments.addAll(segments);
		TreeMap<Point4d, Integer> map = new TreeMap<Point4d, Integer>();
		n = 0;
		m = 0;
		for (Segment4d s : segments) {
			Point4d a = s.getA();
			if (!map.containsKey(a)) {
				map.put(a, n);
				n++;
			}
			Point4d b = s.getB();
			if (!map.containsKey(b)) {
				map.put(b, n);
				n++;
			}
		}

		deg = new int[n];
		e = new boolean[n][n];

		for (Segment4d s : segments) {
			Point4d a = s.getA();
			Point4d b = s.getB();
			int u = map.get(a);
			int v = map.get(b);
			if (e[u][v]) {
				continue;
			}
			e[u][v] = true;
			e[v][u] = true;
			deg[u]++;
			deg[v]++;
			m++;
		}
		Arrays.sort(deg);
	}

	public String hash() {
		int[] rep = new int[n];
		for (int i = 0; i < n; i++) {
			rep[i] = i;
		}
		int[] min = new int[n * (n - 1) / 2];
		// int[] min = new int[n * n];
		Arrays.fill(min, 2);
		// int count = 0;
		int bad = -1;
		// System.out.println(n);
		do {
			bad = -1;
			boolean ok = true;
			boolean good = false;
			int[] localmin = new int[n * (n - 1) / 2];
			// int[] localmin = new int[n * n];
			int now = 0;
			for (int i = 0; i < n && ok; i++) {
				for (int j = i + 1; j < n && ok; j++) {
					// System.out.println("i:" + i + ", j:" + j);
					if (toInt(e[rep[i]][rep[j]]) > min[now] && !good) {
						ok = false;
						bad = i;
					}
					if (toInt(e[rep[i]][rep[j]]) < min[now]) {
						good = true;
					}
					localmin[now] = toInt(e[rep[i]][rep[j]]);
					now++;
				}
			}
			if (good) {
				min = localmin;
			}
			// count++;
			/*
			 * if(count % 5000000 == 0){ String s = "(" + count + ")"; for (int
			 * i = 0; i < rep.length; i++) { s += " " + rep[i]; }
			 * System.out.println(s); } count++;
			 */
			/*
			 * String s = ""; for (int i = 0; i < rep.length; i++) { s += "" +
			 * rep[i]; } System.out.println(s);
			 */
		} while ((rep = Permutator.nextPermutation(rep, bad)) != null);

		String s = "";
		for (int i = 0; i < min.length; i++) {
			s += min[i];
		}
		return s;
	}

	public long to10(int[] a) {
		long ans = 0;
		for (int i = 0; i < 64; i++) {
			if(a.length > i){
				break;
			}
			if (toBool(a[a.length - i])) {
				ans += Math.pow(2, i);
			}
		}
		return ans;
	}

	public boolean toBool(int a) {
		if (a == 1)
			return true;
		else
			return false;
	}

	public boolean strEquals(String a, String b) {
		if (a.length() != b.length()) {
			return false;
		}
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	public int toInt(boolean a) {
		if (a) {
			return 1;
		} else {
			return 0;
		}
	}

	public String printGraph() {
		String s = "[" + this.n + "][" + this.m + "]\n" + "(";
		for (int i = 0; i < this.n - 1; i++) {
			s += this.deg[i] + ", ";
		}
		s += this.deg[n - 1] + ")\n";
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				s += this.e[i][j] + " ";
			}
			s += "\n";
		}
		return s;
	}

	public boolean equals(Graph g) {
		// if(g) throw new IllegalStateException("Bug");
		if (n != g.n || m != g.m) {
			return false;
		}
		for (int i = 0; i < n; i++) {
			if (deg[i] != g.deg[i]) {
				return false;
			}
		}

		if (this.hash == null)
			this.hash = hash();

		if (strEquals(this.hash, g.hash)) {
			return true;
		} else {
			return false;
		}
		/*
		 * boolean k;
		 * 
		 * if (this.hash.equals(g.hash)) { k = true; } else { k = false; }
		 * 
		 * int[] rep = new int[n]; for (int i = 0; i < n; i++) { rep[i] = i; }
		 * int bad = -1; do { bad = -1; boolean ok = true; for (int i = 0; i < n
		 * && ok; i++) { for (int j = 0; j < n && ok; j++) { if
		 * (g.e[rep[i]][rep[j]] != e[i][j]) { ok = false; bad = i; } } } if (ok)
		 * { if (!k) { System.out.println("WTF"); } return true; } } while ((rep
		 * = Permutator.nextPermutation(rep, bad)) != null);
		 * 
		 * if (k) { System.out.println("WTF 2"); } return false;
		 */
	}
}
