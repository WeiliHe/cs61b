package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.Stopwatch;
import java.lang.Math;
import java.lang.*;

public class PercolationStats {
    private int[] xArray;
    private int netSize;
    private int simTimes;
    final static double cfInterv = 1.96;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 | T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        Stopwatch watch = new Stopwatch();
        for (int t = 0; t < T; t++) {
            Percolation net = pf.make(N);
            int x = 0;
            while (net.percolates() != true) {
//                generate the random row and col
                int row = StdRandom.uniform(0, N - 1);
                int col = StdRandom.uniform(0, N - 1);
                net.open(row, col);
                x++;
            }
            xArray[t] = x;
        }
        System.out.println(watch.elapsedTime());
    }
    public double mean() {
        return StdStats.mean(xArray);
    }
    public double stddev() {
        return StdStats.stddev(xArray);
    }

    public double confidenceLow() {
        return mean() - cfInterv * stddev() / Math.sqrt(xArray.length);
    }

    public double confidenceHigh() {
        return mean() + cfInterv * stddev() / Math.sqrt(xArray.length);
    }
}
