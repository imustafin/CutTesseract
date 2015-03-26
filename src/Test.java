import java.util.Scanner;

import ru.litsey2.cuttesseract.PointRotator;
import ru.litsey2.cuttesseract.geometry.Formulas;
import ru.litsey2.cuttesseract.geometry.Point2d;
import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment2d;
import ru.litsey2.cuttesseract.geometry.Segment4d;

public class Test {
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		Point4d a = new Point4d(in.nextDouble(), in.nextDouble(),
				in.nextDouble(), in.nextDouble());
		Point4d b = new Point4d(in.nextDouble(), in.nextDouble(),
				in.nextDouble(), in.nextDouble());

		Point4d c = new Point4d(in.nextDouble(), in.nextDouble(),
				in.nextDouble(), in.nextDouble());
		Point4d d = new Point4d(in.nextDouble(), in.nextDouble(),
				in.nextDouble(), in.nextDouble());

		System.out.println(Formulas.intersection2dComparator.compare(
				new Segment4d(a, b), new Segment4d(c, d)));

	}
}

/*
 * 
 * 
0 0 0 0
1 1 0 0
0 0 1 0
1 0.5 1 0
*/
