package leetcode;

import java.util.Arrays;
import java.util.Comparator;

public class TwoSum {
	
	/*
	 * https://leetcode.com/problems/two-sum 
	 * 
	 */
	  public int[] twoSum(int[] nums, int target) {
		  
		    Integer[][] numsIdxArr = new Integer[nums.length][2];
		    
		    for(int i=0;i<nums.length;i++) {
		    	numsIdxArr[i]= new Integer[] {i , nums[i]};
		    }
		    
		    Comparator<Integer[]> c = (n1,n2)->n1[1]-n2[1];
		    //O(nlogn)
	        Arrays.sort(numsIdxArr, c);
	        int[] res = new int[2];
           int halfTarget = target/2;
	        for(int i=0;numsIdxArr[i][1]<=halfTarget ;i++){
	        	
	        	
	            int r = Arrays.binarySearch(numsIdxArr,i+1 , numsIdxArr.length, new Integer[] {0,target-numsIdxArr[i][1]},c);
	            
	            if(r>=0){
	                res[0] = numsIdxArr[i][0];
	                res[1] = numsIdxArr[r][0];
	                break;
	            }
	        }
	        
	        return res;
	    }

}
