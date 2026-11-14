package dsa.arrays;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. Two Sum
 * https://leetcode.com/problems/two-sum/
 *
 * Pattern:    hashing (complement lookup)
 * Idea:       for each number, check if (target - number) was already seen in a HashMap;
 *             if yes, return both indices, otherwise store number -> index.
 *             Brute force (two loops, O(n^2)) is kept below as a comment.
 * Complexity: O(n) time, O(n) space
 * Solved:     2026-11-14  ·  confidence: 2/3
 */
public class TwoSum {

    /** Returns the indices of the two numbers that add up to target. */
    public int[] twoSum(int[] nums, int target) {
        // int[] result = new int[2];

        // for(int i = 0; i < nums.length; i++){
        //     for(int j = i + 1; j < nums.length; j++){
        //         if((nums[i] + nums[j]) == target){
        //             result[0] = i;   
        //             result[1] = j;  
        //             return result;
        //         }
        //     }
        // }
        // return result;

        //optimizacija - hes mapa
        Map<Integer, Integer> seen = new HashMap<>();

        for(int i = 0; i < nums.length; i++) {
            int fali = target - nums[i];

            if(seen.containsKey(fali)){
                return new int[]{seen.get(fali), i};
            }

            seen.put(nums[i], i);
        }

        return new  int[] {};
    }
}
