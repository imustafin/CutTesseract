import ru.litsey2.cuttesseract.geometry.Point2d;
import ru.litsey2.cuttesseract.geometry.Segment2d;

public class Test {
	public static void main(String[] args) {

		Segment2d s1 = new Segment2d(new Point2d(-1, -1), new Point2d(1, 1));
		Segment2d s2 = new Segment2d(new Point2d(2, 2), new Point2d(2, 3));

		System.err.println(s1);
		System.err.println(s2);
		
		System.err.println(s1.intersectionPoint(s2));
		
	}
}
