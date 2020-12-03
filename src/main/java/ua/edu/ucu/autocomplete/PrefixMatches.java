package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;


/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (String element: strings) {
            String[] words = element.split(" ");
            for (String word: words) {
                if (word.length() > 2) {
                    trie.add(new Tuple(word, word.length()));
                }
            }
        }
        return trie.size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < 2) {
            throw new IllegalArgumentException("Size of pref is less then 2");
        }
        Iterable<String> allWordsWithPrefix = trie.wordsWithPrefix(pref);
        return allWordsWithPrefix;
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2) {
            throw new IllegalArgumentException("Size of pref is less then 2");
        }
        Iterable<String> allWordsWithPrefix = trie.wordsWithPrefixK(pref, k+2);
        return allWordsWithPrefix;
    }

    public int size() {
        return trie.size();
    }
}
