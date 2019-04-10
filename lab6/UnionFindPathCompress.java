import java.util.ArrayList;
/*
@author Felix
*/
public class UnionFindPathCompress {
    private int[] parentArray;
    private int[] sizeArray;
    private int numElemt;

    public UnionFindPathCompress(int n) {
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
        validate(v1);
        return parentArray[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

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
	public int find(int vertex) {
        validate(vertex);
	    int r = vertex;
	    ArrayList<Integer> wlkThrVertex = new ArrayList();
	    while (parentArray[r] >= 0) {
	    	wlkThrVertex.add(r);
	        r = parentArray[r];
	    }
	    for (int v: wlkThrVertex) {
	    	parentArray[v] = r;
	    }
	    return r;
	}
}
