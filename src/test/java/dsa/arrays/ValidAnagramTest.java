package dsa.arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ValidAnagramTest {

    private final ValidAnagram solution = new ValidAnagram();

    @Test
    void example1() {
        assertTrue(solution.isAnagram("anagram", "nagaram"));
    }

    @Test
    void example2() {
        assertFalse(solution.isAnagram("rat", "car"));
    }

    @Test
    void differentLengths() {
        assertFalse(solution.isAnagram("ab", "a"));
    }

    @Test
    void sameLettersDifferentCounts() {
        assertFalse(solution.isAnagram("aab", "abb"));
    }

    @Test
    void singleLetter() {
        assertTrue(solution.isAnagram("a", "a"));
    }
}
