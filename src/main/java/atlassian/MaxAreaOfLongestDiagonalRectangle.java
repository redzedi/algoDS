package atlassian;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MaxAreaOfLongestDiagonalRectangle {
	
	//https://leetcode.com/problems/maximum-area-of-longest-diagonal-rectangle/?envType=company&envId=atlassian&favoriteSlug=atlassian-six-months
	
	 public int areaOfMaxDiagonal(int[][] dimensions) {
	        
	        double maxDiagonal = -1;
	        int maxArea = -1;

	        for(int i=0;i<dimensions.length;i++){
	            double currD =  Math.sqrt(Math.pow(dimensions[i][1],2)+Math.pow(dimensions[i][0],2));
	            if(currD>maxDiagonal){
	                maxDiagonal = currD;
	                maxArea = Math.round(dimensions[i][0]*dimensions[i][1]);
	            }else if(currD == maxDiagonal){
	                int currArea = Math.round(dimensions[i][0]*dimensions[i][1]);
	                if(currArea > maxArea){
	                    maxDiagonal = currD;
	                    maxArea = currArea;
	                }
	            }

	           
	        }
	         return maxArea;
	    }
	 
	 @Test
	 public void testAreaOfMaxDiagonal() {
		 assertEquals(48,areaOfMaxDiagonal(new int[][] { new int[] {9,3},new int[] {8,6}}));
		 assertEquals(12,areaOfMaxDiagonal(new int[][] { new int[] {3,4},new int[] {4,3}}));
		 assertEquals(30,areaOfMaxDiagonal(new int[][] { new int[] {10,3},new int[] {5,9},new int[] {8,3}}));
	 }
	 
	 

}
