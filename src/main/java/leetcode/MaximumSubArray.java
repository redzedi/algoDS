package leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MaximumSubArray {
	
	//https://leetcode.com/problems/maximum-subarray/
	
	 public int maxSubArray(int[] nums) {
		 if(nums.length == 1) {
			 return nums[0];
		 }else {
			 int res = nums[0];
			 int prevMaxSum = nums[0];
			 for(int i=1;i<nums.length;i++) {
				 prevMaxSum = Math.max(nums[i], prevMaxSum+nums[i]);
				 if(prevMaxSum > res) {
					 res = prevMaxSum;
				 }
			 }
			 return res;
			 
		 }
	    }
	 
	 @Test
	 public void testMaxSubArray() {
		 assertEquals(6, maxSubArray(new int[] {-2,1,-3,4,-1,2,1,-5,4}));
		 assertEquals(1, maxSubArray(new int[] {1}));
		 assertEquals(23, maxSubArray(new int[] {5,4,-1,7,8}));
	 }

}
