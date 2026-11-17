package dsa.arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * 271. Encode and Decode Strings
 * https://neetcode.io/problems/string-encode-and-decode  (na LeetCode-u je Premium)
 *
 * Pattern:    string building (length prefix)
 * Idea:       TODO
 * Complexity: TODO
 * Solved:     TODO  ·  confidence: ?/3
 */
public class EncodeDecodeStrings {

    // ["a-b", "c"]  ->  "3#a-b1#c"
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            // dodaj: dužinu, pa '#', pa samu reč
            sb.append(s.length());
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
            int len = Integer.parseInt(s.substring(i, j));
            String word = s.substring(j+ 1, j + 1 + len);
            result.add(word);
            i = j + 1 + len;
        }
        return result;
    }
}
