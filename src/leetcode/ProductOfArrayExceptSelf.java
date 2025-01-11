package leetcode;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ProductOfArrayExceptSelf {
	
	/*
	 * https://leetcode.com/problems/product-of-array-except-self/
	 */
	
	  public int[] productExceptSelf(int[] nums) {

		  int[] res = new int[nums.length];
	        int[] prefixes = new int[nums.length];
	        int[] suffixes = new int[nums.length];
	        int acc =1;
	        
	        prefixes[0] = nums[0];
	        for(int i=1;i<nums.length;i++) {
	        	prefixes[i] = prefixes[i-1]*nums[i];  
	        }
	        
	        suffixes[nums.length-1] = nums[nums.length-1];
	        
	        for(int i=nums.length-2;i>=0;i--) {
	        	suffixes[i] = suffixes[i+1]*nums[i];
	        }
	        
	        for(int i=0;i<nums.length;i++) {
	        	if(i==0) {
	        		res[0] = suffixes[1];
	        	}else if(i==nums.length-1) {
	        		res[nums.length-1] = prefixes[nums.length-2];
	        	}else {
	        		res[i] = prefixes[i-1] * suffixes[i+1];
	        	}
	        }
	        
	        return res;
	        
	    }
	  
	  public int[] productExceptSelf1(int[] nums) {

		  int[] res = new int[nums.length];
	        int suffixAcc = nums[nums.length-1];
	        int prefixAcc = nums[0];
	        for(int i=nums.length-2;i>0;i--) {
	        	suffixAcc *= nums[i];
	        }
	        res[0]=suffixAcc;
	        for(int i=1;i<nums.length;i++) {
	        	res[i] = prefixAcc;
	        	prefixAcc *= nums[i];
	        }
	        suffixAcc = nums[nums.length-1];
	        for(int i=nums.length-2;i>=1;i--) {
	        	res[i] *= suffixAcc;
	        	suffixAcc *= nums[i];
	        }
	        
	        return res;
	        
	    }
	  
	  
	@Test
	public  void testProductExceptSelf() {
		  assertArrayEquals(new int[] {24,12,8,6} , productExceptSelf1(new int[] {1,2,3,4}));
		  assertArrayEquals(new int[] {0,0,9,0,0} , productExceptSelf1(new int[] {-1,1,0,-3,3}));
		  
	  }

}
