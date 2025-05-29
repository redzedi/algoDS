package fb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;

import org.junit.Test;

public class ShortestPathInBindaryMatrix {
	
	 public int shortestPathBinaryMatrix(int[][] grid) {
	        if(grid[0][0] != 0){
	            return -1;
	        }
	        ArrayDeque<Integer[]> q = new ArrayDeque<>();
	        int res = -1 , n = grid.length;
	        int[] xDiff = new int[]{-1,0,1};
	        q.offer(new Integer[]{0,0});
	        grid[0][0] = -1;
	        while(!q.isEmpty()){
	            Integer[] curr = q.poll();
	            if( curr[0] == n-1 && curr[1] == n-1  ){
	                res = grid[n-1][n-1]*-1;
	                break;
	            }

	            for(int xd = 0 ; xd < xDiff.length;xd++){
	                if( curr[0]+xDiff[xd] <0 ||curr[0]+xDiff[xd] >= n  ){
	                    continue;
	                }
	                int candX = curr[0]+xDiff[xd];
	                for(int yd = 0 ; yd < xDiff.length;yd++){
	                    if( curr[1]+xDiff[yd] <0 ||curr[1]+xDiff[yd] >= n  ){
	                    continue;
	                }
	                int candY = curr[1]+xDiff[yd];
	                if(grid[candX][candY] == 0){
	                    grid[candX][candY] = grid[curr[0]][curr[1]] -1;
	                    System.out.println("adding "+candX+" -- "+candY);
	                    q.offer(new Integer[]{candX,candY});
	                }
	                }

	            }

	        }
	        return res;

	        
	    }
	 
	 @Test
	 public void testShortestPath() {
		 assertEquals(4, shortestPathBinaryMatrix(new int[][]{new int[]{0,0,0},new int[]{1,1,0},new int[]{1,1,0} }));
	 }

}
