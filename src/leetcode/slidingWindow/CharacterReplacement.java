package leetcode.slidingWindow;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CharacterReplacement {
	
	/*
	 * https://leetcode.com/problems/longest-repeating-character-replacement/
	 * 
	 */
	// sliding window solution 
	public int characterReplacement(String s, int k) {

        int[] count = new int[26];
        int maxCount = 0; // max count (freq) of a single character in the current window
        int maxLength = 0;

        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            count[s.charAt(right) - 'A'] += 1;
            maxCount = Math.max(maxCount, count[s.charAt(right) - 'A']);
            //if we consider the current window - and take out  the max freq items- 
            // the minority items can all be replaced to match the max freq type
            // but if the total num of minority items > k ( the number of allowable replacement ) 
            // then the window is slid forward unless the number of minority items are exactly equal to the value k
            while (right - left + 1 - maxCount > k) {
                count[s.charAt(left) - 'A'] -= 1;
                left++;
            }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
	 
	 @Test
	 public void testCharacterReplacement() {
		 
		 assertEquals(4, characterReplacement("ABAB", 2));
		 assertEquals(4, characterReplacement("AABABBA", 1));
		 assertEquals(4, characterReplacement("ABBB", 2));
	 }

}
