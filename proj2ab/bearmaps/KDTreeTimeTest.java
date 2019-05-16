package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class KDTreeTimeTest {
    public static void main(String[] args) {
        int N = 40000;
        int scope = 300;
        List<Point> pointList = new ArrayList<Point>();
        for (int i = 0; i < N; i++) {
            double x = Math.random() * scope;
            double y = Math.random() * scope;
            pointList.add(new Point(x, y));
        }
        NaivePointSet nvPointSet = new NaivePointSet(pointList);
        long start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            double x = Math.random() * scope;
            double y = Math.random() * scope;
            nvPointSet.nearest(x, y);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for NaivePointSet for N " + N + ",: " + (end - start) / 1000.0 + " seconds.");

//        Stopwatch sw = new Stopwatch();
//        KDTree kdTree = new KDTree(pointList);
        KDTree kdTree = new KDTree(pointList);
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            double x = Math.random() * scope;
            double y = Math.random() * scope;
            kdTree.nearest(x, y);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("Total time elapsed for KDTree for N " + N + ",: " + (end1 - start1) / 1000.0 + " seconds.");
    }
}
