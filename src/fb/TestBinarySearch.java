package fb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestBinarySearch {
	
	
	public int binarySearch(int[] nums, int target) {
		int res=-1, l=0, r=nums.length-1;
		
		while(l<=r) {
		   int mid = l + (r-l)/2;
		   
		   System.out.println(String.format("l= %d, mid= %d, r= %d ", l,mid,r));
		   if(nums[mid] == target) {
			   res = mid;
			   break;
		   }else if(nums[mid] < target) {
			   l = mid+1;
		   }else {
			   r = mid-1;
		   }
		}
		
		 System.out.println(String.format("**Final** l= %d,  r= %d ", l,r));
		return res;
	}
	
	@Test
	public void testBinarySearch() {
		
		assertEquals(2 , binarySearch(new int[] {1,2,3,4,5},3));
		assertEquals(4 , binarySearch(new int[] {1,2,3,4,5},5));
		assertEquals(0 , binarySearch(new int[] {1,2,3,4,5},1));

		assertEquals(-1 , binarySearch(new int[] {1,2,3,4,5},6));
		assertEquals(-1 , binarySearch(new int[] {1,2,3,4,5},-1));
	}

}
