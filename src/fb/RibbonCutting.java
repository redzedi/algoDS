package fb;

import java.util.Arrays;
import java.util.Set;

public class RibbonCutting {
	
	//https://leetcode.com/problems/cutting-ribbons/description/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	 public int maxLength(int[] ribbons, int k) {
	        int res=0,lo = 1,  hi = Arrays.stream(ribbons).max().getAsInt();

	        while(lo<=hi){
	            int mid = lo + (hi-lo)/2;
	            if(isCutLengthViable(ribbons, mid ,k)){
	                res = mid;
	                lo = mid+1;
	            }else{
	                hi = mid-1;
	            }
	        }
	         return res;
	        
	    }

	    private boolean isCutLengthViable(int[] ribbons, int mid , int k){
	        int numOfCuts =0;
	        for(int i=0;i<ribbons.length;i++){
	             numOfCuts += ribbons[i]/mid;
	        }
	        return numOfCuts>=k;
	    }
	    
	    public static void main(String[] args) {
			Set.of(1);
		}

}
