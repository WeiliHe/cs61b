/* LinkedListDeque Class*/
public class LinkedListDeque<LochNess> {

	private class StuffNode {
		private LochNess item;
		private StuffNode prev;
		private StuffNode next;

		private StuffNode(LochNess i, StuffNode p, StuffNode n) {
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
	public LinkedListDeque(LochNess x) {
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
		// adding the Lochness before the other.get() is for casting the other to Lochness Type
		for (int i = 0; i < other.size; i++) {
			addLast((LochNess) other.get(i));
		}
	}

	public void addFirst(LochNess x) {
		sentinel.next = new StuffNode(x, sentinel, sentinel.next);
		sentinel.next.next.prev = sentinel.next;
		size = size + 1;

	}

	public void addLast(LochNess x) {
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
	public LochNess removeFirst() {
		if (size == 0) {
			return null;
		} else {
			StuffNode removeNode = sentinel.next;
			sentinel.next = sentinel.next.next;
			sentinel.next.next.prev = sentinel;
			size = size - 1;
			return removeNode.item;
		}
	}

	public LochNess removeLast() {
		if (size == 0) {
			return null;
		} else {
			StuffNode removeNode = sentinel.prev;
			sentinel.prev.prev.next = sentinel;
			sentinel.prev = sentinel.prev.prev;
			size = size - 1;
			return removeNode.item;
		}
	}

	public LochNess get(int index) {
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

	private LochNess getRecursiveHelper(int index, StuffNode currentNode) {
		if (index == 0) {
			return currentNode.item;
		} else {
			return getRecursiveHelper(index - 1, currentNode.next);
		}
	}

	public LochNess getRecursive(int index) {
		if (size == 0 || index > size) {
			return null;
		} else {
			return getRecursiveHelper(index, sentinel.next);
		}
	}
}
