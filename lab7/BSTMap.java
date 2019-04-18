import java.lang.invoke.VarHandle;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size; /*number of nodes in subtree*/
        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }
    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    private V get(Node x, K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left =  put(x.left, key, val);
        } else if (cmp > 0) {
            x.right =  put(x.right, key, val);
        } else {
            x.val = val;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    @Override
    public void put(K key, V val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (val == null) {
            remove(key);
            return;
        }
        root = put(root, key, val);
    }

    private Set<K> keySet(Node x, Set<K> set) {
        if (x == null) {
            return set;
        }
        set = keySet(x.left, set);
        set.add(x.key);
        set = keySet(x.right, set);
        return set;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        return keySet(root, set);
    }

    public K min() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls min() with empty symbol table");
        }
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        } else {
            return min(x.left);
        }
    }

    public K max() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls min() with empty symbol table");
        }
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        } else {
            return min(x.right);
        }
    }

    public void removeMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        root = removeMax(root);
    }

    private Node removeMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = removeMax(x.right);
        x.size = size(x.left) + size(x.right);
        return x;
    }

    public void removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        root = removeMin(root);
    }

    private Node removeMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = removeMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node remove(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(x.left, key);
        } else if (cmp > 0) {
            x.right = remove(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            Node t = x;
            x = min(t.right);
            x.right = removeMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }


    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        V removeValue = get(key);
        root = remove(root, key);
        return removeValue;
    }

    @Override
    public V remove(K key, V val) {
        if (key == null) {
            return null;
        }
        if (get(key) != val) {
            return null;
        }
        V removeValue = get(key);
        root = remove(root, key);
        return removeValue;
    }

    public void printInOrder() {
        return;
    }

    public Iterator<K> iterator() {
        Set<K> keySetIter = keySet();
        return keySetIter.iterator();
    }
}
