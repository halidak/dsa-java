package dsa.arrays;

import java.util.Arrays;

/**
 * 242. Valid Anagram
 * https://leetcode.com/problems/valid-anagram/
 *
 * Pattern:    sorting
 * Idea:       sort both strings and compare; anagrams become identical when sorted.
 *             Next time: try counting letters in an int[26] (O(n)).
 * Complexity: O(n log n) time, O(n) space
 * Solved:     2026-11-14  ·  confidence: 2/3
 */
public class ValidAnagram {

    public boolean isAnagram(String s, String t) {
    
        // prvo ocigledna provera - ako reci nisu iste duzine
        if(s.length() != t.length()){
            return false;
        }

        //ideja A:
        //s.toCharArray() pretvara string u niz slova (char[])
        //Arrays.sort(niz) sortira niz
        //Arrays.equals(niz1, niz2) proverava da li su dva niza ista

        char[] a = s.toCharArray();
        char[] b = t.toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);        
    }
}
