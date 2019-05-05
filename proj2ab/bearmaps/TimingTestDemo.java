package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import java.lang.Math;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug. Demonstrates how you can use either
 * System.currentTimeMillis or the Princeton Stopwatch
 * class to time code.
 */
public class TimingTestDemo {
    public static void main(String[] args) {
        int N = 1000000;
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<Integer> integerPQ = new ArrayHeapMinPQ();
        for (int i = 0; i < N; i++) {
            integerPQ.add(i, N-i);
        }
        for (int i = 0; i < 2000; i++) {
            int key = (int) Math.random() * N;
            double priority = (int) Math.random() * N;
            integerPQ.changePriority(key, priority);
        }
//        for (int i = 0; i < N / 2; i++) {
//            integerPQ.removeSmallest();
//        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for ArrayHeapMinPQ for N " + N + ",: " + (end - start)/1000.0 +  " seconds.");

//        Stopwatch sw = new Stopwatch();
        long start1 = System.currentTimeMillis();
        NaiveMinPQ<Integer> nvIntegerPQ = new NaiveMinPQ();
        for (int i = 0; i < N; i++) {
            nvIntegerPQ.add(i, N-i);
        }
        for (int i = 0; i < 2000; i++) {
            int key = (int) Math.random() * N;
            double priority = (int) Math.random() * N;
            nvIntegerPQ.changePriority(key, priority);
        }
//        for (int i = 0; i < N / 2; i++) {
//            nvIntegerPQ.removeSmallest();
//        }
        long end1 = System.currentTimeMillis();
        System.out.println("Total time elapsed for NaiveMinPQ for N " + N + ",: " + (end1 - start1)/1000.0 +  " seconds.");
//        System.out.println("Total time elapsed for NaiveMinPQ for N" + N + ",: " + sw.elapsedTime() +  " seconds.");
    }
}
