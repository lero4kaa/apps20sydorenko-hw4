package ua.edu.ucu.tries;

import ua.edu.ucu.immutable.Queue;

public class RWayTrie implements Trie {
    private static final int R = 26; // radix
    private static final int asciiFirstLetter = 97;
    private Node root = new Node(); // root of trie
    private int size = 0;

    private static class Node
    {
        private Object val;
        private Node[] next = new Node[R];
    }

    @Override
    public void add(Tuple t) {
        root = put(root, t.term, t.weight, 0);
        size ++;
    }

    private Node put(Node x, String key, int val, int d)
    {
        if (x == null) x = new Node();
        if (d == key.length()) { x.val = val; return x; }
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        x.next[c - asciiFirstLetter] = put(x.next[c - asciiFirstLetter], key,
                                           val, d+1);
        return x;
    }

    @Override
    public boolean contains(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Null value");
        }

        return get(word) != null;
    }

    public Object get(String key)
    {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    private Node get(Node x, String key, int d)
    { // Return node associated with key in the subtrie rooted at x.
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        return get(x.next[c - asciiFirstLetter], key, d+1);
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)){
            root = delete(root, word, 0);
            return true;
        }
        return false;
    }

    private Node delete(Node x, String key, int d)
    {
        if (x == null) return null;
        if (d == key.length())
            x.val = null;
        else
        {
            char c = key.charAt(d);
            x.next[c - asciiFirstLetter] = delete(x.next[c - asciiFirstLetter], key, d+1);
        }
        if (x.val != null) return x;
        for (char c = asciiFirstLetter; c < R+asciiFirstLetter; c++)
            if (x.next[c-asciiFirstLetter] != null) return x;
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    private void collect(Node x, String pre, Queue q)
    {
        if (x == null) return;
        if (x.val != null) q.enqueue(pre);
        for (char c = asciiFirstLetter; c < R+asciiFirstLetter; c++)
            collect(x.next[c-asciiFirstLetter], pre + c, q);
    }

    private void collect(Node x, String pre, Queue q, int k)
    {
        if (x == null) return;
        if ((x.val != null) & ((int) x.val <= k)) q.enqueue(pre);
        for (char c = asciiFirstLetter; c < R+asciiFirstLetter; c++)
            collect(x.next[c-asciiFirstLetter], pre + c, q, k);
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root, s, 0), s, q);
        return q::iterator;
    }

    @Override
    public Iterable<String> wordsWithPrefixK(String s, int k) {
        Queue q = new Queue();
        collect(get(root, s, 0), s, q, k);
        return q::iterator;
    }

    @Override
    public int size() {
        return this.size;
    }

}
