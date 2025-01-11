package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class LongestIncreasingSubsequence {
	
	 public int lengthOfLIS(int[] nums) {
	       int res = 1;
	       //arr dp --> LIS ending at the given position 
	        int[] dp = new int[nums.length];
	        Arrays.fill(dp, 1);
	        dp[0] = 1;
	        for(int i=1;i<nums.length;i++){
	            for(int j=0;j<i;j++){
	                if(nums[i] > nums[j]){
	                    dp[i] = Math.max(dp[i] , dp[j] +1 );
	                }
	            }
	            res = Math.max( res , dp[i]);
	        }
	      return res;
	        
	    }
	 
	// using patience sort - nlogn time 
	 public int lengthOfLIS1(int[] nums) {
		 int len=1;
		 int[] tails = new int[nums.length];
		 Arrays.fill(tails, Integer.MIN_VALUE);
		 tails[0] = nums[0];
		 for(int i=0;i<nums.length;i++) {
			 if( nums[i] > tails[len-1]) {
				 len++;
				 tails[len-1] = nums[i];
			 }else {
				 // find the smallest value in tail that is greater than equal to given value
				 int sidx = smallestValueIndexGreaterThanEqualToReq(tails,len-1,nums[i]);
				 tails[sidx] = nums[i];
			 }
		 }
		 
		 return len;
	 }
	 
	 public int smallestValueIndexGreaterThanEqualToReq(int[] arr , int lim , int k) {
		 int frm=0, origLim = lim;
		 while(frm<=lim) {
			 int mid=  (lim+frm)>>>1;
			 if(arr[mid] == k) {
				 return mid;
			 }else if(arr[mid] < k) {
				 frm=mid+1;
			 }else {
				 lim = mid-1;
			 }
		 }
		// frm will cross over to the smallest value larger than k
		 return frm; 
	 }
	 
	 @Test
	 public void testLengthOfLIS() {
		 assertEquals(4 , lengthOfLIS(new int[] {10,9,2,5,3,7,101,18}));
		 
		 
	 }
	 
	 @Test
	 public void testLengthOfLIS_nlogn() {
//		 assertEquals(1 , smallestValueIndexGreaterThanEqualToReq(new int[] {-1,5,8}, 2, 2));
//		 assertEquals(2 , smallestValueIndexGreaterThanEqualToReq(new int[] {-1,5,8}, 2, 7));
//		 assertEquals(0 , smallestValueIndexGreaterThanEqualToReq(new int[] {4, 10, -2147483648, -2147483648, -2147483648, -2147483648}, 1, 3));
//
		 
		 assertEquals(4 , lengthOfLIS1(new int[] {10,9,2,5,3,7,101,18}));
		 assertEquals(4 , lengthOfLIS1(new int[] {0,1,0,3,2,3}));
		 assertEquals(1 , lengthOfLIS1(new int[] {7,7,7,7,7,7,7}));
	
		 assertEquals(3 , lengthOfLIS1(new int[] {4,10,4,3,8,9}));
		 
		 
		 
		 
		 
	 }

}
