package fb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

public class LargestIsland {
	
	   public int largestIsland(int[][] grid) {
	        int currLabel = 2;
	        int[] delta = new int[]{-1,0,1};
	        HashMap<Integer,Integer> treeSizeMap = new HashMap<>();
	        int n  = grid.length, res = Integer.MIN_VALUE;
	        //mark all the trees first
	        for(int i=0;i<n;i++){
	             for(int j=0;j<n;j++){
	                  if(grid[i][j] == 1){
	                      int currTreeLabel = currLabel++;
	                      ArrayDeque<Integer[]> stk = new ArrayDeque<>();
	                      stk.push(new Integer[]{i,j});
	                      grid[i][j] = currTreeLabel;
	                      treeSizeMap.put(currTreeLabel , 0);
	                      while(!stk.isEmpty()){
	                        Integer[] curr = stk.pop();
	                        treeSizeMap.computeIfPresent(currTreeLabel , (k,v)->v+1);
	                        for(int k=0;k<2;k++){
	                            for(int l=0;l<delta.length;l++){
	                                int candI = k==0?curr[0]:curr[0]+delta[l], candJ = k==1?curr[1]:curr[1]+delta[l];

	                                if(candI>=0 && candI<n && candJ>=0 && candJ<n && grid[candI][candJ] == 1){
	                                    grid[candI][candJ] = currTreeLabel;
	                                    stk.push(new Integer[]{candI, candJ});

	                                }
	                        }
	                      }
	                  }

	                  res = Math.max(res , treeSizeMap.get(currTreeLabel) );
	             }

	        }
	        
	    }

	     for(int i=0;i<n;i++){
	             for(int j=0;j<n;j++){
	                  if(grid[i][j] == 0){
	                    int connectingTreeSize = 1;
	                    HashSet<Integer> matchedLabels = new HashSet<>();
	                     for(int k=0;k<2;k++){
	                            for(int l=0;l<delta.length;l++){
	                                //int candI = i+delta[k], candJ = j+delta[l];
	                                int candI = k==0?i:i+delta[l], candJ = k==1?j:j+delta[l];
	                                //get sum of the bordering trees
	                                
	                                if(candI>=0 && candI<n && candJ>=0 && candJ<n && grid[candI][candJ] != 0 && !matchedLabels.contains(grid[candI][candJ])){
	                                    matchedLabels.add(grid[candI][candJ]);
	                                    if( treeSizeMap.containsKey(grid[candI][candJ])){
	                                        connectingTreeSize += treeSizeMap.get(grid[candI][candJ]);
	                                    }
	                                    
	                     
	                                }
	                        }
	                      }
	                    //   if(connectingTreeSize > 0){
	                    //     connectingTreeSize++;
	                    //   }
	                      res = Math.max(res, connectingTreeSize );
	                  }
	             }
	     }
	     return res;
	}
	   
	   
	   @Test
	   public void testLargestIsland() {
		   assertEquals(9, largestIsland(new int[][] {new int[] {1,1,1},new int[] {1,1,1},new int[] {1,1,1}}));
	   }

}
