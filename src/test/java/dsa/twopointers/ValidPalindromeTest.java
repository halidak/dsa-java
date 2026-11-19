package dsa.twopointers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ValidPalindromeTest {

    private final ValidPalindrome solution = new ValidPalindrome();

    // --- primeri sa LeetCode-a ---

    @Test
    void example1() {
        assertTrue(solution.isPalindrome("A man, a plan, a canal: Panama"));
    }

    @Test
    void example2() {
        assertFalse(solution.isPalindrome("race a car"));
    }

    @Test
    void example3OnlySpace() {
        assertTrue(solution.isPalindrome(" "));
    }

    // --- ivični slučajevi ---

    @Test
    void singleCharacter() {
        assertTrue(solution.isPalindrome("a"));
    }

    @Test
    void onlySymbols() {
        assertTrue(solution.isPalindrome(".,!?"));
    }

    @Test
    void upperAndLowerCase() {
        assertTrue(solution.isPalindrome("Aa"));
    }

    @Test
    void digitsCount() {
        assertFalse(solution.isPalindrome("0P"));
        assertTrue(solution.isPalindrome("1a2,2A1"));
    }

    @Test
    void evenAndOddLength() {
        assertTrue(solution.isPalindrome("abba"));
        assertTrue(solution.isPalindrome("aba"));
        assertFalse(solution.isPalindrome("abca"));
    }
}
