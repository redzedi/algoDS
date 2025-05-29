package leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MaxProductSubarray {
	
	// https://leetcode.com/problems/maximum-product-subarray/
	 
	 public int maxProduct(int[] nums) {
		 int res = nums[0];
		 int maxIncludingCurrent = nums[0];
		 int minIncludingCurrent = nums[0];
		 
		 for(int i=1;i<nums.length;i++) {
			 int tmp = maxIncludingCurrent;
			 maxIncludingCurrent = Math.max( minIncludingCurrent*nums[i], Math.max(nums[i], maxIncludingCurrent*nums[i])); 
			 minIncludingCurrent = Math.min(tmp*nums[i], Math.min(nums[i], minIncludingCurrent*nums[i]));
			 res = Math.max(res, maxIncludingCurrent);
		 }
		 
		 return res;
		 
	 }
	 
	 @Test
	 public void testMaxProduct() {
		 assertEquals(6, maxProduct(new int[] {2,3,-2,4}));
		 assertEquals(180, maxProduct(new int[] {6,-3,-10,0,2}));
		 assertEquals(0, maxProduct(new int[] {-2,0,-1}));
		 
		 assertEquals(108, maxProduct(new int[] {-1,-2,-9,-6}));
	 }

}
