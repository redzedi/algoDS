package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class RussianDollEnvelops {
	
	//https://leetcode.com/problems/russian-doll-envelopes/?envType=company&envId=atlassian&favoriteSlug=atlassian-six-months
	
	 public int maxEnvelopes(int[][] e) {
		 
		 Arrays.sort(e,(e1,e2)->(e1[0]-e2[0]) + (e1[1] - e2[1]));
		 
		 System.out.println(Arrays.deepToString(e));
		 int res=1, maxSortedIdx=0;
		 for (int i = 1; i < e.length; i++) {
			
			 if( (e[i][0] > e[maxSortedIdx][0]) && (e[i][1] > e[maxSortedIdx][1])) {
				 res++;
				 maxSortedIdx=i;
			 }
			 
		}
		 
		 return res;
	        
	    }
	 
	 @Test
	 public void testMaxEnvelopes() {
		 assertEquals(3 , maxEnvelopes(new int[][] { new int[] {5,4},new int[] {6,4},new int[] {6,7},new int[] {2,3}}));
		 assertEquals(1 , maxEnvelopes(new int[][] { new int[] {1,1},new int[] {1,1},new int[] {1,1}}));
		 assertEquals(4 , maxEnvelopes(new int[][] {  new int[] {4,5}, new int[] {4,6}, new int[] {6,7}, new int[] {2,3}, new int[] {1,1}, new int[] {1,1}}));
	 }
	 
	 public static void main(String[] args) {
		
		 int[][] a = new int[][] { new int[] { 4,5 } , new int[] { 3,4 }, new int[] { 2,3 }, new int[] { 3,6 }, new int[] { 1,4 }};
		 
		 Arrays.sort(a,(e1,e2)->(e1[0]-e2[0]) + (e1[1] - e2[1]));
		 System.out.println(Arrays.deepToString(a));
		 
		 //[[5, 7], [2, 13], [2, 13], [2, 14], [8, 9], [16, 1], [8, 11], [8, 11], [4, 17], [2, 20], [15, 8], [13, 11], [13, 11], [8, 19], [11, 19], [11, 19], [11, 19], [18, 13], [14, 17], [18, 19]]

	}
	

}
