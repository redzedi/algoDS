package arcesium;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;

import org.junit.Test;

public class BrokenCalculator {
	
	//https://leetcode.com/problems/broken-calculator/description/
	
	 public int brokenCalc(int startValue, int target) {
		 
		  ArrayDeque<Integer[]> q = new ArrayDeque<>();
		  
		  q.add(new Integer[] {startValue, 0});
		  
		  int res  = Integer.MAX_VALUE;
		  
		  while(!q.isEmpty()) {
			  
			  Integer[] curr = q.poll();
			  
			  if(curr[0] == target) {
				  res = Math.min(res, curr[1]);
				  break;
			  }
			  
			  q.add(new Integer[] {curr[0]*2,curr[1]+1});
			  q.add(new Integer[] {curr[0]-1,curr[1]+1});
			  
		  }
		  
		  return res;
	        
	    }
	 
	 
	 @Test
	 public void testBrokenCalc() {
		 assertEquals(2, brokenCalc(2, 3));
		 assertEquals(2, brokenCalc(5,8));
		 assertEquals(3, brokenCalc(3,10));
		 
		 assertEquals(3, brokenCalc(1024,1));
	 }

}
