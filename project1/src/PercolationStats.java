import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
    // Creates an integer instance variable named m
    private int experiments;
    // Creates an instance variable named thresholds
    private double[] thresholds;

    // Performs m independent experiments.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Illegal n or m");
        }
        this.experiments = m;
        thresholds = new double[m];
        // Conduct m independent experiments.
        for (int i = 0; i < m; i++) {
            // Create new percolation object for each experiment
            Percolation percolation = new UFPercolation(n);

            // Randomly open sites until system percolates.
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                percolation.open(row, col);
            }
            // Calculate threshold of open sites at percolation.
            double threshold = (percolation.numberOfOpenSites() * 1.0 / (n * n));

            thresholds[i] = threshold;
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        double mean = StdStats.mean(thresholds);
        return mean;
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        double stdDev = StdStats.stddev(thresholds);
        return stdDev;
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        double confidenceLow =  mean() - ((1.96 * stddev()) / Math.sqrt(experiments));
        return confidenceLow;
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        double confidenceHigh = mean() + ((1.96 * stddev()) / Math.sqrt(experiments));
        return confidenceHigh;
    }


    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}