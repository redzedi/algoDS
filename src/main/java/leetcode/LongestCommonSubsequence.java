package leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LongestCommonSubsequence {
	
	//https://leetcode.com/problems/longest-common-subsequence/
	
	public int longestCommonSubsequence(String t1, String t2) {
		  int[][] lcsResCache =  new int[2][t2.length()+1];
		  
		  for(int t1Idx=t1.length();t1Idx>=0;t1Idx--) {
			  int curr = (t1Idx)%2;
			  int prev = (t1Idx+1)%2;
			  for(int t2Idx=t2.length();t2Idx>=0;t2Idx--) {
				 
				  if(t1.length() == t1Idx || t2.length() == t2Idx) {
					  
					  lcsResCache[curr][(t2Idx)]=0;
				  }else if(t1.charAt(t1Idx) == t2.charAt(t2Idx)) {
						  lcsResCache[curr][ (t2Idx)] =  1+  lcsResCache[prev][ (t2Idx+1)];
				  }else {
					  lcsResCache[curr][ (t2Idx)] =  Math.max( lcsResCache[prev][ (t2Idx)],lcsResCache[curr][ (t2Idx+1)]);
				  }
				  
			  }
			  
		  }
		  
		  return lcsResCache[0][0];
	 
	  }
    @Test	
	public void testLongestCommonSubsequence() {
		assertEquals(3, longestCommonSubsequence("abcde", "ace"));
		assertEquals(3, longestCommonSubsequence("abc", "abc"));
		assertEquals(0, longestCommonSubsequence("abc", "def"));
		
		assertEquals(1, longestCommonSubsequence("psnw", "vozsh"));
		assertEquals(2, longestCommonSubsequence("ezupkr", "ubmrapg"));
	}

}
