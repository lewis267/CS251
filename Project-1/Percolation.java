/**
 * Created by root on 2/4/17.
 */

public class Percolation {

    boolean[] openArray;
    int size;
    public int count;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        this.size = n;
        this.count = n*n;
        this.openArray = new boolean[count];
        this.uf = new WeightedQuickUnionUF(n*n);
    }

    public void open(int x, int y) {
        int num = getNum(x,y);
        if (openArray[num]) return;
        openArray[num] = true;

        if (num+1 < count && openArray[num+1]) uf.union(num, num+1);
        if (num-1 > -1 && openArray[num-1]) uf.union(num, num-1);
        if (num+size < count && openArray[num+size]) uf.union(num, num+size);
        if (num-size > -1 && openArray[num-size]) uf.union(num, num-size);
    }

    public boolean isOpen(int x, int y) {
        return openArray[getNum(x, y)];
    }

    public boolean isFull(int x, int y) {
        int num = getNum(x,y);
        for (int i = count-size; i < count; i++) {
            if (openArray[i] && uf.connected(i, num)) return true;
        }
        return false;
    }

    public boolean percolates() {
        for (int i = 0; i < this.size; i++) {
            if (openArray[i] && isFull(0, i)) return true;
        }
        return false;
    }

    private int getNum(int x, int y) {
        /*
        * 6 7 8
        * 3 4 5
        * 0 1 2
        */
        return x*this.size + y;
    }
    public int getX(int num) {
        return (num - this.getY(num)) / this.size;
    }
    public int getY(int num) {
        return num % this.size;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation p = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            if (p.isOpen(x, y)) continue;
            p.open(x, y);
        }
        StdOut.println((p.percolates() ? "Yes" : "No"));
    }
}
