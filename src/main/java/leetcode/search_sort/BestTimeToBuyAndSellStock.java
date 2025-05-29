package leetcode.search_sort;

public class BestTimeToBuyAndSellStock {
	
	/*
	 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock
	 */
	
	 public int maxProfit(int[] prices) {
	       
	       int cheapestSoFar = Integer.MAX_VALUE;
	        int maxProfit = Integer.MIN_VALUE;
	         int currProfit = 0;
	        for(int i=0;i<prices.length;i++){
	            if(prices[i]<cheapestSoFar){
	                cheapestSoFar = prices[i];
	            }else{
	                currProfit =(prices[i]-cheapestSoFar);
	                if(currProfit > maxProfit ){
	                    maxProfit = currProfit;
	                }
	            }

	        }

	        return Math.max(0,maxProfit);
	        
	    }

}
