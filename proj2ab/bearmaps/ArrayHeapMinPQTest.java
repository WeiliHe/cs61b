package bearmaps;

import edu.princeton.cs.algs4.In;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    @Test
    public void testBasic() {
        ArrayHeapMinPQ<String> stringPQ = new ArrayHeapMinPQ();
        stringPQ.add("a", 2);
        stringPQ.add("b", 1);
        stringPQ.add("c", 5);
        stringPQ.add("d", 8);
        stringPQ.add("e", 6);
        stringPQ.add("f", 4);
        stringPQ.add("g", 3);
        assertEquals(7, stringPQ.size());
        assertEquals("b", stringPQ.getSmallest());
        assertEquals("b", stringPQ.removeSmallest());
        assertEquals(6, stringPQ.size());
        assertEquals("a", stringPQ.getSmallest());
        stringPQ.changePriority("d", 0.5);
        assertEquals("d", stringPQ.getSmallest());
    }

    @Test
    public void testResize() {
        ArrayHeapMinPQ<Integer> integerPQ = new ArrayHeapMinPQ();
        for (int i = 0; i < 100; i++) {
            integerPQ.add(i, 100-i);
        }
        assertEquals(100, integerPQ.size());
        assertEquals((Integer)99, integerPQ.getSmallest());
        for (int i = 0; i < 50; i++) {
            integerPQ.removeSmallest();
        }
        assertEquals(50, integerPQ.size());
        assertEquals((Integer)49, integerPQ.getSmallest());
    }
}
