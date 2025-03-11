package atlassian;

public class BuyingAndSellingStock2 {
	
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
