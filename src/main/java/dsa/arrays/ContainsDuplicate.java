package dsa.arrays;

import java.util.HashSet;
import java.util.Set;

/**
 * 217. Contains Duplicate
 * https://leetcode.com/problems/contains-duplicate/
 *
 * Pattern:    hashing (seen-set)
 * Idea:       walk once and remember what you have seen; the first repeat answers the question.
 * Complexity: O(n) time, O(n) space.
 *             Alternative: sort first, then compare neighbours: O(n log n) time, O(1) extra space.
 * Solved:     2026-11-04  ·  confidence: 3/3
 */
public class ContainsDuplicate {

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int n : nums) {
            if (!seen.add(n)) {
                return true;
            }
        }
        return false;
    }
}
