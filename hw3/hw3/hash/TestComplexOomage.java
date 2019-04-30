package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        int N = 50;
        List<Oomage> oomages = new ArrayList<>();
        for (int i = 1; i < N; i++) {
            ArrayList<Integer> params = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                params.add(254);
            }
            ComplexOomage oom = new ComplexOomage(params);
            oomages.add(oom);
        }
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        int N = 10000;
        for (int i = 0; i < N; i += 1) {
//            oomies.add(SimpleOomage.randomSimpleOomage());
            deadlyList.add(ComplexOomage.randomComplexOomage());
        }
        // Your code here.
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}