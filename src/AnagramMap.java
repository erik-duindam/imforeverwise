import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by erik on 1/9/17.
 */
public class AnagramMap implements AnagramSolution  {
    private HashMap<String, ArrayList<String>> anagramMap;

    public AnagramMap() {
        anagramMap = new HashMap<String, ArrayList<String>>();
    }

    public int size() {
        return anagramMap.size();
    }

    public String toAlphabeticalString(String word) {
        char[] characters = word.toCharArray();
        Arrays.sort(characters);
        return new String(characters);
    }

    public void insert(String word) {
        String alphabetical = toAlphabeticalString(word);

        if (!anagramMap.containsKey(alphabetical)) {
            anagramMap.put(alphabetical, new ArrayList<String>());
        }

        ArrayList<String> anagrams = anagramMap.get(alphabetical);
        anagrams.add(word);
    }

    public ArrayList<String> findAnagrams(String word) {
        String alphabetical = toAlphabeticalString(word);

        if (!anagramMap.containsKey(alphabetical)) return null;

        // Copy words
        ArrayList<String> anagrams = new ArrayList<String>(anagramMap.get(alphabetical));

        // Exclude the user's input, since it cannot be an anagram of itself
        anagrams.remove(word);

        if (anagrams.size() == 0) return null;

        return anagrams;
    }
}
