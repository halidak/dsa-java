package dsa.arrays;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 49. Group Anagrams
 * https://leetcode.com/problems/group-anagrams/
 *
 * Pattern:    hashing (group by key)
 * Idea:       anagrams look the same after sorting their letters ("eat", "tea" -> "aet"),
 *             so the sorted word is the group key. Map key -> list of words,
 *             add each word with computeIfAbsent, return all map values.
 * Complexity: O(n * k log k) time, O(n * k) space (n = number of words, k = max word length)
 * Solved:     2026-11-16  ·  confidence: 1/3 (ideja moja, sintaksa uz pomoć)
 */
public class GroupAnagrams {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for(int i = 0; i < strs.length; i++){
            char[] chars = strs[i].toCharArray();
            Arrays.sort(chars);

            String key = new String(chars);

            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(strs[i]);
        }

        return new ArrayList<>(groups.values());

    }
}
