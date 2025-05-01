package fb;

import java.util.List;

public class LongestConsecutiveOnes {
	
	//https://leetcode.com/problems/max-consecutive-ones-iii/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	  public int longestOnes(int[] nums, int k) {

	        int l=0,r=0,currCapacity = k , maxWinLength = 0;
	        while(l<=r && r < nums.length){
	            if(nums[r] == 1){
	               maxWinLength = Math.max(maxWinLength, r-l+1);
	                r++;
	                
	            }else{
	               
	                    if(currCapacity>0){
	                        maxWinLength = Math.max(maxWinLength, r-l+1);
	                        r++;
	                       
	                        currCapacity--;
	                    }else{
	                        if(nums[l] == 0){
	                           currCapacity = (currCapacity+1)>k?k:currCapacity+1;
	                        }
	                        if(l==r){
	                            r++;
	                        }
	                        l++;
	                        //r++;
	                        //currCapacity = k;
	                    }
	                
	            }
	        }
	      return maxWinLength;
	        
	    }
	  
	  public static void main(String[] args) {
	 System.out.println(List.of(new Integer[] {1,1}, new Integer[] {1,1}).toArray(l->new Integer[l][2])[0][0]);	;
	}

}
