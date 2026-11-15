package dsa.arrays;

import java.util.HashSet;
import java.util.Set;

/**
 * 217. Contains Duplicate
 * https://leetcode.com/problems/contains-duplicate/
 *
 * Pattern:    hashing (seen-set)
 * Idea:       keep a HashSet of numbers already seen; if the current number is in it,
 *             there is a duplicate. Only "was it seen?" matters, so a Set is enough
 *             (my first version used a HashMap number -> index, kept below).
 *             Alternatives: brute force O(n^2); sort and compare neighbours O(n log n).
 * Complexity: O(n) time, O(n) space
 * Solved:     2026-11-15  ·  confidence: 2/3
 */
public class ContainsDuplicate {

    /** Returns true if any value appears at least twice. */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();

        for (int n : nums) {
            if (seen.contains(n)) {
                return true;
            }
            seen.add(n);
        }

        return false;
    }

    // My first version (HashMap, works, but the index i is never read):
    //
    // Map<Integer, Integer> seen = new HashMap<>();
    // for (int i = 0; i < nums.length; i++) {
    //     if (seen.containsKey(nums[i])) {
    //         return true;
    //     }
    //     seen.put(nums[i], i);
    // }
    // return false;
}
