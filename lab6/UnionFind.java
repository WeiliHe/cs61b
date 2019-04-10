public class UnionFind {
    private int[] parentArray;
    private int[] sizeArray;
    private int numElemt;
    // TODO - Add instance variables?

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parentArray = new int[n];
        sizeArray = new int[n];
        for (int i = 0; i < n; i++) {
            parentArray[i] = -1;
            sizeArray[i] = 1;
        }
        numElemt = n;
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 | vertex > numElemt) {
            throw new RuntimeException("v1 is not a valid index");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        return -parentArray[find(v1)];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        // TODO
        validate(v1);
        return parentArray[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        if (rootV1 != rootV2) {
            if (sizeOf(v1) >= sizeOf(v2)) {
                parentArray[rootV1] += parentArray[rootV2];
                parentArray[rootV2] = rootV1;
                sizeArray[rootV1] += sizeArray[rootV2];
            } else {
                parentArray[rootV2] += parentArray[rootV1];
                parentArray[rootV1] = rootV2;
                sizeArray[rootV2] += sizeArray[rootV1];
            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        int r = vertex;
        while (parentArray[r] >= 0) {
            r = parentArray[r];
        }
        return r;
    }
}
