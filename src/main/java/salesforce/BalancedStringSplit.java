package salesforce;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BalancedStringSplit {
	
	
	//https://leetcode.com/problems/split-a-string-in-balanced-strings
	 public int balancedStringSplit(String s) {
		 
		 int cnt = 0 , rCnt=0, lCnt=0;
		 
		 for (int r = 0; r < s.length(); r++) {
			
			 switch(s.charAt(r)) {
			 case 'R':
				 rCnt++;
				 break;
			 case 'L':
				 lCnt++;
				 
				 
			 }
			 
			 if(lCnt ==rCnt) {
				 cnt++;
				 lCnt=0;
				 rCnt=0;
			 }
			 
			 
		}
	        
		 return cnt;
	    }
	 
	 
	 @Test
	 public void testBalancedStringSplit() {
		 assertEquals(4, balancedStringSplit("RLRRLLRLRL"));
		 assertEquals(2, balancedStringSplit("RLRRRLLRLL"));
		 assertEquals(1, balancedStringSplit("LLLLRRRR"));
	 }

}
