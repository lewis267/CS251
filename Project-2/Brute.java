import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lewis267 on 2/8/17.
 */
public class Brute {

    public ArrayList<Point[]> run(Point[] points) {
        ArrayList<Point[]> lines = new ArrayList<>();

        for (int a = 0; a < points.length - 3; a++) {
            for (int b = a + 1; b < points.length - 2; b++) {
                for (int c = b + 1; c < points.length - 1; c++) {
                    for (int d = c + 1; d < points.length; d++) {
                        if (Point.areCollinear(points[a], points[b], points[c], points[d])) {
                            lines.add(new Point[]{points[a], points[b], points[c], points[d]});
                        }
                    }
                }
            }
        }
        return lines;
    }

    public static void main(String[] args) {
        String filename = "visualPoints.txt";
        Point[] points = Point.readPoints();
        Arrays.sort(points);

        Brute B = new Brute();
        ArrayList<Point[]> lines = B.run(points);

        Point.saveLinesFile(lines, filename);
        Point.printLines(lines);
    }
}
