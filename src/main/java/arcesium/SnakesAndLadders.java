package arcesium;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;

import org.junit.Test;

public class SnakesAndLadders {
	
 public int snakesAndLadders(int[][] board) {
	  
	 int res = Integer.MAX_VALUE;
	 ArrayDeque<Integer[]> qu = new ArrayDeque<>();
	 
	 int boardDim = board.length;
	 int maxNum = boardDim*boardDim;
	 qu.add(new Integer[] {1,0});
	 
	 int firstRowQuotient = (boardDim-1)%2;
	 
	 boolean isDone = false;
	 
//	 int[][]  labels = new int[maxNum+1][2];
//	 int l = 1;
//	 for (int row = board.length-1; row <= 0; row--) {
//		 for (int col = 0; col < board[0].length; col++) {
//			labels[l++] = new int[] {row,(row%2)==firstRowQuotient?col:boardDim-col-1};
//		}
//		
//	}
	 
	 while(!qu.isEmpty() && !isDone) {
		 
		 Integer[] currV = qu.pollFirst();
		 int currNum = currV[0];
		 
		 
		 
		 // check for any ladder or snake in the way 
		 
		 for(int i=1;i<=6;i++) {
			 
			 if(currNum+i == maxNum) {
				 
				 res = Math.min(res, currV[1]+1);
				 isDone = true;
				 break;
			 }else  if(currNum+i > maxNum) {
				 break;
			 }
			 
			 int[] currCoords = getCurrentCoords( boardDim,  currNum+i,firstRowQuotient);
			 
			 if(board[currCoords[0]][currCoords[1]] != -1) {
				 
				 System.out.println("adding "+board[currCoords[0]][currCoords[1]]);
					 //ladder base
					 qu.add(new Integer[] {board[currCoords[0]][currCoords[1]], currV[1]+1});
				 
			 }
		 }
		 
		 if(currNum+6 <= maxNum )
		 qu.add(new Integer[] { currNum+6,currV[1]+1});
		
		 
		 
		 
	 }
	 
     
	
	return res;
    }

private int[] getCurrentCoords(int boardDim, int currNum, int firstRowQuotient) {
	int currX = boardDim - (currNum / boardDim) -1;
	 int currY = (currX%2)==firstRowQuotient?  (currNum % boardDim)-1 : boardDim - (currNum % boardDim)-1;
	 
	 System.out.println(currNum +" --> ( "+currX+" , "+currY+" )");
	 return new int[] {currX, currY};
}


  @Test
  public void testSnakeAndLadders() {
	  assertEquals(4, snakesAndLadders(new int[][] {new int[]{-1,-1,-1,-1,-1,-1},new int[]{-1,-1,-1,-1,-1,-1},new int[]{-1,-1,-1,-1,-1,-1},new int[]{-1,35,-1,-1,13,-1},new int[]{-1,-1,-1,-1,-1,-1},new int[]{-1,15,-1,-1,-1,-1}}));
	  assertEquals(1, snakesAndLadders(new int[][] {new int[]{-1,-1},new int[]{-1,3}}));
	  assertEquals(1, snakesAndLadders(new int[][] {new int[]{-1,-1,-1},new int[]{-1,9,8},new int[]{-1,8,9}}));
	  
  }

}
