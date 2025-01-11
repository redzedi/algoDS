package leetcode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class PacificAtlanticWaterflow {
	
	//https://leetcode.com/problems/pacific-atlantic-water-flow/
	
public List<List<Integer>> pacificAtlantic1(int[][] heights) {
		
		List<List<Integer>> res = new ArrayList<>();
		
		boolean[][] pacificFlow = new boolean[heights.length][heights[0].length]; 
		boolean[][] atlanticFlow = new boolean[heights.length][heights[0].length]; 
		
		//filling  pacific flow 
		for(int r=0;r<heights.length;r++) {
			if(r>0) {
				Arrays.fill(pacificFlow[r], false);
				pacificFlow[r][0]=true;
			}else {
				Arrays.fill(pacificFlow[r], true);
				
			}
		}
		
		for(int r=1;r<heights.length;r++) {
			for(int c=1;c<heights[r].length;c++) {
				pacificFlow[r][c] =  (pacificFlow[r][c-1] &&  (heights[r][c-1] <= heights[r][c])) 
						|| (pacificFlow[r-1][c] &&  (heights[r-1][c] <= heights[r][c])); 
			}
		}
		
		//reverse traversal
		
		for(int r=heights.length-1;r>=0;r--) {
			for(int c=heights[r].length-2;c>=0;c--) {
				pacificFlow[r][c] = pacificFlow[r][c] ||  (pacificFlow[r][c+1] &&  (heights[r][c+1] <= heights[r][c])) 
						|| ((r<heights.length-1) && (pacificFlow[r+1][c] &&  (heights[r+1][c] <= heights[r][c]))); 
			}
		}
		
		System.out.println("Pacific flow 11,3 "+pacificFlow[11][3]);
		
		//filling  atlantic flo flow 
				for(int r=0;r<heights.length;r++) {
					if(r==heights.length-1) {
						Arrays.fill(atlanticFlow[r], true);
					}else {
						Arrays.fill(atlanticFlow[r], false);
						atlanticFlow[r][atlanticFlow[r].length-1]=true;
						
					}
				}
				
				for(int r=heights.length-2;r>=0;r--) {
					for(int c=heights[r].length-2;c>=0;c--) {
						atlanticFlow[r][c] =  (atlanticFlow[r][c+1] &&  (heights[r][c+1] <= heights[r][c])) 
								|| (atlanticFlow[r+1][c] &&  (heights[r+1][c] <= heights[r][c])); 
					}
				}
				
				//reverse traversal - atlantic
				for(int r=0;r<heights.length;r++) {
					for(int c=1;c<heights[r].length;c++) {
						atlanticFlow[r][c] =  atlanticFlow[r][c] ||  (atlanticFlow[r][c-1] &&  (heights[r][c-1] <= heights[r][c])) 
								|| ((r>0) && (atlanticFlow[r-1][c] &&  (heights[r-1][c] <= heights[r][c]))); 
					}
				}
				System.out.println("atlantic flow 11,2 "+atlanticFlow[11][2]+" -- "+heights[11][2]);
				System.out.println("atlantic flow 11,3 "+atlanticFlow[11][3]+" -- "+heights[11][3]);
				System.out.println("atlantic flow 11,4 "+atlanticFlow[11][4]+" -- "+heights[11][4]);
				
				System.out.println("atlantic flow 10,3 "+atlanticFlow[10][3]+" -- "+heights[10][3]);
				System.out.println("atlantic flow 12,3 "+atlanticFlow[12][3]+" -- "+heights[12][3]);
				
				
				System.out.println("heights r,c "+heights.length+" -- "+heights[0].length);
				System.out.println();
				
				for(int r=0;r<heights.length;r++) {
					for(int c=0;c<heights[r].length;c++) {
						if(atlanticFlow[r][c] && pacificFlow[r][c]) {
							res.add(Arrays.asList(r,c));
						}
					}
				}
		
		return res;
	        
	    }

 public static enum GRID_STATE{ UNVISITED, DRAINS , DOES_NOT_DRAIN};

public List<List<Integer>> pacificAtlantic2(int[][] heights) {
	
	List<List<Integer>> res = new ArrayList<>();
	List<List<Integer>> res1 = new ArrayList<>();
	
	boolean[][] pacificFlow = new boolean[heights.length][heights[0].length]; 
	
	
	//filling  pacific flow 
//	for(int r=0;r<heights.length;r++) {
//		if(r>0) {
//			Arrays.fill(pacificFlow[r], false);
//			pacificFlow[r][0]=GRID_STATE.DRAINS;
//		}else {
//			Arrays.fill(pacificFlow[r], GRID_STATE.DRAINS);
//			
//		}
//	}
	
	for(int r=0;r<heights.length;r++) {
		for(int c=0;c<heights[r].length;c++) {
			if(r>0 && c>0) {
				continue;
			}
			//dfs
			LinkedList<List<Integer>> stk = new LinkedList<>();
			if(!pacificFlow[r][c]) {
				stk.push(Arrays.asList(r,c));
			}
			
			while( !stk.isEmpty()) {
				List<Integer> currNode = stk.pop();
				int r1 = currNode.get(0);
				int c1 = currNode.get(1);
				
				System.out.println(" pacific currNode --> "+currNode);
				
				if(!pacificFlow[r1][c1]) {
					res.add(currNode);
					pacificFlow[r1][c1] = true;
				}
				
				//up
				if(r1>0 && heights[r1][c1] <= heights[r1-1][c1] && !pacificFlow[r1-1][c1] ) {
					stk.push(Arrays.asList(r1-1,c1));
				}
				//down
				if(r1<heights.length-1 && heights[r1][c1] <= heights[r1+1][c1] && !pacificFlow[r1+1][c1]) {
					stk.push(Arrays.asList(r1+1,c1));
				}
				
				//left
				if(c1>0 && heights[r1][c1] <= heights[r1][c1-1] && !pacificFlow[r1][c1-1]) {
					stk.push(Arrays.asList(r1,c1-1));
				}
					
				//right
				if(c1<heights[r1].length-1 && heights[r1][c1] <= heights[r1][c1+1] && !pacificFlow[r1][c1+1]) {
					stk.push(Arrays.asList(r1,c1+1));
				}	
					
				
				
			}
			
		}
	}
	System.out.println("Completed pacific flow ");
   // res has all grids that are reachable from pacific - do dfs from each of them for atlantic
	
	for(List<Integer> currGrid:res) {
		
		LinkedList<List<Integer>> stk = new LinkedList<>();
		stk.push(currGrid);
		//dfs
		boolean[][] atlanticFlow = new boolean[heights.length][heights[0].length]; 
		
		while(!stk.isEmpty()) {
			List<Integer> currNode = stk.pop();
			int r = currNode.get(0);
			int c = currNode.get(1);
			if(atlanticFlow[r][c]) {
				continue;
			}
			atlanticFlow[r][c] = true;
			
			if(r == heights.length-1 || c == heights[r].length-1) {
				res1.add(currGrid);
				break;
			}
			
			//up
			if(r>0 && heights[r][c] >= heights[r-1][c+1] && !atlanticFlow[r-1][c] ) {
				stk.push(Arrays.asList(r-1,c));
			}
			//down
			if(r<heights.length-1 && heights[r][c] >= heights[r+1][c] && !atlanticFlow[r+1][c]) {
				stk.push(Arrays.asList(r+1,c));
			}
			
			//left
			if(c>0 && heights[r][c] >= heights[r][c-1] && !atlanticFlow[r][c-1]) {
				stk.push(Arrays.asList(r,c-1));
			}
				
			//right
			if(c<heights[r].length-1 && heights[r][c] >= heights[r][c+1] && !pacificFlow[r][c+1]) {
				stk.push(Arrays.asList(r,c+1));
			}	
			
			
			
		}
		
	}
	
	return res1;
        
    }


public List<List<Integer>> pacificAtlantic(int[][] heights) {
	
	List<List<Integer>> res = new ArrayList<>();
	List<List<Integer>> res1 = new ArrayList<>();
	
	boolean[][] pacificFlow = new boolean[heights.length][heights[0].length]; 
	boolean[][] atlanticFlow = new boolean[heights.length][heights[0].length]; 
	
	

	
	for(int r=0;r<heights.length;r++) {
		for(int c=0;c<heights[r].length;c++) {
			if(r>0 && c>0) {
				continue;
			}
			//dfs
			LinkedList<List<Integer>> stk = new LinkedList<>();
			if(!pacificFlow[r][c]) {
				stk.push(Arrays.asList(r,c));
			}
			
			while( !stk.isEmpty()) {
				List<Integer> currNode = stk.pop();
				int r1 = currNode.get(0);
				int c1 = currNode.get(1);
				
				System.out.println(" pacific currNode --> "+currNode);
				
				if(!pacificFlow[r1][c1]) {
					//res.add(currNode);
					pacificFlow[r1][c1] = true;
				}
				
				//up
				if(r1>0 && heights[r1][c1] <= heights[r1-1][c1] && !pacificFlow[r1-1][c1] ) {
					stk.push(Arrays.asList(r1-1,c1));
				}
				//down
				if(r1<heights.length-1 && heights[r1][c1] <= heights[r1+1][c1] && !pacificFlow[r1+1][c1]) {
					stk.push(Arrays.asList(r1+1,c1));
				}
				
				//left
				if(c1>0 && heights[r1][c1] <= heights[r1][c1-1] && !pacificFlow[r1][c1-1]) {
					stk.push(Arrays.asList(r1,c1-1));
				}
					
				//right
				if(c1<heights[r1].length-1 && heights[r1][c1] <= heights[r1][c1+1] && !pacificFlow[r1][c1+1]) {
					stk.push(Arrays.asList(r1,c1+1));
				}	
					
				
				
			}
			
		}
	}
	System.out.println("Completed pacific flow ");
   // res has all grids that are reachable from pacific - do dfs from each of them for atlantic
	
	for(int r=heights.length-1;r>=0;r--) {
		for(int c=heights[r].length-1;c>=0;c--) {
			if(r<heights.length-1 && c<heights[r].length-1) {
				continue;
			}
			//dfs
			LinkedList<List<Integer>> stk = new LinkedList<>();
			if(!atlanticFlow[r][c]) {
				stk.push(Arrays.asList(r,c));
			}
			
			while( !stk.isEmpty()) {
				List<Integer> currNode = stk.pop();
				int r1 = currNode.get(0);
				int c1 = currNode.get(1);
				
				System.out.println(" atlanctic currNode --> "+currNode);
				
				if(!atlanticFlow[r1][c1]) {
					//res.add(currNode);
					atlanticFlow[r1][c1] = true;
				}
				
				//up
				if(r1>0 && heights[r1][c1] <= heights[r1-1][c1] && !atlanticFlow[r1-1][c1] ) {
					stk.push(Arrays.asList(r1-1,c1));
				}
				//down
				if(r1<heights.length-1 && heights[r1][c1] <= heights[r1+1][c1] && !atlanticFlow[r1+1][c1]) {
					stk.push(Arrays.asList(r1+1,c1));
				}
				
				//left
				if(c1>0 && heights[r1][c1] <= heights[r1][c1-1] && !atlanticFlow[r1][c1-1]) {
					stk.push(Arrays.asList(r1,c1-1));
				}
					
				//right
				if(c1<heights[r1].length-1 && heights[r1][c1] <= heights[r1][c1+1] && !atlanticFlow[r1][c1+1]) {
					stk.push(Arrays.asList(r1,c1+1));
				}	
					
				
				
			}
			
		}
	}
	
	for(int r=0;r<heights.length;r++) {
		for(int c=0;c<heights[r].length;c++) {
			if(atlanticFlow[r][c] && pacificFlow[r][c]) {
				res.add(Arrays.asList(r,c));
			}
		}
	}
	
	return res;
        
    }
	
    @Test
	public void testPacificAtlantic() {
    	
    	assertEquals(Arrays.asList(Arrays.asList(0,0)) , pacificAtlantic(new int[][] { new int[] {1}})) ;
    	
		MatcherAssert.assertThat( Arrays.asList(Arrays.asList(0,4),Arrays.asList(1,3),Arrays.asList(1,4),Arrays.asList(2,2), Arrays.asList(3,0),Arrays.asList(3,1),Arrays.asList(4,0))
				, Matchers.containsInAnyOrder( pacificAtlantic(new int[][] { new int[] {1,2,2,3,5}, new int[] {3,2,3,4,4}, new int[] {2,4,5,3,1}, new int[] {6,7,1,4,5}, new int[] {5,1,1,2,4}}).toArray(new List[] {})));
	
		MatcherAssert.assertThat(Arrays.asList( Arrays.asList(0,2),Arrays.asList(1,0),Arrays.asList(1,1),Arrays.asList(1,2),Arrays.asList(2,0), Arrays.asList(2,1), Arrays.asList(2,2)) 
		    		,Matchers.containsInAnyOrder( pacificAtlantic(new int[][] { new int[] {1,2,3}, new int[] {8,9,4} , new int[] {7,6,5} }).toArray(new List[] {}) ));

	MatcherAssert.assertThat(Arrays.asList(Arrays.asList(0,3), Arrays.asList(1,0),Arrays.asList(1,1),Arrays.asList(1,2),Arrays.asList(1,3),Arrays.asList(2,0),Arrays.asList(2,1),Arrays.asList(2,2),Arrays.asList(2,3),Arrays.asList(3,0),Arrays.asList(3,1),Arrays.asList(3,2),Arrays.asList(3,3))
			, Matchers.containsInAnyOrder( pacificAtlantic(new int[][] { new int[] { 1,2,3,4}, new int[] {12,13,14,5}, new int[] {11,16,15,6} , new int[] {10,9,8,7}}).toArray(new List[] {}) ));	

    
    MatcherAssert.assertThat( Arrays.asList(Arrays.asList(0,13),Arrays.asList(0,14),Arrays.asList(1,13),Arrays.asList(11,3),Arrays.asList(12,0),Arrays.asList(12,2),Arrays.asList(12,3),Arrays.asList(13,0),Arrays.asList(13,1),Arrays.asList(13,2),Arrays.asList(14,0),Arrays.asList(15,0))
    		, Matchers.containsInAnyOrder( pacificAtlantic(new int[][]{new int[]{8,13,11,18,14,16,16,4,4,8,10,11,1,19,7},new int[]{2,9,15,16,14,5,8,15,9,5,14,6,10,15,5},new int[]{15,16,17,10,3,6,3,4,2,17,0,12,4,1,3},new int[]{13,6,13,15,15,16,4,10,7,4,19,19,4,9,13},new int[]{7,1,9,14,9,11,5,4,15,19,6,0,0,13,5},new int[]{9,9,15,12,15,5,1,1,18,1,2,16,15,18,9},new int[]{13,0,4,18,12,0,11,0,1,15,1,15,4,2,0},new int[]{11,13,12,16,9,18,6,8,18,1,5,12,17,13,5},new int[]{7,17,2,5,0,17,9,18,4,13,6,13,7,2,1},new int[]{2,3,9,0,19,6,6,15,14,4,8,1,19,5,9},new int[]{3,10,5,11,7,14,1,5,3,19,12,5,2,13,16},new int[]{0,8,10,18,17,5,5,8,2,11,5,16,4,9,14},new int[]{15,9,16,18,9,5,2,5,13,3,10,19,9,14,3},new int[]{12,11,16,1,10,12,6,18,6,6,18,10,9,5,2},new int[]{17,9,6,6,14,9,2,2,13,13,15,17,15,3,14},new int[]{18,14,12,6,18,16,4,10,19,5,6,8,9,1,6}}).toArray(new List[] {})));
    }
    
   
}
