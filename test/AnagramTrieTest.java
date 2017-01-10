import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * Created by erik on 1/9/17.
 */
public class AnagramTrieTest {
    @Test
    public void initializesEmpty() throws Exception {
        AnagramTrie trie = new AnagramTrie();
        assertTrue(trie.getRoot() instanceof TrieNode);
        assertNull(trie.getRoot().children);
    }

    @Test
    public void insertsNodes() throws Exception {
        AnagramTrie trie = new AnagramTrie();

        trie.insert("erik");
        assertEquals(1, trie.getRoot().children.size());

        trie.insert("abc");
        assertEquals(2, trie.getRoot().children.size());
    }

    @Test
    public void insertAddsNodesAlphabetically() throws Exception {
        AnagramTrie trie = new AnagramTrie();

        trie.insert("erik");
        assertEquals(1, trie.getRoot().children.size());

        // Same as previous input
        trie.insert("kire");
        assertEquals(1, trie.getRoot().children.size());

        // Same start character as first two inputs
        trie.insert("eri");
        assertEquals(1, trie.getRoot().children.size());

        trie.insert("xyz");
        assertEquals(2, trie.getRoot().children.size());

        trie.insert("xzzzzz");
        assertEquals(2, trie.getRoot().children.size());
    }

    @Test
    public void findAnagrams() throws Exception {
        AnagramTrie trie = new AnagramTrie();

        trie.insert("erik");
        trie.insert("kire");
        trie.insert("riek");

        assertArrayEquals(new String[]{"kire", "riek"}, trie.findAnagrams("erik").toArray(new String[0]));
        assertArrayEquals(new String[]{"erik", "riek"}, trie.findAnagrams("kire").toArray(new String[0]));

        assertNull(trie.findAnagrams("xyz"));
    }

    @Test
    /**
     * Runs in approximately 250ms on my MacBook Pro, equally fast to the AnagramMap solution.
     */
    public void findAnagramsIn355kEnglishDictionary() throws Exception {
        AnagramMap map = new AnagramMap();

        try (BufferedReader br = new BufferedReader(new FileReader("words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                map.insert(line);
            }
        }

        assertArrayEquals(new String[]{"god"}, map.findAnagrams("dog").toArray(new String[0]));
        assertArrayEquals(new String[]{"act"}, map.findAnagrams("cat").toArray(new String[0]));
        assertArrayEquals(new String[]{"discriminator"}, map.findAnagrams("doctrinairism").toArray(new String[0]));

        assertNull(map.findAnagrams("xyz"));
    }

}