import java.util.ArrayList;

/**
 * Created by lewis267 on 2/18/17.
 */
public class Test {

    // Creates a Point[n] of n=count random Point objects.
    private static Point[] makePoints(int count) {
        int max = 32768; //or is it 32,767
        int multiple = 100;
        Point[] points = new Point[count];

        for (int i = 0; i < count; i++) {
            points[i] = new Point(StdRandom.uniform(max / multiple) * multiple, StdRandom.uniform(max / multiple) * multiple);
        }

        return points;
    }

    public static void main(String[] args) {
        int[] counts = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                counts[i] = Integer.parseInt(args[i]);
            }
            catch (Exception e) {
                StdOut.println("Invalid argument in call to Test.");
                return;
            }
        }

        Brute B = new Brute();
        Fast F = new Fast();
        long init;
        long end;
        ArrayList<Point[]> a;

        StdOut.println("Count\t: Brute\t\t#\tFast\t\t#");

        for (int count : counts) {

            StdOut.print(Integer.toString(count));
            Point[] points = makePoints(count);
            StdOut.print("\t: ");

            //region Brute
            init = System.nanoTime();
            a = B.run(points);
            end = System.nanoTime();

            String timestr = Float.toString((float)(end - init)/(float)1000000000);
            timestr = timestr.contains("E") ? timestr.substring(0, 7) + " " + timestr.substring(7): timestr;
            timestr = timestr.length() == 11 ? timestr : String.format("%-11s", timestr).replace(' ', '0');
            StdOut.print( timestr + "\t" + Integer.toString(a.size()));
            //endregion

            //region Fast
            init = System.nanoTime();
            a = F.run(points);
            end = System.nanoTime();

            timestr = Float.toString((float)(end - init)/(float)1000000000);
            timestr = timestr.contains("E") ? timestr.substring(0, 7) + " " + timestr.substring(7): timestr;
            timestr = timestr.length() == 11 ? timestr : String.format("%-11s", timestr).replace(' ', '0');
            StdOut.println("\t" + timestr + "\t" + Integer.toString(a.size()));
            //endregion
        }
    }
}
