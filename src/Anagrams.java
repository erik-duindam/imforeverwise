import java.util.ArrayList;

/**
 * Created by erik on 1/9/17.
 */
public class Anagrams {

    /**
     * Please ignore this function and look at the explanations below, and run the tests (JUnit 4).
     *
     * @param args
     */
    public static void main(String[] args) {
        explainHashmapSolution();
        explainTrieSolution();
        explain100MSolution();
    }

    /**
     * The most straightforward way of finding anagrams is by using one big HashMap to store arrays of anagrams at
     * an alphabetically sorted key. Lookups and inserts will generally cost O(1) time.
     *
     * There are two downsides to this approach:
     *  1. A lot of natural words contain the same (starting) characters, which means the HashMap will have a lot of
     *     redundant starting characters that take space. This means you'll need a lot of memory for large maps.
     *  2. HashMaps cannot use primitive values (like ints/chars) as key, which means there's quite a lot of memory
     *     overhead due to the fact that Java has to store objects on the heap.
     */
    public static void explainHashmapSolution() {
        AnagramMap map = new AnagramMap();
        map.insert("erik");
        map.insert("kire");
        map.insert("eer");

        ArrayList<String> anagrams = map.findAnagrams("erik");
        System.out.println(anagrams);
    }

    /**
     * An alternative way to find anagrams is to create an alphabetically ordered Trie. The main benefit is saving some
     * space for the lookup keys, and thus for memory. This method would be a lot more beneficial if we were only interested
     * in the number of anagrams (the count) instead of the actual words. To further optimize this, radix tries would be
     * even better.
     *
     * The main downside of this approach is the O(log n) lookup and insert time.
     */
    public static void explainTrieSolution() {
        AnagramTrie trie = new AnagramTrie();
        trie.insert("erik");
        trie.insert("kire");
        trie.insert("eer");

        ArrayList<String> anagrams = trie.findAnagrams("erik");
        System.out.println(anagrams);
    }

    /**
     * If you would have a set of hundreds of millions of dictionary words, storing all of these in memory is not going
     * to be efficient (or even possible). If you'd still want to use a manual HashMap or Trie approach, you'd have to
     * store separate maps/tries based on the input word length, or based on the first characters. You would end up with
     * many smaller maps or tries instead of 1 big one. It's conceptually the same as creating a database index.
     * However, there's no point in building this manually, since there are great tools out there for database indexing.
     *
     * The better solution is to use proven database software. If the set is indeed 100M records, any decent database system
     * should still be able to handle this on one server without sharding. It wouldn't matter that much if you'd use
     * MySQL, PostgreSQL, MongoDB, etc. You would create a table or collection with an INDEX on the alphabetical key.
     *
     * A more complicated question would be how you would get the anagrams of a word with a 10B word dictionary,
     * or a 100M word dictionary that's being updated every second. In that case, you'd need a database system that's able
     * to handle a lot of reads and writes while scaling over multiple servers. You could theoretically do this with
     * any database system by using key-based sharding and master-slave replication, but it would still be tricky for
     * something like PostgreSQL if there are billions of records.
     *
     * One great tool for this use case would be a column store like Cassandra. Cassandra partitions data over multiple
     * nodes (servers) automatically based on your partition key per table. If you use the anagram key's length and first
     * characters in the partition key, Cassandra will automatically partition your anagrams over many servers.
     *
     * Cassandra model:
     *  CREATE TABLE anagrams (
     *   a_key text,
     *   length int,
     *   first_char text,
     *   anagrams list<text>,
     *
     *   PRIMARY KEY ((length, first_char), a_key)
     *  );
     *
     * Example data:
     *  INSERT INTO anagrams (a_key, length, first_char, anagrams) VALUES ('eikr', 4, 'e', ['erik', 'kire']);
     *
     * Example selection:
     *  SELECT * FROM anagrams WHERE length = 4 AND first_char = 'e' AND a_key = 'eikr';
     *
     * The partition key is (length, first_char), which means the keys of anagrams are split up in all combinations of
     * key length and first character. Assuming around 26 alphabet characters and a max word length of 35, the keys
     * are partitioned over 26 x 35 = 910 partitions. Cassandra can write each partition on a different server, with
     * replication on a per-partition basis. So practically you'd have each partition written on at least 3 nodes.
     *
     * Cassandra is able to do 1M reads/writes per second on a pretty common server setup (3 relatively large servers).
     * Given the fact that we break up our problem in (max) 910 parts, we should be able to scale this to Facebook
     * proportions. If 910 parts is not enough, we could partition on the first 2 characters instead, which would give as a huge
     * number of extra partitions.
     */
    public static void explain100MSolution() {

    }
}

