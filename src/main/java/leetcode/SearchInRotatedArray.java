package leetcode;

public class SearchInRotatedArray {
	
	/*
	 * https://leetcode.com/problems/search-in-rotated-sorted-array
	 */
	
	private int binarySearch(int[] nums, int target, int i , int j) {
		 int res=-1;

	        while(i<=j){
	            if(nums[i] == target){
	                res=i;
	                break;
	            }else if(nums[j] == target){
	               res=j;
	               break;
	            }else{
	                int mid = (i+j)/2;
	                if(nums[mid] == target){
	                    res=mid;
	                    break;
	                }else if(target<nums[mid]  ){
	                	j=mid-1;
	                }else{
	                     i=mid+1;
	                }
	            }
	        }
	        return res;
	}
	
	  public int search(int[] nums, int target) {

		  int res=-1, pivot = -1;
		  
		  //if rotated find pivot 
		  if(nums[nums.length-1] <= nums[0]) {
			  
			  int i=0,j=nums.length-1;
			  
			  while(i<=j){
				int  mid = (i+j)/2;
		                if(mid == i || mid ==j) {
		                	pivot =mid;
		                	break;
		                }else  if((nums[mid] > nums[mid+1]) || (nums[mid] < nums[mid-1])){
		                    pivot=mid;
		                    break;
		                }else {
		                  if(nums[i] < nums[mid]) {
		                	  
		                	  i=mid+1;
		                  }else {
		                	  j = mid-1;
		                  }
		                }
		        }
		  }
		  
		  if(pivot != -1) {
			  res = binarySearch(nums,target , 0, pivot);
			  
			  if(res==-1) {
				  
				  res = binarySearch(nums,target , pivot, nums.length-1);
			  }
		  }else {
			  res = binarySearch(nums,target , 0, nums.length-1);
			  
		  }

	        return res;
	        
	    }

}
