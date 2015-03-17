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

	long hash1 = 0;
	long hash2;

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

	public void hash() {
		int[] rep = new int[n];
		for (int i = 0; i < n; i++) {
			rep[i] = i;
		}
		boolean[] min = new boolean[n * (n - 1) / 2];
		Arrays.fill(min, true);
		int bad = -1;
		do {
			bad = -1;
			boolean ok = true;
			boolean good = false;
			boolean[] localmin = new boolean[n * (n - 1) / 2];
			int now = 0;
			for (int i = 0; i < n && ok; i++) {
				for (int j = i + 1; j < n && ok; j++) {
					if (e[rep[i]][rep[j]] && !min[now] && !good) {
						ok = false;
						bad = i;
					}
					if (!e[rep[i]][rep[j]] && min[now]) {
						good = true;
					}
					localmin[now] = e[rep[i]][rep[j]];
					now++;
				}
			}
			if (good) {
				min = localmin;
			}
		} while ((rep = Permutator.nextPermutation(rep, bad)) != null);

//		String s = "";
//		for (int i = 0; i < min.length; i++) {
//			s += min[i];
//		}
		this.hash1 = to10(Arrays.copyOfRange(min, 0, Math.min(63, min.length)));
		if (min.length > 64) {
			this.hash2 = to10(Arrays.copyOfRange(min, 64,
					Math.min(127, min.length)));
		} else {
			this.hash2 = 0;
		}
	}

	public int myhash(){
		int a = 0;
		for(int i = 0; i < this.n; i++){
			a *= 10 * this.deg[i];
		}
		return a;
	}
	
	public long to10(boolean[] a) {
		long ans = 0;
		for (int i = 0; i < Math.min(64, a.length); i++) {
			if (a.length <= i) {
				break;
			}
			long x = a[i] ? 1 : 0;
			ans = ans << 1;
			ans = ans | x;
		}
		return ans;
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
		if (n != g.n || m != g.m) {
			return false;
		}
		for (int i = 0; i < n; i++) {
			if (deg[i] != g.deg[i]) {
				return false;
			}
		}

		if (this.hash1 == 0)
			this.hash();

		if(this.hash1 == g.hash1 && this.hash2 == g.hash2){
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
