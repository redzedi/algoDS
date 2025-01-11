package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SpiralMatrix {

	// https://leetcode.com/problems/spiral-matrix/description/

public List<Integer> spiralOrder1(int[][] matrix) {
		
		ArrayList<Integer> res = new ArrayList<>();
		
		for(int m = matrix[0].length,  n = matrix.length , x=0,y=0;m>0||n>0;n-=2,m-=2,x++,y++) {
			int totalNum = n>1?(2*m + 2*n -4):m;
			int currX=x, currY=y;
			for(int c=0;c<totalNum;c++) {
				//control loop
				if(currX < x+m && currY == y) {
					res.add(matrix[currY][currX++]);
				}else if(currX == x+m && currY <y+n-1) {
					res.add(matrix[++currY][currX-1]);
				}else if(currX > x && currY ==y+n-1) {
					if(currX == x+m) {
						currX--;
					}
					res.add(matrix[currY][--currX]);
				}else if(currX == x && currY >y) {
					res.add(matrix[--currY][currX]);
				}
			}
		}
		return res;
	}


public List<Integer> spiralOrder2(int[][] matrix) {
	
	ArrayList<Integer> res = new ArrayList<>();
	
	for(int m = matrix[0].length,  n = matrix.length , x=0,y=0; m>0||n>0; n-=2,m-=2,x++,y++) {
		int currX=x, currY=y;
		System.out.println(m+" <--m , n-->"+n);
		
		 do {
			//control loop
			if(currX < x+m && currY == y) {
				res.add(matrix[currY][currX++]);
			}else if(currX == x+m && currY <y+n-1) {
				res.add(matrix[++currY][currX-1]);
			}else if(currX > x && currY ==y+n-1) {
				if(currX == x+m) {
					currX--;
				}
				res.add(matrix[currY][--currX]);
			}else if(currX == x && currY >y) {
				res.add(matrix[--currY][currX]);
			}
			System.out.println(currX+" ,  "+currY+" -- "+res);
		}while(!(currX==x && currY == y+1));
		 System.out.println("out of the control loop");
	}
	return res;
}

public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> res = new ArrayList<>();
    int left = 0, right = matrix[0].length - 1;
    int top = 0, bottom = matrix.length - 1;
    int dir = 0;
    while(left <= right && top <= bottom) {
        if(dir == 0) { // Traverse from left to right
            for(int i = left; i <= right; i ++) 
            res.add(matrix[top][i]);
            top ++;
        }
        else if(dir == 1) { // Traverse from top to bottom
            for(int i = top; i <= bottom; i ++)
            res.add(matrix[i][right]);
            right --;
        }
        else if(dir == 2) { // Traverse from right to left
            for(int i = right; i >= left; i --) 
            res.add(matrix[bottom][i]);
            bottom --;
        }
        else if(dir == 3) { // Traverse from bottom to top
            for(int i = bottom; i >= top; i --)
            res.add(matrix[i][left]);
            left ++;
        }
        dir = (dir + 1) % 4;
    }
    return res;
  }

  



	
	
	@Test
	public void testSpiralOrder() {
		assertEquals( Arrays.asList(1,2,3,4,8,12,11,10,9,5,6,7) , spiralOrder(new int[][]{new int[]{1,2,3,4},new int[]{5,6,7,8},new int[]{9,10,11,12}}));
		assertEquals( Arrays.asList(1,2,3,6,9,8,7,4,5) , spiralOrder(new int[][]{new int[]{1,2,3,4},new int[]{5,6,7,8},new int[]{9,10,11,12}}));
		assertEquals( Arrays.asList(1,2,3,6,9,8,7,4,5) , spiralOrder(new int[][]{new int[]{6,9,7}}));
	}
	
	
	

}
