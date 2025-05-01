package fb;

public class PivotIndex {
	
	//https://leetcode.com/problems/find-pivot-index/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days

    public int pivotIndex(int[] nums) {

        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        for(int i=1;i<nums.length;i++){
            sum[i] = sum[i-1]+nums[i];
        }
     
       for(int i=0;i<nums.length;i++){
            int l = i==0?0:sum[i-1];
            int r = sum[nums.length-1]-sum[i];
            if(l==r){
                return i;
            }
        }

        return -1;

        
    }

}
