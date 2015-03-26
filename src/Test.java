import java.util.ArrayList;
import java.util.Scanner;

import ru.litsey2.cuttesseract.PointRotator;
import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;

public class Test {
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		int n = in.nextInt();

		ArrayList<Segment4d> edges = new ArrayList<Segment4d>();

		for (int i = 0; i < n; i++) {
			Point4d a = new Point4d(in.nextDouble(), in.nextDouble(),
					in.nextDouble(), in.nextDouble());

			Point4d b = new Point4d(in.nextDouble(), in.nextDouble(),
					in.nextDouble(), in.nextDouble());
			
			edges.add(new Segment4d(a, b));
		}
		
//		System.err.println(PointRotator.isSorted(edges));

		PointRotator.drawOrderSort(edges);
		
		System.err.println(edges);
		
		in.close();

	}
}

/*
 * 
 * 
 * 0 0 0 0 1 1 0 0 0 0 1 0 1 0.5 1 0
 */
