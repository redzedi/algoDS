package leetcode;

public class CountingBits {

	// https://leetcode.com/problems/counting-bits/

	 public int[] countBits(int n) {
	        int k = n+1;
	        int[] nums = new int[k];
	        nums[0] = 0;
	        if(k>1)
	        nums[1] = 1;

	        for(int i=2;i<k;i++){
	            nums[i] = nums[i/2] + i%2;
	        }

	        return nums;
	    }

}
