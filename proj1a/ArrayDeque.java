/**Array Deque 
always leave the nextfirst and nextlast to be empty
*/

public class ArrayDeque<T> {
	/**
	 * Declare Variables
	 */
	private T[] items;
	private int size;
	private double usageRatio;
	private int nextfirst;
	private int nextlast;
	private final double leastUsage = 0.25;
	private final int factor = 2;

	// create an empty list, pick a random start point
	public ArrayDeque() {
		items = (T[]) new Object[8];
		size = 0;
		usageRatio = (double) size / (double) items.length;
		nextfirst = 3;
		nextlast = 4;
	}

	// create a deep copy of the other
	public ArrayDeque(ArrayDeque other) {
		items = (T[]) new Object[other.size() + 2];
		nextfirst = 0;
		nextlast = 1;
		size = 0;
		for (int i = 0; i < other.size; i++) {
			addLast((T) other.get(i));
		}
		usageRatio = (double) size / (double) items.length;
	}

	private void resize() {
		if (size == items.length - 2) {
			expand(items.length * factor);
		}

		if (usageRatio < leastUsage && items.length > 8) {
			reduce(items.length / factor);
		}
	}

	// expand the size
	private void expand(int capacity) {
		T[] a = (T[]) new Object[capacity];
		if (nextfirst < nextlast) {
			System.arraycopy(items, 1, a, 1, size);
		} else {
			System.arraycopy(items, 0, a, 0, nextlast);
			System.arraycopy(items, nextfirst + 1, a, capacity - (items.length - 1 - nextfirst),
					(items.length - 1 - nextfirst));
			nextfirst = capacity - (items.length - 1 - nextfirst) - 1;
		}
		items = a;
		usageRatio = (double) size / (double) items.length;
	}

	// reduce the size
	private void reduce(int capacity) {
		T[] a = (T[]) new Object[capacity];
		if (nextfirst < nextlast) {
			System.arraycopy(items, nextfirst + 1, a, 1, nextlast - nextfirst - 1);
			nextlast = nextlast - nextfirst;
			nextfirst = 0;
		} else {
			System.arraycopy(items, 0, a, 0, nextlast);
			System.arraycopy(items, nextfirst + 1, a, capacity - (items.length - nextfirst - 1),
					items.length - nextfirst - 1);
			nextfirst = capacity - (items.length - 1 - nextfirst) - 1;
		}
		items = a;
		usageRatio = (double) size / (double) items.length;
	}

	// helper funtion to round the nextfirst or nextlast
	private int minusOne(int nextnum) {
		if (nextnum == 0) {
			return items.length - 1;
		} else {
			return nextnum - 1;
		}
	}

	private int plusOne(int nextnum) {
		if (nextnum == items.length - 1) {
			return 0;
		} else {
			return nextnum + 1;
		}
	}

	public void addFirst(T item) {
		resize();
		items[nextfirst] = item;
		nextfirst = minusOne(nextfirst);
		size = size + 1;
		usageRatio = (double) size / (double) items.length;
	}

	public void addLast(T item) {
		resize();
		items[nextlast] = item;
		nextlast = plusOne(nextlast);
		size = size + 1;
		usageRatio = (double) size / (double) items.length;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void printDeque() {
		int cur = plusOne(nextfirst);
		while (items[cur] != null && cur != nextlast) {
			System.out.print(items[cur]);
			cur = plusOne(cur);
		}
		System.out.println(' ');
	}

	public T removeFirst() {
		if (size == 0) {
			return null;
		}
		resize();
		nextfirst = plusOne(nextfirst);
		T itemRomove = items[nextfirst];
		items[nextfirst] = null;
		size = size - 1;
		usageRatio = (double) size / (double) items.length;
		return itemRomove;
	}

	public T removeLast() {
		if (size == 0) {
			return null;
		}
		resize();
		nextlast = minusOne(nextlast);
		T itemRomove = items[nextlast];
		items[nextlast] = null;
		size = size - 1;
		usageRatio = (double) size / (double) items.length;
		return itemRomove;
	}

	public T get(int index) {
		if (size == 0 || index > size - 1) {
			return null;
		} else {
			if (nextfirst + 1 + index > items.length - 1) {
				return items[index - (items.length - 1 - nextfirst)];
			} else {
				return items[nextfirst + 1 + index];
			}
		}
	}

	public static void main(String[] args) {
		ArrayDeque a = new ArrayDeque();
		a.addLast(0);
		a.addFirst(1);
		a.get(1);
		a.addLast(3);
		a.get(2);
		a.get(2);
		a.get(1);
		a.addFirst(7);
		a.addLast(8);
		System.out.println(a.get(3));
		a.addFirst(10);
		a.addFirst(11);
		a.get(4);
		a.addFirst(13);
		a.removeLast();
		a.printDeque();
		ArrayDeque b = new ArrayDeque(a);
		b.removeFirst();
		b.printDeque();
	}
}