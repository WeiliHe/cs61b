import java.util.HashMap;

public class MyTrieSet implements TrieSet61B {
    private Node root;
    private int n;

    @Override
    public void clear(){
        root = null;
    }

    @Override
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    private Character get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length() && x.isKey) {
            return x;
        }
        char c = key.charAt(d);
        return get(x.map.get(c), key, d+1);
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    private static class Node {
        char val;
        boolean isKey;
        HashMap<Character, Node> map;
        public Node(char val, boolean isKey) {
            this.val = val;
            this.isKey = isKey;
        }
    }
}
