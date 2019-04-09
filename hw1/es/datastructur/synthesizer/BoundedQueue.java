package es.datastructur.synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    public Iterator<T> iterator();
    public int capacity();
    public int fillCount();
    public void enqueue(T x);
    public T dequeue();
    public T peek();
    default public boolean isEmpty() {
        return fillCount() == 0;
    }
    default public boolean isFull() {
        return fillCount() == capacity();
    }
}
