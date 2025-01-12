package leetcode.dp;

import java.util.Arrays;

class CoinChange {
	   public int coinChange(int[] coins, int amount) {
			 //Arrays.sort(coins);
			 int[] mins = new int[amount+1];
			 Arrays.fill(mins, Integer.MAX_VALUE);
			 mins[0]=0;
			 
			 for(int i=1;i<mins.length;i++) {
				 for(int j=0;j<coins.length;j++) {
					 if(coins[j]<=i) {
						 if(mins[i-coins[j]] < mins[i]) {
							 mins[i] = mins[i-coins[j]]+1;
						 }
					 }
				 }
			 }
			 
			 
			 return mins[amount]==Integer.MAX_VALUE?-1:mins[amount];
		 }
	}
