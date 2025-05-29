package leetcode.search_sort;

import java.util.Arrays;

public class ArrayContainsDuplicate {
	
	/**
	 * https://leetcode.com/problems/contains-duplicate
	 * 
	 * here instead of using the a sort algo as an util the trick is to hand write a sort such that we can combine search 
	 * 
	 * 
	 * @param nums
	 * @return
	 */
	
	public boolean containsDuplicate(int[] nums) {

		boolean res = false;
        Arrays.sort(nums );
        
        for(int i=0;i<nums.length;i++) {
        	if(i < nums.length-1) {
        		if(nums[i] == nums[i+1]) {
        			res = true ;
        			break;
        		}
        	}
        }
        
        return res ;
        
    }

}
