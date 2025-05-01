package fb;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class RangeInSortedArray {
	
	 public int[] searchRange(int[] nums, int target) {

		  int r1 =-1 , r2=-1;
		  r1 = findBound(nums, target, true);
		  if(r1 != -1) {
			  r2 = findBound(nums, target, false);
		  }
	        return new int[] {r1,r2};
	        

	       
	        
	    }

	private int findBound(int[] nums, int target, boolean isLowerBound) {
		int res = -1, l =0, r = nums.length-1 , r1=-1 , r2=-1;

		while(l<=r ){
		    int mid = l + (r-l)/2;

		   // System.out.println("Before -- l = "+l+". r= "+r+" r1= "+r1+" r2= "+r2+" mid= "+mid);
		     
		   
		    
		    if(nums[mid] == target){
		    	if(isLowerBound) {
		    		if(l == mid || nums[mid-1] != target) {
			    		res = mid;
			    		break;
			    	}else {
			    		
			    		r = mid-1;
			    	}
	    		}else {
	    			if(r == mid || nums[mid+1] != target) {
			    		res = mid;
			    		break;
			    	}else {
			    		
			    		l = mid+1;
			    	}
	    		}
		    	
		        
		    } else if( nums[mid] < target){
		        l = mid+1;
		    }else{
		        r = mid-1;
		    }

   
		}
		
		return res;
	}
	
	@Test
	public void testSearchRange() {
//		assertArrayEquals(new int[] {3,4}, searchRange(new int[] {5,7,7,8,8,10}, 8));
//		assertArrayEquals(new int[] {0,0}, searchRange(new int[] {1}, 1));
		assertArrayEquals(new int[] {0,1}, searchRange(new int[] {2,2}, 2));
	}
	
	

}
