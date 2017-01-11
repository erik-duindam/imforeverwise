## Assignments

### 1. Find anagrams from a dictionary
To find anagrams of a word by looking words up in a dictionary, I've built two solutions and described one more:
1. HashMap solution; optimized for speed
2. Trie solution; optimized for memory
3. Cassandra solution; scalable till billions of words

I chose Java for these algorithms, because it allows me to choose my data structures specifically,
while being very readable to most programmers. Additionally, since performance is a factor here,
a lower level language makes it easier to illustrate actual performance impact.

Please refer to src/Anagrams.java for my complete comments. You could run the Junit tests
with IntelliJ IDEA, Eclipse or plain JUnit 4. The tests with a real dictionary run in 250ms.
Alternatively, you could run `java Anagrams` after compiling with `javac Anagrams`.

### 2. Merge meeting time slots
To merge meeting time slots into combined tuples, I chose JavaScript for simplicity.
I've built an algorithm that iterates the input array from last start time to first
start time, to be able to merge new time slots into the original array. This means
I don't have to use a temporary stack or array for saving merged time slots.

To run the code, simply run `npm test` (after doing `npm install` for mocha/expect libs).
