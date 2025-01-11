package leetcode;

public class ValidAnagram {

	// https://leetcode.com/problems/valid-anagram/description/

public boolean isAnagram(String s, String t) {
		
		if(s.length() != t.length()) {
			return false;
		}
		
		int[] x = new int[26];
		s.chars().forEach((c)->x[c-97]++) ;
		return !t.chars().anyMatch(c-> x[c-97]--==0 );

	}
	
	public static void main(String[] args) {
		System.out.println(String.format("%d", (int)'z'-97));
	}

}
