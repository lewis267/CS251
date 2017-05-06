import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lewis267 on 2/15/17.
 */
public class Fast {

    public ArrayList<Point[]> run(Point[] points) {
        ArrayList<Point[]> lines = new ArrayList<>();

        for (int i = 0; i < points.length - 3; i++) {
            Point[] sortable = new Point[points.length - (i + 1)];
            for (int j = 0; j < sortable.length; j++) sortable[j] = points[j+1+i];

            Arrays.sort(sortable, points[i].BY_SLOPE_ORDER);

            int count;
            for (int j = 0; j < sortable.length; j += count) {

                double s = points[i].slope(sortable[j]);
                count = 1;

                //find all with same slope
                while (j+count < sortable.length && s == points[i].slope(sortable[j + count])) count++;

                //when you find a group of 3 or more
                if (count > 2) {
                    //create an array for the group
                    Point[] line = new Point[count + 1];

                    //add the fixed point
                    line[0] = points[i];

                    //add the group of points
                    for (int c = 0; c < count; c++) line[c + 1] = sortable[j + c];

                    lines.add(line);
                }
            }
        }
        return Point.removeDupes(lines);
    }

    public static void main(String[] args) {
        String filename = "visualPoints.txt";
        Point[] points = Point.readPoints();
        Arrays.sort(points);

        Fast F = new Fast();
        ArrayList<Point[]> lines = F.run(points);

        Point.saveLinesFile(lines, filename);
        Point.printLines(lines);
    }
}
