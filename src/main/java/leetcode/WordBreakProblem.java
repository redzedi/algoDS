package leetcode;

import java.util.List;

public class WordBreakProblem {
	
	//https://leetcode.com/problems/word-break/
	
	public boolean wordBreak(String s, List<String> wordDict) {
		 
		 boolean[][] dp = new boolean[s.length()+1][s.length()+1];
		 
		 for(int bpe=0;bpe<s.length();bpe++) {
			 dp[bpe][0] = false;
		 }
		 dp[0][0]=true;
		 
		 for(int bpe=1;bpe<=s.length();bpe++) {
			 for(int bps=1;bps<=bpe;bps++) {
				// System.out.println("**  "+bps+" -- "+bpe);
				
				 dp[bpe][bps] = (wordDict.contains(s.substring(bps-1,bpe))? rowHasPositiveVal(dp,bps-1):false) || dp[bpe][bps];
				 
				// System.out.println("**  "+bps+" -- "+bpe+" --> "+ dp[bpe][bps]);
			 }
		 }
		 
		 
	      return  rowHasPositiveVal(dp,s.length());    
	    }
	 
	// y this this done ?
	 public boolean rowHasPositiveVal(boolean[][] dp, int row) {
		 boolean res = false;
		 for(int i=0;i<=row;i++) {
			 if(dp[row][i]) {
				 res=true;
				 //System.out.println("** 11 row has positive val "+row+" --> "+res);
				 break;
			 }
		 }
		// System.out.println("** row has positive val "+row+" --> "+res+" -- "+Arrays.toString(dp[row]));
		 return res;
	 }

}
