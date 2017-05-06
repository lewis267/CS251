import java.io.*;
import java.util.logging.FileHandler;

/**
 * Created by root on 2/4/17.
 */
public class PercolationVisualizer {
    private String filename = "visualMatrix.txt";
    private String file;

    public PercolationVisualizer() {
        this.file = "";
    }

    private void printlnS(String line) {
        StdOut.println(line);
        this.file += line + "\n";
    }
    private void printS(String value) {
        StdOut.print(value);
        this.file += value;
    }

    private void writeMatrix(Percolation p, int n) {
        for (int row = n-1; row > -1; row--) {
            for (int col = 0; col < n; col++) {
                if (col!=0) printS(" ");
                int num = row*n + col;
                if (p.isOpen(row, col)) {
                    if (p.isFull(row, col)) printS("2");
                    else printS("1");
                }
                else printS("0");
            }
            printlnS("");
        }
        printlnS("");
    }

    private void writeFile() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(this.filename));
            bw.write(this.file);
        } catch (IOException e) {
            StdOut.println("Error in writing file.");
            e.printStackTrace();
        } finally {
            try { if (bw != null) { bw.close(); }
            } catch (IOException e) { //Do nothing
            }
        }
    }

    public static void main(String[] args) {
        PercolationVisualizer pv = new PercolationVisualizer();

        int n = StdIn.readInt();
        pv.printlnS(Integer.toString(n));
        pv.printlnS("");

        Percolation p = new Percolation(n);

        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            if (p.isOpen(x, y)) continue;
            p.open(x, y);
            pv.writeMatrix(p, n);
        }

        pv.writeFile();
    }
}
