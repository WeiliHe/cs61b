package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.*;
import java.util.ArrayList;

public class Percolation {
    private WeightedQuickUnionUF openSites;
    private WeightedQuickUnionUF antiBackwash;
    private int[][] sqNetwork;
    private int numofOpenSites;
    private int virtualTop;
    private int virtualBottom;
    private int netSize;

    public Percolation(int N) {
        if (N <= 0) {
    		throw new IllegalArgumentException();
    	}
        openSites = new WeightedQuickUnionUF(N * N + 2);
        antiBackwash = new WeightedQuickUnionUF(N * N + 1);
        sqNetwork = new int[N][N];
        numofOpenSites = 0;
        virtualTop = N * N;
        virtualBottom = N * N + 1;
        // initializeUnionSet(N);
        netSize = N;
    }

    private void initializeUnionSet(int N) {
    	for (int i = 0; i < N; i++) {
    		openSites.union(xyTo1D(0, i), virtualTop);
    		openSites.union(xyTo1D(N - 1, i), virtualBottom);
    		antiBackwash.union(xyTo1D(0, i), virtualTop);
    	}
    }

    // convert 2D index to 1D
    private int xyTo1D(int r, int c) {
    	return r * netSize +  c;
    }

    // find the nearby open node
    private ArrayList<Integer> getOpenNearby(int row, int col) {
    	ArrayList<Integer> openNeighbors = new ArrayList();
    	if (row > 0) {
            if (isOpen(row - 1, col)) {
                openNeighbors.add(xyTo1D(row - 1, col));
            } 
        }
        if (row < netSize - 1) {
            if (isOpen(row + 1, col)) {
                openNeighbors.add(xyTo1D(row + 1, col));
            } 
        }
        if (col > 0) {
        	if (isOpen(row, col - 1)) {
                openNeighbors.add(xyTo1D(row, col - 1));
            } 
        }
       	if (col < netSize - 1) {
        	if (isOpen(row, col + 1)) {
                openNeighbors.add(xyTo1D(row, col + 1));
            } 
        }
    	return openNeighbors;
    } 

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        if ((row < 0 | row > netSize - 1) | (col < 0 | col > netSize - 1)) {
            throw new IndexOutOfBoundsException();
        }
        if (row == 0) {
        	openSites.union(virtualTop, xyTo1D(row, col));
            antiBackwash.union(virtualTop, xyTo1D(row, col));
        } 
        if (row == netSize - 1) {
        	openSites.union(virtualBottom, xyTo1D(row, col));
        }

        sqNetwork[row][col] = 1;
        numofOpenSites += 1;
        // if nearbyNode is open, connect
        ArrayList<Integer> openNeighbors = getOpenNearby(row, col);
        for (Integer openNeighbor: openNeighbors) {
        	if (openNeighbor != null) {
	            openSites.union(openNeighbor, xyTo1D(row, col));
	            antiBackwash.union(openNeighbor, xyTo1D(row, col));
        	}
        }
    }

    public boolean isOpen(int row, int col) {
    	if ((row < 0 | row > netSize - 1) | (col < 0 | col > netSize - 1)) {
            throw new IndexOutOfBoundsException();
        }
        return sqNetwork[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
    	if ((row < 0 | row > netSize - 1) | (col < 0 | col > netSize - 1)) {
            throw new IndexOutOfBoundsException();
        }
        return antiBackwash.connected(xyTo1D(row, col), virtualTop);
    }

    public int numberOfOpenSites() {
        return numofOpenSites;
    }

    public boolean percolates() {
        return openSites.connected(virtualTop, virtualBottom);
    }
    public static void main(String[] args) {
        int N = 1;
        Percolation net = new Percolation(N);
    }
}
