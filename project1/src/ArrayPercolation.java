import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using a 2D array.
public class ArrayPercolation implements Percolation {
    // Percolation system size variable
    private int n;

    // Percolation system matrix
    private boolean[][] open;

    // Variable to hold the number of open sites
    private int openSites;


    // Constructs an n x n percolation system, with all sites blocked.
    public ArrayPercolation(int n) {
        // Checking corner cases
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n;
        open = new boolean[n][n];
        openSites = 0;

    } // end ArrayPercolation constructor

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!open[i][j]) {
            open[i][j] = true;
            openSites++;
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j];

    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }

        boolean[][] full = new boolean[n][n];
        for (int k = 0; k < n; k++) {
            floodFill(full, 0, k);

        }
        return full[i][j];
    }
    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        for (int a = 0; a < n; a++) {
            if (isFull(n-1, a)) {
                return true;
            }
        }
        return false;
    }

    // Recursively flood fills full[][] using depth-first exploration, starting at (i, j).
    private void floodFill(boolean[][] full, int i, int j) {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1 ||
                !open[i][j] || full[i][j]) {
            return;
        }
        full[i][j] = true;
        floodFill(full, i - 1, j);
        floodFill(full, i, j + 1);
        floodFill(full, i, j - 1);
        floodFill(full, i + 1, j);

    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        ArrayPercolation perc = new ArrayPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}
