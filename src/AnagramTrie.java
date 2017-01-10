/**
 * Created by erik on 1/9/17.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A common Trie structure, but with alphabetical ordering of characters to store anagrams
 */
public class AnagramTrie implements AnagramSolution {
    private final TrieNode root;

    public AnagramTrie() {
        root = new TrieNode();
    }

    public TrieNode getRoot() {
        return root;
    }

    /**
     * Insert a word into the Trie structure
     *
     * @param word
     */
    public void insert(String word) {
        char[] characters = word.toCharArray();

        // Sort characters alphabetically, since we care about anagrams
        Arrays.sort(characters);

        TrieNode node = root;

        for (char character : characters) {
            if (node.children == null) {
                node.children = new HashMap<Character, TrieNode>();
            }

            TrieNode childNode = node.children.get(character);
            if (childNode == null) {
                childNode = new TrieNode();
                node.children.put(character, childNode);
            }

            node = childNode;
        }

        // Add the actual full word to the leaf node
        node.addWord(word);
    }

    /**
     * Find all anagrams of the provided string
     *
     * @param word
     * @return
     */
    public ArrayList<String> findAnagrams(String word) {
        char[] characters = word.toCharArray();

        // Sort characters alphabetically, since we care about anagrams
        Arrays.sort(characters);

        TrieNode node = root;

        for (char character : characters) {
            if (node.children == null) {
                return null;
            }

            TrieNode childNode = node.children.get(character);
            if (childNode == null) {
                return null;
            }

            node = childNode;
        }

        // Copy words
        ArrayList<String> anagrams = new ArrayList<String>(node.words);

        // Exclude the user's input, since it cannot be an anagram of itself
        anagrams.remove(word);

        if (anagrams.size() == 0) return null;

        return anagrams;
    }
}

class TrieNode {
    public HashMap<Character, TrieNode> children;
    public ArrayList<String> words;

    /**
     * Get child node by character
     *
     * @param character
     * @return
     */
    public TrieNode get(char character) {
        if (children == null) return null;

        return children.get(character);
    }

    /**
     * Add a full word that was reached at this combination of characters
     *
     * @param word
     */
    public void addWord(String word) {
        if (words == null) {
            words = new ArrayList<String>();
        }

        words.add(word);
    }
}