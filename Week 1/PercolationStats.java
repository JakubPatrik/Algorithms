import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private final double[] values;
    private final int trials;
    private final static double confidence_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        values = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
            }
            values[i] = (double) percolation.numberOfOpenSites() / Math.pow(n, 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.values);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.values);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidence_95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidence_95 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
     
        Stopwatch watch = new Stopwatch();
        PercolationStats pStats = new PercolationStats(n, trials);
        double time = watch.elapsedTime();
        
        StdOut.print(String.format("mean                          %f \nstddev                        %f \n95%% confidence interval      [%f, %f]", 
            pStats.mean(), pStats.stddev(), pStats.confidenceLo(), pStats.confidenceHi()));
    
        StdOut.print(String.format("\n\nThe operation took %f seconds\n", time));
    }
}