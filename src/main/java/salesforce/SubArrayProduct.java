package salesforce;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SubArrayProduct {

	// https://leetcode.com/problems/subarray-product-less-than-k/description/?envType=company&envId=salesforce&favoriteSlug=salesforce-three-months

	public int numSubarrayProductLessThanK(int[] nums, int k) {
		
		if(k==0)
			return 0;
		
		int arrCnt = 0 , l = 0 ,r=0, currPrd = 1;
		
		while(l<=r && r<nums.length) {
			
			currPrd *= nums[r];
			
			while(currPrd>=k) {
				currPrd /=nums[l++];
			}
			
			arrCnt += (r-l+1);
			
		r++;
			
		}
		
		return arrCnt;
		
	}
	
	
	
	@Test
	public void testNumSubarrayProductLessThanK() {
		assertEquals(8, numSubarrayProductLessThanK(new int[] {10,5,2,6}, 100));
		assertEquals(0, numSubarrayProductLessThanK(new int[] {1,2,3}, 0));
		
		assertEquals(1, numSubarrayProductLessThanK(new int[] {57,44,92,28,66,60,37,33,52,38,29,76,8,75,22}, 18));
	}

}
