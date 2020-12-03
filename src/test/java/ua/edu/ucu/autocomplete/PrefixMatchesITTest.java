
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;

/**
 *
 * @author Andrii_Rodionov
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithSmallPrefix_String() {
        String pref = "a";

        Iterable<String> result = pm.wordsWithPrefix(pref);
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_Delete() {
        pm.delete("abce");

        Iterable<String> result = pm.wordsWithPrefix("ab");

        String[] expResult = {"abc", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_Contains() {
        assertTrue(pm.contains("abc"));
    }

    @Test
    public void testWordsWithPrefix_NotContains() {
        pm.delete("abce");
        assertFalse(pm.contains("abce"));
    }

    @Test
    public void testLoadsRow() {
        PrefixMatches pm2 = new PrefixMatches(new RWayTrie());
        pm2.load("abc abd", "aga abg");
        String pref = "ab";

        Iterable<String> result = pm2.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abd", "abg"};

        assertThat(result, containsInAnyOrder(expResult));

    }
}
