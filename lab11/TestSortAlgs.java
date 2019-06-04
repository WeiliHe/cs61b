import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> tas = new Queue<Integer>();
        int N = 400000;
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i <= N; i++) {
            tas.enqueue((int) (Math.random() * N));
        }
        tas = QuickSort.quickSort(tas);
        System.out.println(sw.elapsedTime());
//        System.out.println(tas);
        assertTrue(isSorted(tas));
    }

    @Test
    public void testMergeSort() {
//        Queue<String> tas = new Queue<String>();
//        tas.enqueue("Joe");
//        tas.enqueue("Omar");
//        tas.enqueue("Itai");
//        tas.enqueue("Felix");
//        tas.enqueue("Alice");
//        tas.enqueue("Apple");
//        tas.enqueue("Fuck");
//        tas.enqueue("Zark");
        Queue<Integer> tas = new Queue<Integer>();
        int N = 400000;
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i <= N; i++) {
            tas.enqueue((int) (Math.random() * N));
        }
        tas = MergeSort.mergeSort(tas);
        System.out.println(sw.elapsedTime());
//        System.out.println(tas);
        assertTrue(isSorted(tas));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
