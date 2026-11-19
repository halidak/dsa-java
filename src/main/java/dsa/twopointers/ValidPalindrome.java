package dsa.twopointers;

/**
 * 125. Valid Palindrome
 * https://leetcode.com/problems/valid-palindrome/
 *
 * Pattern:    two pointers
 * Idea:       TODO
 * Complexity: TODO
 * Solved:     TODO  ·  confidence: ?/3
 */
public class ValidPalindrome {

    // "A man, a plan, a canal: Panama"  ->  true
    // "race a car"                      ->  false
    public boolean isPalindrome(String str) {
        // StringBuilder sb = new StringBuilder();

        // for(int i = 0; i< str.length(); i++) {
        //     char c = str.charAt(i);
        //     if(Character.isLetterOrDigit(c)){
        //         sb.append(Character.toLowerCase(c));
        //     }
        // }

        // int left = 0;
        // int right = sb.length() - 1;

        // while(left <= right){
        //     if(sb.charAt(left) != sb.charAt(right)){
        //         return false;
        //     }

        //     left++;
        //     right--;
        // }
        // return true;

        int left = 0;
        int right = str.length() - 1;

       while(left < right){
        while(left < right && !Character.isLetterOrDigit(left)){
            left++;
        }

        while(left < right && !Character.isLetterOrDigit(right)){
            right--;
        }

        if(Character.toLowerCase(str.charAt(left)) != Character.toLowerCase(right)){
            return false;
        }

        left++;
        right--;
       }

       return  true;
        
    }
}
