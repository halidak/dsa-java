package dsa.arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 347. Top K Frequent Elements
 * https://leetcode.com/problems/top-k-frequent-elements/
 *
 * Pattern:    hashing (counting) + bucket sort
 * Idea:       count each number (number -> count), then flip it into buckets
 *             (count -> list of numbers). A count can't be bigger than n, so try
 *             counts from n down to 1 and collect numbers until there are k.
 *             Simpler alternative: sort numbers by count, O(n log n).
 * Complexity: O(n) time, O(n) space
 * Solved:     2026-11-16  ·  confidence: 1/3 (koraci 1-2 moji, korak 3 uz pomoć)
 */
public class TopKFrequent {

    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> seen = new HashMap<>();

        for(int i = 0; i < nums.length; i++){
            seen.put(nums[i], seen.getOrDefault(nums[i], 0) + 1);            
        }

        Map<Integer, List<Integer>> byCount = new HashMap<>();
        
        seen.forEach((broj, brojPonavljanja) -> {
            byCount.computeIfAbsent(brojPonavljanja, x -> new ArrayList<>()).add(broj);
        });

        int[] result = new int[k];
        int idx = 0;

        for (int key = nums.length; key >= 1; key--) {
            if (byCount.containsKey(key)) {
                for (int broj : byCount.get(key)) {
                    result[idx] = broj;
                    idx++;
                    if (idx == k){
                        return result;
                    }
                }
            }
        }

        return result;
    }
}
