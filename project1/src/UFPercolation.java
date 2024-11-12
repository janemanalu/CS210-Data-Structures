import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

public class UFPercolation implements Percolation {
    // Creates a private instance variable n
    private int gridSize;
    // Creates a new 2D boolean array to store the state of each site.
    private boolean[][] isOpen;
    // Creates a variable to keep track of the number of open sites.
    private int openSitesCount;
    // Creates a Union Find Representation of the Percolation system.
    private WeightedQuickUnionUF uf;
    // Creates a Union Find Representation of the Percolation system.
    // This data structure will not have two virtual sites; it will only have the source.
    private WeightedQuickUnionUF backwashUf;
    // Creates a virtual instance variable named source.
    private int virtualTopSite;
    // Creates a virtual instance variable named sink.
    private int virtualBottomSite;


    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n"); // Check for an invalid size input.
        }
        virtualTopSite = 0;
        virtualBottomSite = (n * n) + 1;
        this.gridSize = n; // Initializes an int n
        this.openSitesCount = 0; // Initializes an int openSites.
        // create object for Weighted Quick Union Find with 2 extra virtual sites.
        uf = new WeightedQuickUnionUF(n * n + 2);
        // create object for Weighted Quick Union Find with 1 extra virtual sites for only the top row.
        backwashUf = new WeightedQuickUnionUF(n * n + 1);
        isOpen = new boolean[n][n]; // Initializes a 2D array isOpen.
        // Connect the top row to the source and the bottom row to the sink.
        for (int i = virtualTopSite; i < n; i++) {
            uf.union(encode(0, i), virtualTopSite);
            backwashUf.union(encode(0, i), virtualTopSite);
            uf.union(encode((n - 1), i), virtualBottomSite);
            // Set all the sites in the grid to a closed state.
            for (int j = 0; j < n; j++) {
                isOpen[i][j] = false;
            }
        }
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || i >= gridSize || j < 0 || j >= gridSize) { // check for an invalid row or column input.
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!isOpen(i, j)) { // check if the site is already open or not.
            isOpen[i][j] = true; // mark the site as open.
            openSitesCount++; // increment the open site count.
            if ((i + 1) < gridSize && isOpen[i + 1][j]) { // Check if the site is in the grid.
                uf.union(encode(i, j), encode(i + 1, j)); // Connects the site to the north site.
                backwashUf.union(encode(i, j), encode(i + 1, j)); // Connects the site to the north site.
            }
            if ((i - 1) >= 0 && isOpen[i - 1][j]) { // Check if the site is in the grid.
                uf.union(encode(i, j), encode(i - 1, j)); // Connects the site to the south site.
                backwashUf.union(encode(i, j), encode(i - 1, j)); // Connects the site to the south site.
            }
            if ((j + 1) < gridSize && isOpen[i][j + 1]) { // Check if the site is in the grid.
                uf.union(encode(i, j), encode(i, j + 1)); // Connects the site to the east site.
                backwashUf.union(encode(i, j), encode(i, j + 1)); // Connects the site to the east site.
            }
            if ((j - 1) >= 0 && isOpen[i][j - 1]) { // Check if the site is in the grid.
                uf.union(encode(i, j), encode(i, j - 1)); // Connects the site to the west site.
                backwashUf.union(encode(i, j), encode(i, j - 1)); // Connects the site to the west site.
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i >= gridSize || j < 0 || j >= gridSize) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return isOpen[i][j]; // Returns the value at isOpen[i][j], true if open, false if not.
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || i >= gridSize || j < 0 || j >= gridSize) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return isOpen(i, j) && uf.connected(encode(i, j), virtualTopSite) && backwashUf.connected(encode(i, j), virtualTopSite);
        // Returns true if the site is connected to the source, and false if not connected.
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        if (gridSize <= 1) {
            return false;
        }
        return uf.connected(virtualBottomSite, virtualTopSite);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return (gridSize * i) + j + 1;
    }


    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
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