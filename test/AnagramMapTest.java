import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * Created by erik on 1/9/17.
 */
public class AnagramMapTest {
    @Test
    public void toAlphabeticalString() throws Exception {
        AnagramMap map = new AnagramMap();

        assertEquals("abcdef", map.toAlphabeticalString("fedabc"));
        assertEquals("abcdef", map.toAlphabeticalString("fedcba"));
        assertEquals("aaa", map.toAlphabeticalString("aaa"));
        assertEquals("'az", map.toAlphabeticalString("az'"));
    }

    @Test
    public void insert() throws Exception {
        AnagramMap map = new AnagramMap();

        map.insert("erik");
        map.insert("kire");
        map.insert("riek");

        assertEquals(1, map.size());

        map.insert("abc");
        map.insert("everwise");

        assertEquals(3, map.size());
    }

    @Test
    public void findAnagrams() throws Exception {
        AnagramMap map = new AnagramMap();

        map.insert("erik");
        map.insert("kire");
        map.insert("riek");

        assertArrayEquals(new String[]{"kire", "riek"}, map.findAnagrams("erik").toArray(new String[0]));
        assertArrayEquals(new String[]{"erik", "riek"}, map.findAnagrams("kire").toArray(new String[0]));

        assertNull(map.findAnagrams("xyz"));
    }

    @Test
    /**
     * Runs in approximately 250ms on my MacBook Pro, equally fast to the AnagramTrie solution.
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