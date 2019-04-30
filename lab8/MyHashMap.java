import java.util.*;
import java.util.ArrayList;
public class MyHashMap<K, V> implements Map61B<K, V> {
	private double loadFactor = 0.75;
	private double initialSize = 16;
	private HashSet<K> keys;
	private int n; // number of key value pair
	private int m; // number of buckets
	private ArrayList<Entry<K, V>> bukets;

	private static class Entry<K, V> {
		K key;
		V value;
		Entry<K, V> next;
		private Entry (K key, V value, Entry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
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
			return -1;
		}
		public boolean equals() {
			return false;
		}
	}
	public void clear() {
		return;
	}

	public boolean containsKey(K key) {
		return;
	}

	public V get(K key) {
		return;
	}

	public int size() {
		return size;
	}

	public void put(K key, V value) {
		return;
	}

	public Set<K> keySet() {
		return;
	}

	public V remove(K key) {
		return;
	}

	public V remove(K key, V value) {
		throw new UnsupportedOperationException();
	}

	public Iterator<K> iterator() {
		return;
	}
}