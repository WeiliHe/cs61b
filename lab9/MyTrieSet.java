import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        if (x == null | x.isKey == false) {
            return null;
        }
        return x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
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
        n++;
    }

    private void prefixHelp(Node x, String prefix, List keys) {
        if (x == null) {
            return;
        }
        if (x.val != null && x.isKey == true) {
            keys.add(prefix);
        }
        for (char c: x.map.keySet()) {
            prefixHelp(x.map.get(c), prefix + c, keys);
        }
    }

    @Override
    public List<String> keysWithPrefix(String prefix){
        if (prefix== null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        List<String> keys = new ArrayList();
        Node x = get(root, prefix, 0);
        prefixHelp(x, prefix, keys);
        return keys;
    }

    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) {
            return length;
        }
        if (x.val != null) {
            return length;
        }
        if (d == query.length()) {
            return length;
        }
        char c = query.charAt(d);
        return longestPrefixOf(x.map.get(c), query, d+1, length);
    }

    @Override
    public String longestPrefixOf(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        }
        int length = longestPrefixOf(root, key, 0, -1);
        if (length == -1) {
            return null;
        } else {
            return key.substring(0, length);
        }
    }

    private static class Node {
        Character val;
        boolean isKey;
        HashMap<Character, Node> map;
        public Node(char val, boolean isKey) {
            this.val = val;
            this.isKey = isKey;
        }
    }
}
