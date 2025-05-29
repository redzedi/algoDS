package leetcode.search_sort;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class MergeIntervals {
	
	/*
	 * https://leetcode.com/problems/merge-intervals/description/
	 */
	
	 public int[][] merge(int[][] intervals) {
		 
		 Arrays.sort(intervals, (c1,c2)-> c1[0]-c2[0]);
		 
		 int[][] res = new int[intervals.length][2];
		 res[0] = new int[] {intervals[0][0] , intervals[0][1]};
		 int r=0;
		 for(int i=1;i<intervals.length;i++) {
			 if(res[r][0] <= intervals[i][0] && intervals[i][0] <= res[r][1]  ) {
				 if(res[r][1] < intervals[i][1])
					 res[r][1] = intervals[i][1];
			 }else {
				 res[++r] = new int[] {intervals[i][0] , intervals[i][1]}; 
			 }
		 }
		 
		 return  Arrays.copyOfRange(res, 0, r+1);
	        
	    }
	 
	 @Test
	 public void testMerge() {
		 int[][] actual1 = merge(new int[][] {new int[] {1,3},new int[] {2,6},new int[] {8,10},new int[] {15,18}});
		 System.out.println(actual1);
		 assertArrayEquals(new int[][] {new int[] {1,6},new int[] {8,10},new int[] {15,18}} , actual1);
	 }

}
