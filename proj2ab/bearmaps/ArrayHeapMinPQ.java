package bearmaps;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.HashMap;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private PriorityNode[] items;
    private final static int INITIAL_LENGTH = 8;
    private final static double EMPTY_FACTOR = 0.25;
    private final static int FACTOR = 2;
    private int first;
    private int nextLast;
    private int n;  // how many numbers added to the MinPQ
    private HashMap<T, Integer> indexOfKey;  // store the index of the node in array

    public ArrayHeapMinPQ() {
        items = new ArrayHeapMinPQ.PriorityNode[INITIAL_LENGTH];
        first = 1;
        nextLast = 1;
        indexOfKey = new HashMap<>();
    }

    /** Note this method does not throw the proper exception,
     *  otherwise it is painfully slow (linear time).
     */
    private void resize() {
//        the first one is not occupied for any time
        if (n == items.length - 1) {
            resizeHelper(items.length * FACTOR);
        }
        if (n < items.length * EMPTY_FACTOR && items.length > INITIAL_LENGTH) {
            resizeHelper(items.length / FACTOR);
        }
    }

    private void resizeHelper(int capacity) {
        PriorityNode[] tempArray = new ArrayHeapMinPQ.PriorityNode[capacity];
        for (int i = 0; i < items.length; i++) {
            tempArray[i] = items[i];
        }
        this.items = tempArray;
    }


    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        resize();
        PriorityNode node = new PriorityNode(item, priority);
        indexOfKey.put(item, nextLast);
        items[nextLast] = node;
        nextLast += 1;
    }

    @Override
    public boolean contains(T item) {
        return items.contains(new PriorityNode(item, 0));
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return Collections.min(items).getItem();
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        int minInd = indOf(getSmallest());
        return items.remove(minInd).getItem();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (contains(item) == false) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        items.get(indOf(item)).setPriority(priority);
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return items.size();
    }

    private int indOf(T elem) {
        return items.indexOf(new PriorityNode(elem, 0));
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
