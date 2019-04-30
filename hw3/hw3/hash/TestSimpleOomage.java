package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        for (int i = 0; i < 1000000; i++) {
            int red1 = StdRandom.uniform(0, 51) * 5;
            int green1 = StdRandom.uniform(0, 51) * 5;
            int blue1 = StdRandom.uniform(0, 51) * 5;
            SimpleOomage ooA = new SimpleOomage(red1, green1, blue1);
            int red2 = StdRandom.uniform(0, 51) * 5;
            int green2 = StdRandom.uniform(0, 51) * 5;
            int blue2 = StdRandom.uniform(0, 51) * 5;
            SimpleOomage ooB = new SimpleOomage(red2, green2, blue2);
            if (ooA.equals(ooB) == false) {
                assertTrue(ooA.hashCode() != ooB.hashCode());
            }
        }
        SimpleOomage ooC = new SimpleOomage(5, 10, 15);
        SimpleOomage ooD = new SimpleOomage(15, 10, 5);
        assertTrue(ooC.hashCode() != ooD.hashCode());
        /*
          meaning no two SimpleOomages should EVER have the same
          hashCode UNLESS they have the same red, blue, and green values!
         */

    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }


    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}
