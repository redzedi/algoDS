package fb;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MergeSortedArraysInPlace {
	
	public void merge(int[] nums1, int m, int[] nums2, int n) {
		
		for(int n1 = m-1, n2=n-1 ,n1End = m+n-1 ; n1End >=0 && n2>=0;n1End--){
			
			if(n1>=0 && nums1[n1] >= nums2[n2]) {
				//copy and decrement
				if(n1 != n1End) {
					nums1[n1End] = nums1[n1];
				}
				n1--;
			}else {
				nums1[n1End] = nums2[n2--]; 
			}
		}
		
	}
	
	@Test
	public void testMerge() {
		int[] nums1 = new int[] {1,2,3,0,0,0};
		int[] nums2 = new int[] {2,5,6};
		
		merge(nums1, 3, nums2,3);
		
		assertArrayEquals(new int[] {1,2,2,3,5,6}, nums1 );
	}
	
	@Test
	public void testMerge1() {
		int[] nums1 = new int[] {0};
		int[] nums2 = new int[] {1};
		
		merge(nums1, 0, nums2,1);
		
		assertArrayEquals(new int[] {1}, nums1 );
	}
	
	@Test
	public void testMerge2() {
		int[] nums1 = new int[] {1};
		int[] nums2 = new int[] {};
		
		merge(nums1, 1, nums2,0);
		
		assertArrayEquals(new int[] {1}, nums1 );
	}
	
	@Test
	public void testMerge3() {
		int[] nums1 = new int[] {4,5,6,0,0,0};
		int[] nums2 = new int[] {1,2,3};
		
		merge(nums1, 3, nums2,3);
		
		assertArrayEquals(new int[] {1,2,3,4,5,6}, nums1 );
	}

}
