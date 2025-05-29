package fb;

import java.util.Arrays;

public class NextPermutation {
	
	//https://leetcode.com/problems/next-permutation/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	public void nextPermutation(int[] nums) {
        int minSoFar = nums[nums.length-1];
        int minIdx = nums.length-1;
        boolean isRearranged = false;
          int c =-1;
          int cutIdx = -1;
        for(int i=nums.length-2;i>=0;i--){

        
          int minOfSubArray = Integer.MAX_VALUE;
          
          for(int j=i+1;j<nums.length;j++){
                if(nums[i] < nums[j]){
                    if(minOfSubArray > nums[j]){
                        minOfSubArray = nums[j];
                        c = j;
                    }
                }
          }

          if(c!= -1){
            int tmp = nums[i];
            nums[i] = nums[c];
            nums[c] = tmp;
            isRearranged = true;
            cutIdx = i;
            break;
          }

            
        }

       
            Arrays.sort(nums, cutIdx+1 , nums.length);
       
        
    }

}
