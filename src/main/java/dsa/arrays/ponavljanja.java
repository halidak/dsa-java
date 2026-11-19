package dsa.arrays;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Input: strs = ["eat","tea","tan","ate","nat","bat"]

// Output: [["bat"],["nat","tan"],["ate","eat","tea"]]

public class ponavljanja {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for(int i = 0; i < strs.length; i++){
            char[] chars = strs[i].toCharArray();
            Arrays.sort(chars);

            String key = new String(chars);

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(strs[i]);
        }

        return new ArrayList<>(map.values());

    }


        //Input: nums = [1,1,1,2,2,3], k = 2

        // Output: [1,2]


    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> seen = new HashMap<>();

        for(int i = 0; i < nums.length; i++){
            seen.put(nums[i], seen.getOrDefault(nums[i], 0) + 1);            
        }

        Map<Integer, List<Integer>> byCount = new HashMap<>();

        seen.forEach((number, count) -> {
            byCount.computeIfAbsent(count, x -> new ArrayList<>()).add(number);
        });

        int[] result = new int[k];
        int idx = 0;

        for(int key = nums.length - 1; key >= 1; key--){
            if(byCount.containsKey(key)){
                for(int num: byCount.get(key)){
                    result[idx] = num;
                    idx++;
                    if (idx == k){
                        return result;
                    }
                }
            }
        }

        return result;

    }

    // ["a-b", "c"]  ->  "3#a-b1#c"
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();

        for(String s: strs){
            int len = s.length();
            sb.append(len);
            sb.append("#");
            sb.append(s);
        }

        return sb.toString();
    }

    // "3#a-b1#c"  ->  ["a-b", "c"]
    public List<String> decode(String s) {
        List<String> result = new ArrayList<>();
        int i = 0;

        while (i < s.length()){
            int j = s.indexOf("#", i);
            Integer len = Integer.parseInt(s.substring(i, j));
            String word = s.substring(j + 1, j + 1 + len);
            result.add(word);
            i = j + 1 + len;
        }

        return result;
    }

}
