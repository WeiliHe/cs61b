/* LinkedListDeque Class*/
public class LinkedListDeque<T> {

	private class StuffNode {
		private T item;
		private StuffNode prev;
		private StuffNode next;

		private StuffNode(T i, StuffNode p, StuffNode n) {
			item = i;
			prev = p;
			next = n;
		}
	}

	private StuffNode sentinel;
	private int size;

	// create empty list
	public LinkedListDeque() {
		sentinel = new StuffNode(null, null, null);
		sentinel.prev = sentinel;
		sentinel.next = sentinel;
		size = 0;
	}

	// create list
	public LinkedListDeque(T x) {
		sentinel = new StuffNode(null, null, null);
		sentinel.next = new StuffNode(x, sentinel, sentinel);
		sentinel.prev = sentinel.next;
		size = 1;
	}
	// create a deep copy of the other
	public LinkedListDeque(LinkedListDeque other) {
		sentinel = new StuffNode(null, null, null);
		sentinel.prev = sentinel;
		sentinel.next = sentinel;
		size = 0;
		// adding the T before the other.get() is for casting the other to T Type
		for (int i = 0; i < other.size; i++) {
			addLast((T) other.get(i));
		}
	}

	public void addFirst(T x) {
		sentinel.next = new StuffNode(x, sentinel, sentinel.next);
		sentinel.next.next.prev = sentinel.next;
		size = size + 1;

	}

	public void addLast(T x) {
		sentinel.prev.next = new StuffNode(x, sentinel.prev, sentinel);
		sentinel.prev = sentinel.prev.next;
		size = size + 1;
	}

	public boolean isEmpty() {
		return sentinel.next == sentinel;
	}

	public int size() {
		return size;
	}

	public void printDeque() {
		StuffNode currentNode = sentinel.next;
		while (currentNode != sentinel) {
			System.out.print(currentNode.item);
			currentNode = currentNode.next;
		}
		System.out.println(' ');
	}

	// is if statement necessary?
	public T removeFirst() {
		if (size == 0) {
			return null;
		} else {
			StuffNode removeNode = sentinel.next;
			sentinel.next = sentinel.next.next;
			sentinel.next.prev = sentinel;
			size = size - 1;
			return removeNode.item;
		}
	}

	public T removeLast() {
		if (size == 0) {
			return null;
		} else {
			StuffNode removeNode = sentinel.prev;
			sentinel.prev = sentinel.prev.prev;
			sentinel.prev.next = sentinel;
			size = size - 1;
			return removeNode.item;
		}
	}

	public T get(int index) {
		if (size == 0 || index > size) {
			return null;
		} else {
			StuffNode currentNode = sentinel.next;
			while (index > 0) {
				index -= 1;
				currentNode = currentNode.next;
			}
			return currentNode.item;
		}
	}

	private T getRecursiveHelper(int index, StuffNode currentNode) {
		if (index == 0) {
			return currentNode.item;
		} else {
			return getRecursiveHelper(index - 1, currentNode.next);
		}
	}

	public T getRecursive(int index) {
		if (size == 0 || index > size) {
			return null;
		} else {
			return getRecursiveHelper(index, sentinel.next);
		}
	}

	public static void main(String[] args){
		LinkedListDeque a = new LinkedListDeque();
		a.addLast(0);
		a.addLast(1);
		a.addFirst(2);
		System.out.print(a.getRecursive(0));
		a.removeFirst();
		a.removeFirst();
		a.addLast(6);
		a.addFirst(7);
		a.removeLast();
		a.removeFirst();
		a.addFirst(10);
		a.getRecursive(1);
	}
}
