import edu.princeton.cs.algs4.Queue;
import java.lang.Math;
public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     *
     * This method should take linear time and will result in "items" being empty
     *
     * @param   items  A Queue of items.
     * @return         A Queue of queues, each containing an item from items.
     *
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue queueOfQueues = new Queue<Queue<Item>>();
        for (Item item: items) {
            Queue x = new Queue<Item>();
            x.enqueue(item);
            queueOfQueues.enqueue(x);
        }
        return queueOfQueues;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2;
        }
        if (q2.isEmpty()) {
            return q1;
        }
        Queue mergeQueue = new Queue<Item>();
        int size = q1.size() + q2.size();
        while (mergeQueue.size() < size){
            Item min = getMin(q1, q2);
            mergeQueue.enqueue(min);
        }
        return mergeQueue;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     *
     * This method should take roughly nlogn time where n is the size of "items"
     * running this method will result in "items" being emptied into the returned queue
     *
     * @param   items  A Queue to be sorted.
     * @return         A Queue containing every item in "items".
     *
     */
//    private static <Item extends Comparable> Queue<Item> recursiveCall( Queue<Queue<Item>> items) {
//        if (items.size() == 1) {
//            return items.dequeue();
//        }
//        if (items.size() == 0) {
//            return null;
//        }
//        else {
//            Queue left = new Queue<Queue<Item>>();
//            Queue right = new Queue<Queue<Item>>();
//            for (int i = 0; i < items.size()/2; i++) {
//                left.enqueue(items.dequeue());
//            }
//            right = items;
//            left = recursiveCall(left);
//            right = recursiveCall(right);
//
//            Queue<Queue<Item>> left = items.return
//        }
//        return null;
//    }

    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if (items == null) {
            throw new IllegalArgumentException("input is null");
        }
        Queue<Queue<Item>> qq  = makeSingleItemQueues(items);
        Queue temp = new Queue<Queue<Item>>();
        while (qq.size() > 1) {
            int i = 0;
            int size = qq.size();
            while (i < Math.ceil((float) size / 2.0)) {
                Queue left = qq.dequeue();
                Queue right = qq;
                if (!qq.isEmpty()) {
                    right = qq.dequeue();
                }
                temp.enqueue(mergeSortedQueues(left, right));
                i++;
            }
            qq = temp;
        }
        return qq.dequeue();
    }
}
