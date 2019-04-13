package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF net;
    public Percolation(int N) {
        net = new WeightedQuickUnionUF(N);
    }
    public void open(int row, int col) {
        return;
    }
    public boolean isOpen(int row, int col) {
        return false;
    }
    public boolean isFull(int row, int col) {
        return false;
    }
    public int numberOfOpenSites() {
        return 0;
    }
    public boolean percolates() {
        return false;
    }
    public static void main (String[] args) {

    }
}
