import java.lang.invoke.VarHandle;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V>{
    private Node root;
    private class Node{
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
        return;
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

    public Set<K> keySet(Node x) {
        if (x.key == null) {
            return null;
        }

    }

    @Override
    public Set<K> keySet() {
        if (root == null) {
            return null;
        } else
    }

    @Override
    public V remove (K key) {
        return;
    }

    @Override
    public V remove(K key, V value) {
        return;
    }

    public void printInOrder() {
        return;
    }
}
