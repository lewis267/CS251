/**
 * Created by root on 2/4/17.
 */
public class PercolationStats {
    private static void openBlockedCell(Percolation p) {
        int count = p.count;
        boolean[] isOpen = p.openArray;

        while (true) {
            int num = StdRandom.uniform(count);
            int x = p.getX(num);
            int y = p.getY(num);

            if (!isOpen[num]) {
                p.open(x,y);
                break;
            }
        }
    }
    private static void openBlockedCell(PercolationQuick p) {
        int count = p.count;
        boolean[] isOpen = p.openArray;

        while (true) {
            int num = StdRandom.uniform(count);
            int x = p.getX(num);
            int y = p.getY(num);

            if (!isOpen[num]) {
                p.open(x,y);
                break;
            }
        }
    }

    private static int getNumTillPerc(int n, String type) {
        int counter = 0;

        //fast version
        if (type.equals("fast")) {
            Percolation p = new Percolation(n);

            while (counter < n || !p.percolates()) {
                openBlockedCell(p);
                counter++;
            }
            return counter;
        }

        //slow version
        if (type.equals("slow")) {
            PercolationQuick p = new PercolationQuick(n);

            while (counter < n || !p.percolates()) {
                openBlockedCell(p);
                counter++;
            }
            return counter;
        }

        //random corner case when type is wrong
        else {
            StdOut.println("Invalid 3rd argument to PercolationStats");
            return counter;
        }
    }

    private static long getMean(long[] a) {
        if (a.length == 0) return 0;

        long total = 0;
        for (long i : a) { total += i; }
        return total / a.length;
    }

    private static long getSD(long[] a) {
        if (a.length == 0) return 0;

        long mean = getMean(a);
        long sum = 0;
        for (long i : a) {
            sum += (i - mean) * (i - mean);
        }
        return (long)Math.sqrt(sum / a.length);
    }

    public static void main(String[] args) {
        long s0 = System.nanoTime();
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        String find_type = args[2];

        double mean_count;
        double sd_count;
        double mean_time;
        double sd_time;
        double total_time;

        long[] values = new long[T];
        long[] times = new long[T];

        for (int i = 0; i < T; i++) {
            long start = System.nanoTime();
            values[i] = getNumTillPerc(N, find_type);
            times[i] = System.nanoTime() - start;
        }

        mean_count = getMean(values);
        sd_count = getSD(values);
        mean_time = (double)getMean(times)/(double)1000000000;
        sd_time = (double)getSD(times)/(double)1000000000;
        total_time = (double)(System.nanoTime() - s0)/(double)1000000000;

        StdOut.println();
        StdOut.println("**OUTPUT BELOW**");
        StdOut.println("mean threshold="+mean_count);
        StdOut.println("std dev="+sd_count);
        StdOut.println("time="+total_time);
        StdOut.println("mean time="+mean_time);
        StdOut.println("stddev time="+sd_time);
        StdOut.println();
    }
}
