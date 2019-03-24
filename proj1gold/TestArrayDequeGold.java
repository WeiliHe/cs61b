import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test
    public void testArrayDeque() {
        // StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        // ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
        // // addLast test
        // for (int i = 0; i < 10; i += 1) {
        //     int numberBetweenZeroAndTen = StdRandom.uniform(11);
        //     sad1.addLast(numberBetweenZeroAndTen);
        //     ads1.addLast(numberBetweenZeroAndTen);
        // }
        // // System.out.println("student array");
        // // sad1.printDeque();
        // // System.out.println("solution array");
        // // ads1.printDeque();
        // // get the item and see whether they ara equal
        // for (int i = 0; i < 10; i += 1) {
        //     int actual = sad1.get(i);
        //     int expected = ads1.get(i);
        //     assertEquals("student answer was" + actual + "correct one should be" + expected, expected, actual);
        // }


        // // addFirst Test
        // StudentArrayDeque<Integer> sad2 = new StudentArrayDeque<>();
        // ArrayDequeSolution<Integer> ads2 = new ArrayDequeSolution<>();
        // for (int i = 0; i < 10; i += 1) {
        //     int numberBetweenZeroAndTen = StdRandom.uniform(11);
        //     sad2.addFirst(numberBetweenZeroAndTen);
        //     ads2.addFirst(numberBetweenZeroAndTen);
        // }
        // // System.out.println("student array");
        // // sad1.printDeque();
        // // System.out.println("solution array");
        // // ads1.printDeque();
        // // get the item and see whether they ara equal
        // for (int i = 0; i < 10; i += 1) {
        //     int actual = sad1.get(i);
        //     int expected = ads1.get(i);
        //     assertEquals("student answer was" + actual + "correct one should be" + expected, expected, actual);
        // }

        // // random addLast addFirst
        // StudentArrayDeque<Integer> sad3 = new StudentArrayDeque<>();
        // ArrayDequeSolution<Integer> ads3 = new ArrayDequeSolution<>();
        // // addLast test
        // for (int i = 0; i < 10; i += 1) {
        //     double numberBetweenZeroAndOne = StdRandom.uniform();  
        //     if (numberBetweenZeroAndOne < 0.5) {
        //     sad3.addFirst(i);
        //     ads3.addFirst(i);                
        //     } else {
        //         sad3.addLast(i);
        //         ads3.addLast(i); 
        //     }
        // }
        // // System.out.println("student array");
        // // sad3.printDeque();
        // // System.out.println("solution array");
        // // ads3.printDeque();
        // // get the item and see whether they ara equal
        // for (int i = 0; i < 10; i += 1) {
        //     int actual = sad3.get(i);
        //     int expected = ads3.get(i);
        //     assertEquals("student answer was" + actual + "correct one should be" + expected, expected, actual);
        // }

        // test remove
        StudentArrayDeque<Integer> sad4 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads4 = new ArrayDequeSolution<>();
        // addLast test
        for (int i = 0; i < 20; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();  
            if (numberBetweenZeroAndOne < 0.5) {
            sad4.addFirst(i);
            ads4.addFirst(i);                
            } else {
                sad4.addLast(i);
                ads4.addLast(i); 
            }
        }
        // System.out.println("student array");
        // sad4.printDeque();
        // System.out.println("solution array");
        // ads4.printDeque();
        // get the item and see whether they ara equal
        StudentArrayDeque<Integer> fromsad4 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> fromads4 = new ArrayDequeSolution<>();
        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();  
            if (numberBetweenZeroAndOne < 0.1) {
                fromsad4.addFirst(sad4.removeFirst());
                fromads4.addFirst(ads4.removeFirst());
            } else {
                fromsad4.addLast(sad4.removeLast());
                fromads4.addLast(ads4.removeLast());
            }
        }
        System.out.println("student array");
        sad4.printDeque();
        for (int i = 0; i < 10; i += 1) {
            System.out.print(ads4.get(i));
        }
        System.out.println("");
        for (int i = 0; i < 10; i += 1) {
            int actual = sad4.get(i);
            int expected = ads4.get(i);
            assertEquals(expected, actual);
            assertEquals("student answer was" + actual + "correct one should be" + expected, expected, actual);
        }

        for (int i = 0; i < 10; i += 1) {
            int actual = fromsad4.get(i);
            int expected = fromsad4.get(i);
            assertEquals("student answer was" + actual + "correct one should be" + expected, expected, actual);
        }

        for (int i = 0; i < 10; i += 1) {
            Integer actual = sad4.removeFirst();
            Integer expected = ads4.removeFirst();
            // System.out.println(i);
            assertEquals("student answer was" + actual + "correct one should be" + expected, expected, actual);
        }
        System.out.println("student array");
        sad4.printDeque();
    }

    @Test
    public void testRemoveLast() {
        StudentArrayDeque<Integer> studentArr = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionArr = new ArrayDequeSolution<>();
        // addLast test
        for (int i = 0; i < 20; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();  
            if (numberBetweenZeroAndOne < 0.5) {
            studentArr.addFirst(i);
            solutionArr.addFirst(i);                
            } else {
                studentArr.addLast(i);
                 solutionArr.addLast(i); 
            }
        }
        System.out.println("student array");
        studentArr.printDeque();
        for (int i = 0; i < 20; i += 1) {
            System.out.print(solutionArr.get(i));
        }

        for (int i = 0; i < 10; i += 1) {
            Integer actual = studentArr.removeLast();
            Integer expected = solutionArr.removeLast();
            // System.out.println(i);
            assertEquals("student answer was" + actual + "correct one should be" + expected, expected, actual);
        }

    }
} 