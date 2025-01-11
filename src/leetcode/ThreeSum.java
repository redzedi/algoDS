package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
	
	/*
	 * https://leetcode.com/problems/3sum 
	 */
	
	 public List<List<Integer>> threeSum(int[] nums) {
		  ArrayList<List<Integer>> res = new ArrayList<>();
		  Arrays.sort(nums);
		  
		  for(int i=0;i<nums.length;i++) {
			  if(i>0 && nums[i] == nums[i-1]) {
				  continue;
			  }
			  for(int j=i+1;j<nums.length;j++) {
				  
				  if(j>(i+1) && nums[j] == nums[j-1]) {
					  continue;
				  }
				  
				  int k = Arrays.binarySearch(nums, j+1, nums.length, 0 -(nums[i] + nums[j]));
				 
				 if(k>=0) {
					 res.add(Arrays.asList(nums[i],nums[j],nums[k]));
				 }
				  
			  }	  
			  
		  }
		  return res;
	        
	    }

}
