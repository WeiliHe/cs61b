import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Weili
 * @Source StudentArrayDequeLauncher
 */
public class TestArrayDequeGold {
//    @Test
//    public void testArrayDeque() {
//
//        // test remove
//        StudentArrayDeque<Integer> sad4 = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> ads4 = new ArrayDequeSolution<>();
//        // addLast test
//        for (int i = 0; i < 20; i += 1) {
//            double numberBetweenZeroAndOne = StdRandom.uniform();
//            if (numberBetweenZeroAndOne < 0.5) {
//                sad4.addFirst(i);
//                ads4.addFirst(i);
//            } else {
//                sad4.addLast(i);
//                ads4.addLast(i);
//            }
//        }
//
//        StudentArrayDeque<Integer> fromsad4 = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> fromads4 = new ArrayDequeSolution<>();
//        for (int i = 0; i < 10; i += 1) {
//            double numberBetweenZeroAndOne = StdRandom.uniform();
//            if (numberBetweenZeroAndOne < 0.1) {
//                fromsad4.addFirst(sad4.removeFirst());
//                fromads4.addFirst(ads4.removeFirst());
//            } else {
//                fromsad4.addLast(sad4.removeLast());
//                fromads4.addLast(ads4.removeLast());
//            }
//        }
//        System.out.println("student array");
//        sad4.printDeque();
//        for (int i = 0; i < 10; i += 1) {
//            System.out.print(ads4.get(i));
//        }
//        System.out.println("");
//        for (int i = 0; i < 10; i += 1) {
//            int actual = sad4.get(i);
//            int expected = ads4.get(i);
//            assertEquals(expected, actual);
//            assertEquals("student answer was" + actual + "correct one should be" + expected,
//                    expected, actual);
//        }
//
//        for (int i = 0; i < 10; i += 1) {
//            Integer actual = sad4.removeFirst();
//            Integer expected = ads4.removeFirst();
//            // System.out.println(i);
//            assertEquals("student answer was" + actual + "correct one should be" + expected,
//                    expected, actual);
//        }
//    }

    @Test
    public void testRemoveLast() {
        ArrayDequeSolution<Integer> solutionArr = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> studentArr = new StudentArrayDeque<>();
        int randomNum = StdRandom.uniform(100);
        studentArr.addFirst(randomNum);
        solutionArr.addFirst(randomNum);
        assertEquals("addFirst(" + randomNum + ")", solutionArr.get(0),
                studentArr.get(0));
        System.out.println("addFirst(" + randomNum + ")");

        randomNum = StdRandom.uniform(100);
        studentArr.addLast(randomNum);
        solutionArr.addLast(randomNum);
        assertEquals("addLast(" + randomNum + ")", solutionArr.get(1),
                studentArr.get(1));
        System.out.println("addLast(" + randomNum + ")");

        studentArr.printDeque();
        int actual = studentArr.removeFirst();
        int expected = solutionArr.removeFirst();
        assertEquals("removeFirst()", actual, expected);
        System.out.println(actual + "+" + expected);
        System.out.println("removeFirst()");


        actual = studentArr.removeLast();
        expected = solutionArr.removeFirst();
        assertEquals("removeLast()", actual, expected);
        System.out.println("removeLast()");

//        for (int i = 0; i < 10; i += 1) {
//            Integer actual2 = studentArr.removeFirst();
//            Integer expected2 = solutionArr.removeFirst();
//            assertEquals("student answer was" + actual2 + "correct one should be" + expected2,
//                    expected2, actual2);
//          }
    }

    @Test
    public void testAnother() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        int random = StdRandom.uniform(100);
        ads.addFirst(random);
        sad.addFirst(random);
        assertEquals("addFirst(" + random + ")", ads.get(0), sad.get(0));
        System.out.println("addFirst(" + random + ")");

        random = StdRandom.uniform(100);
        ads.addLast(random);
        sad.addLast(random);
        assertEquals("addLast(" + random + ")", ads.get(1), sad.get(1));
        System.out.println("addLast(" + random + ")");

        sad.printDeque();
        int actual = ads.removeFirst();
        int expected = sad.removeFirst();
        assertEquals("removeFirst()", actual, expected);
        System.out.println("removeFirst()");

        actual = ads.removeLast();
        expected = sad.removeLast();
        assertEquals("removeLast()", actual, expected);
        System.out.println("removeLast()");
    }

    @Test
    public void testArratDeque() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        // addLast
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            ads.addLast(random);
            sad.addLast(random);
        }
        for (int i = 0; i < 10; i++) {
            int actual = ads.get(i);
            int expected = sad.get(i);
            assertEquals("Oh noooo!\nThis is bad in addLast():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }

        // addFirst
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            ads.addFirst(random);
            sad.addFirst(random);
        }
        for (int i = 0; i < 10; i++) {
            int actual = ads.get(i);
            int expected = sad.get(i);
            assertEquals("Oh noooo!\nThis is bad in addFirst():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }

        // removeFirst
        List<Integer> actualList = new ArrayList<>();
        List<Integer> expectedList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actualList.add(ads.removeFirst());
            expectedList.add(sad.removeFirst());
        }
        for (int i = 0; i < 10; i++) {
            int actual = ads.get(i);
            int expected = sad.get(i);
            assertEquals("Oh noooo!\nThis is bad in removeFirst():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }
        for (int i = 0; i < 10; i++) {
            int actual = actualList.get(i);
            int expected = expectedList.get(i);
            assertEquals("Oh noooo!\nThis is bad in removeFirst():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }

        // removeLast
        actualList.clear();
        expectedList.clear();
        for (int i = 0; i < 10; i++) {
            actualList.add(ads.removeLast());
            expectedList.add(sad.removeLast());
        }
        int actual = ads.size();
        int expected = sad.size();
        assertEquals("Oh noooo!\nThis is bad in removeLast():\n   actual size " + actual
                        + " not equal to " + expected + "!",
                expected, actual);
        for (int i = 0; i < 10; i++) {
            assertEquals("Oh noooo!\nThis is bad in removeLast():\n   Random number "
                            + actualList.get(i) + " not equal to " +  expectedList.get(i) + "!",
                    expectedList.get(i), actualList.get(i));
        }
    }

}
