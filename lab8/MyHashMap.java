import java.util.*;
import java.util.ArrayList;
public class MyHashMap<K, V> implements Map61B<K, V> {
	private static final double INITIAL_LOAD_FACTOR = 0.75;
	private static final int INITIAL_SIZE = 16;
	private static final int AVG_BUCKET_SIZE = 10; // for seperate chaining, no longer than this size;
	private double loadFactor = 0.75; // I don't understand, if u use separate chaining, why do u use loadFactor
	private HashSet<K> keys;
	private HashSet<Integer> occupiedBuckets; // store the already used buckets;
	private int n; // number of key value pair
	private int m; // number of buckets
	private ArrayList<ArrayList<Entry<K, V>>> buckets;  // ArrayList of ArrayList, because the bucket needs to be resized

	private static class Entry<K, V> {
		public K key;
		public V value;
//		public Entry<K, V> next;
		public Entry (K key, V value) {
			this.key = key;
			this.value = value;
//			this.next = next;
		}
		public K getKey() {
			return key;
		}
		public V getValue() {
			return value;
		}
		public V setValue(V x) {
			V old = value;
			value = x;
			return value;
		}
		public int hashCode() {
			return (this.getKey() == null   ? 0 : this.getKey().hashCode()) ^
					(this.getValue() == null ? 0 : this.getValue().hashCode());
		}
		public boolean equals(Object o) {
			Entry that = (Entry) o;
			return (this.getKey() == null ?
					that.getKey() == null : this.getKey().equals(that.getKey()))  &&
					(this.getValue() == null ?
							that.getValue() == null : this.getValue().equals(that.getValue()));
		}
	}

	public MyHashMap() {
		this(INITIAL_SIZE, INITIAL_LOAD_FACTOR);
	}

	public MyHashMap(int initialSize) {
		this(initialSize, INITIAL_LOAD_FACTOR);
	}

	public MyHashMap(int initialSize, double loadFactor) {
		if (initialSize < 1 || loadFactor <= 0.0) {
			throw new IllegalArgumentException();
		}
//		see how this initialization works
		buckets = (ArrayList<ArrayList<Entry<K,V>>>) new ArrayList();
		for (int i = 0; i < initialSize; i++) {
			ArrayList<Entry<K,V>> bucket = new ArrayList<>();
			buckets.add(bucket);
		}
		this.loadFactor  = loadFactor;
		this.m  = initialSize;
		this.n = 0;
		keys = new HashSet<>();
		occupiedBuckets = new HashSet<>();
	}

	public void clear() {
		MyHashMap<K, V> temp  = new MyHashMap<>(INITIAL_SIZE);
		this.m = temp.m;
		this.n = temp.n;
		this.buckets = temp.buckets;
		this.keys = temp.keys;
		this.occupiedBuckets = temp.occupiedBuckets;
	}

	public boolean containsKey(K key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to contains() is null");
		}
		return get(key) != null;
	}

	public V get(K key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to contains() is null");
		}
		int i = hash(key);
		Entry e = find(key, buckets.get(i));
		return (e == null) ? null : (V) e.value;
	}

//	finding the position of the bucket for key
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}

//	helper method for get
	private Entry<K, V> find(K key, ArrayList<Entry<K, V>> bucket) {
		if (bucket == null) {
			return null;
		}
		for (Entry<K, V> entry: bucket) {
			if (entry.getKey().equals(key)) {
				return entry;
			}
		}
		return null;
	}

	public int size() {
		return n;
	}

	private void resize(int bucketsSize) {
		MyHashMap<K, V> temp  = new MyHashMap<>(bucketsSize);
		for (int i = 0; i < m; i++) {
			for (Entry e: buckets.get(i)) {
				temp.put((K)e.key, (V)e.value);
			}
		}
		this.m = temp.m;
		this.n = temp.n;
		this.buckets = temp.buckets;
		this.keys = temp.keys;
		this.occupiedBuckets = temp.occupiedBuckets;
	}

	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("argument to contains() is null");
		}
//		double the size if larger than the loadFactor
		int i = hash(key);
		if (occupiedBuckets.size() == 0) {
			occupiedBuckets.add(i);
		} else {
			if (occupiedBuckets.contains(i) == false) {
				occupiedBuckets.add(i);
				if (n >= m * AVG_BUCKET_SIZE) {
					resize(2 * m);
				}
			}
		}
		ArrayList<Entry<K, V>> bucket = buckets.get(i);
		Entry<K, V> e = find(key, bucket);
		if (e == null) {
			bucket.add(new Entry<K, V>(key, value));
			n += 1;
			keys.add(key);
		} else {
			int index = bucket.indexOf(e); // index in the bucket.
			bucket.set(index, new Entry(key, value));
		}
	}

	public Set<K> keySet() {
		return keys;
	}

	public V remove(K key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to delete() is null");
		}
		int i = hash(key);
		ArrayList<Entry<K, V>> bucket = buckets.get(i);
		Entry e = find(key, bucket);
		V value = (V) e.value;
		if (e != null) {
			bucket.remove(e);
			return value;
		} else {
			return null;
		}
	}

	public V remove(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("argument to delete() is null");
		}
		int i = hash(key);
		ArrayList<Entry<K, V>> bucket = buckets.get(i);
		Entry e = find(key, bucket);
		Entry<K, V> curEntry = new Entry<>(key, value);
		if (e.equals(curEntry)) {
			V returnValue = value;
			bucket.remove(e);
			return returnValue;
		} else {
			return null;
		}
	}

	public Iterator<K> iterator() {
		return keys.iterator();
	}
}