package fb;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class RandomPickWithWeight {
	
	
	class Solution {

		private Random rnd;
		private int[] prefixSums;
		private int sum;
		
	    public Solution(int[] w) {
	    	prefixSums = new int[w.length];
	    	int acc=0;
	        for (int i = 0; i < w.length; i++) {
				acc += w[i];
				prefixSums[i] = acc;
			}
	        rnd = new Random();
	        sum = acc;
	    }
	    
	    public int pickIndex() {
	    	int curr = rnd.nextInt(1,sum+1);
	    	int res = Arrays.binarySearch(prefixSums, curr);
	       return res>=0?res: -1*(res+1);     
	    }
	}
	
	
	
	@Test
	public void testPickIndex1() {
		Solution s = new Solution(new int[] {0});
		assertEquals(0, s.pickIndex());
	}
	
	public static void main(String[] args) {
		Random rnd = new Random(10);
		
		int evnCnt=0;
		for (int i = 0; i < 10000; i++) {
			int curr = rnd.nextInt(0,4);
			if(curr >= 1 ) {
				evnCnt++;
			}
			
		}
		
		System.out.println("Even perc --> "+ (double)evnCnt/10000);
		System.out.println("Even cnt --> "+ evnCnt);
		System.out.println(Arrays.binarySearch(new int[] {2,5,7}, 3));
	}

}
