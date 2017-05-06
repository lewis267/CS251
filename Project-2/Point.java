/*************************************************************************
 * Compilation:  javac Point.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point>{

    // compare points by slope
    public final Comparator<Point> BY_SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {

            return Double.compare(Point.this.slope(o1), Point.this.slope(o2));
        }
    };

    private final int x;      // x coordinate
    private final int y;      // y coordinate

    public final String str;

    // constructor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.str = "(" + Integer.toString(this.x) + ", " + Integer.toString(this.y) + ")";
    }

    // are the 3 points p, q, and r collinear?
    public static boolean areCollinear(Point p, Point q, Point r) {
        return (p.slope(q) == r.slope(q));
    }

    // are the 4 points p, q, r, and s collinear?
    public static boolean areCollinear(Point p, Point q, Point r, Point s) {
        return areCollinear(p, q, r) && areCollinear(q, r, s);
    }

    // is this point lexicographically smaller than that one?
    public int compareTo(Point that) {
        int cx = ((Integer)this.x).compareTo(that.x);
        if (cx != 0) return cx;
        return ((Integer)this.y).compareTo(that.y);
    }

    //region My Functions

    public double slope(Point p) {
        if (this.x - p.x == 0) return Double.POSITIVE_INFINITY;
        else return (double)(this.y - p.y) / (double)(this.x - p.x);
    }

    public static String getLineString(Point[] pts) {
        Arrays.sort(pts);
        String rtnStr = Integer.toString(pts.length) + ":";
        for (int i = 0; i < pts.length; i++) {
            if (i != 0) rtnStr += " -> ";
            rtnStr += pts[i].str;
        }
        return rtnStr;
    }

    public static void printLines(ArrayList<Point[]> lines) {
        for (Point[] line : lines) StdOut.println(getLineString(line));
        StdOut.println();
    }
    public static void saveLinesFile(ArrayList<Point[]> lines, String filename) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filename));
            for (Point[] line : lines) bw.write(getLineString((line)) + "\n");
        } catch (IOException e) {
            StdOut.println("Error in writing file.");
            e.printStackTrace();
        } finally {
            try { if (bw != null) { bw.close(); }
            } catch (IOException e) { //Do nothing
            }
        }
    }

    public static Point[] readPoints() {
        int counter = 0;
        Point[] points = new Point[StdIn.readInt()];
        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            //make a new point
            points[counter++] = new Point(x, y);
        }
        return points;
    }

    @Override
    public boolean equals(Object other) {

        if (other.getClass() != this.getClass()) return false;
        if (other == this) return true;
        boolean t = this.x == ((Point) other).x && this.y == ((Point) other).y;
        //StdOut.print((t?"T":"F") + " ");
        return (t);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + ((Integer)this.x).hashCode();
        hash = 31 * hash + ((Integer)this.y).hashCode();
        return hash;
    }

    public static ArrayList<Point[]> removeDupes(ArrayList<Point[]> lines) {
        ArrayList<Point[]> removable = new ArrayList<>();
        removable.addAll(lines);

        for (Point[] line : removable) {
            int n = line.length;
            //will skip any lines of length 4 here:
            for (int i = 1; n - i > 3; i++) {
                // n-i is the size of the sub-arrays being tested
                for (int j = 0; j <= i; j++) {
                    // j is the shift of the sub-array from the left side of the line array
                    Point[] subarray = Arrays.copyOfRange(line, j, (n - i) + j);

                    //remove dup
                    for (int a = 0; a < lines.size(); a++) {
                        if (Arrays.equals(subarray, lines.get(a)))
                            lines.remove(a);
                    }
                }
            }
        }
        return lines;
    }
    //endregion
}
