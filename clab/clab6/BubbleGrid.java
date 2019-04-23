import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.*;
import java.util.ArrayList;

public class BubbleGrid {
    private int[][] grid;
    private UnionFind stkySet;
    private int colLength;
    private int rowLength;
    private int virtualTop;
    private int numOnes;
    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        if (grid.length == 0) {
            throw new IllegalArgumentException("Grid is null");
        }
        rowLength = grid.length;
        colLength = grid[0].length;
        virtualTop = rowLength * colLength;
        stkySet = new UnionFind(rowLength * colLength + 1);
        StickToTop(colLength);
        numOnes = countNumOnes();
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < rowLength; i++) {
            for (int j =0; j < colLength; j++) {
                if (isStikcy(i, j)) {
                    return;
                }
                if ((i < 0 | i > rowLength - 1) | (j < 0 | j > colLength - 1)) {
                    throw new IndexOutOfBoundsException();
                }
                if ((i == 0) && (grid[i][j] == 1)){
                    stkySet.union(virtualTop, xyTo1D(i, j));
                }
                ArrayList<Integer> stickyNeighbors = getStickyNeighbors(i, j);
                for (Integer stickyNeighbor: stickyNeighbors) {
                    if (stickyNeighbor != null) {
                        stkySet.union(stickyNeighbor, xyTo1D(i, j));
                    }
                }
            }
        }
    }

//    initialize the top
    private void StickToTop(int N) {
        for (int j = 0; j < N; j++) {
            if (grid[0][j] == 1) {
                stkySet.union(xyTo1D(0, j), virtualTop);
            }
        }
    }

    private int countNumOnes() {
        int count = 0;
        for (int i = 0; i < rowLength; i++) {
            for (int j =0; j < colLength; j++) {
                if (grid[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    private int xyTo1D(int x, int y){
        return x * colLength + y;
    }

    private boolean isStikcy(int row, int col) {
        if ((row < 0 | row > rowLength - 1) | (col < 0 | col > colLength - 1)) {
            throw new IndexOutOfBoundsException();
        }
        return stkySet.connected(xyTo1D(row, col), virtualTop);
    }

    private ArrayList<Integer> getStickyNeighbors(int row, int col) {
        ArrayList<Integer> stkyNeighbors = new ArrayList();
        if (row > 0) {
            if (isStikcy(row - 1, col)) {
                stkyNeighbors.add(xyTo1D(row - 1, col));
            }
        }
        if (row < rowLength - 1) {
            if (isStikcy(row + 1, col)) {
                stkyNeighbors.add(xyTo1D(row + 1, col));
            }
        }
        if (col > 0) {
            if (isStikcy(row, col - 1)) {
                stkyNeighbors.add(xyTo1D(row, col - 1));
            }
        }
        if (col < colLength - 1) {
            if (isStikcy(row, col + 1)) {
                stkyNeighbors.add(xyTo1D(row, col + 1));
            }
        }
        return stkyNeighbors;
    }


    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int[] fallNum = new int[darts.length];
        for (int i = 0; i < darts.length; i++) {
            int row = darts[i][0];
            int col = darts[i][1];
            stkySet.deUnion(xyTo1D(row, col));
            numOnes -= 1;
            fallNum[i] = numOnes - (stkySet.sizeOf(virtualTop) - 1);
        }
        return fallNum;
    }
}
