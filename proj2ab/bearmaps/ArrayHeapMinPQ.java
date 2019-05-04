package bearmaps;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.HashSet;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private PriorityNode[] items;
    private final static int INITIAL_LENGTH = 8;
    private final static double EMPTY_FACTOR = 0.25;
    private final static int FACTOR = 2;
    private int n;  // how many numbers added to the MinPQ
    private HashMap<T, Integer> indexOfKey;  // store the index of the node in array
    private HashSet<T> keys; //store the key in a HashSet, for faster call in contains

    public ArrayHeapMinPQ() {
        items = new ArrayHeapMinPQ.PriorityNode[INITIAL_LENGTH];
        n = 0;
        indexOfKey = new HashMap<>();
        keys = new HashSet<>();
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
        indexOfKey.put(item, n);
        keys.add(item);
        items[n] = node;
        swim(n);
        n++;
    }

    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
//            compare to the smaller one of children
            if (j < n && greater(j, j+1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private void exch(int i, int j) {
        PriorityNode swap = items[i];
        items[i] = items[j];
        items[j] = swap;
        indexOfKey.put(items[i].getItem(), i);
        indexOfKey.put(items[j].getItem(), j);
    }

    private boolean greater(int i, int j) {
        if (items[i].compareTo(items[j]) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(T item) {
        return keys.contains(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return items[1].getItem();
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode last = items[n];
        T smallestItem = items[1].getItem();
        items[n] = null;
        items[1] = last;
        n -= 1;
        keys.remove(smallestItem);
        indexOfKey.remove(smallestItem);
        sink(1);
        return smallestItem;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (contains(item) == false) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int index = indexOfKey.get(item);
        items[index].setPriority(priority);
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return n;
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
