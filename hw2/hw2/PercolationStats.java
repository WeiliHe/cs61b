package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.Stopwatch;
import java.lang.Math;
import java.lang.*;

public class PercolationStats {
    private double[] xArray;
    private int netSize;
    private int simTimes;
    static final double CFINTERV = 1.96;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 | T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        xArray = new double[T];
        Stopwatch watch = new Stopwatch();
        for (int t = 0; t < T; t++) {
            Percolation net = pf.make(N);
            int x = 0;
            while (!net.percolates()) {
//                generate the random row and col
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                if (!net.isOpen(row, col)) {
                    net.open(row, col);
                    x++;
                }
            }
            System.out.println(x);
            xArray[t] = (double) x / (N * N);
//            System.out.println("simulation time");
//            System.out.println(t);
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
        return mean() - CFINTERV * stddev() / Math.sqrt(xArray.length);
    }

    public double confidenceHigh() {
        return mean() + CFINTERV * stddev() / Math.sqrt(xArray.length);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(1, 20, pf);
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
    }
}
