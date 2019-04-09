package es.datastructur.synthesizer;
import java.util.Iterator;
import java.util.Objects;
import java.util.ArrayList;


public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    private int cap;
    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */

    @Override 
    public int capacity() {
        return cap;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        cap = capacity;
    }

    private int minusOne(int index) {
        if (index == 0) {
            return cap - 1;
        } else {
            return index - 1;
        }
    }

    private int plusOne(int index) {
        if (index == cap - 1) {
            return 0;
        } else {
            return index + 1;
        }
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            rb[last] = x;
            fillCount += 1;
            last = plusOne(last);
        }
        return;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T dequeueNum = rb[first];
            rb[first] = null;
            fillCount -= 1;
            first = plusOne(first);
            return dequeueNum;
        }

    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return rb[first];
        }
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos;
        private int count;
        ArrayRingBufferIterator() {
            count = 0;
            pos = first;
        }
        @Override
        public boolean hasNext() {
            return count < fillCount();
        }

        @Override 
        public T next() {
            T returnItem = rb[pos];
            count += 1;
            if (pos == cap - 1) {
                pos = 0;
            } else {
                pos += 1;
            }
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
//        casting here?
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if (this.fillCount() != other.fillCount()) {
            return false;
        }

        ArrayList itemThis = new ArrayList();
        ArrayList itemOther = new ArrayList();
        for (T item: this) {
            itemThis.add(item);
        }
        for (T item: other) {
            itemOther.add(item);
        }
        for (int i = 0; i < itemThis.size(); i++) {
            if (!Objects.equals(itemThis, itemOther)) {
                return false;
            }
        }
        return true;
    }

}

