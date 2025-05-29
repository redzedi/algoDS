package salesforce;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.BitSet;

import org.junit.Test;

public class SmallestSubstringWIthIdenticalChar {
	
	
	//https://leetcode.com/problems/smallest-substring-with-identical-characters-i

	
	public int minLength(String s, int numOps) {
		
		int lo=1,hi=s.length(), minLen = Integer.MAX_VALUE;
		
		if(hi<2) {
			return hi;
		}
		
		while(lo<=hi) {
			int mid = (lo+hi)/2;
			
			if(mid==1 ) {
				if( checkIfAlternatingPattern(s, numOps)) {
					minLen = 1;
					hi= mid-1;
				}else {
					lo=mid+1;
				}
			
			
			}else {
				int maxWinSize = maxWinSizeIfSubstringPossible(s,mid,numOps); 
				if(maxWinSize != -1) {
					hi= mid-1;
					minLen = Math.min(minLen, maxWinSize);
				}else {
					lo=mid+1;
				
				}
			}
				
			
			
		}
		
		
		return minLen;
	}
	
	
	
	
	
	private boolean checkIfAlternatingPattern(String s, int numOps) {
		// TODO Auto-generated method stub
		char prev = s.charAt(0);
		boolean res = true; 
		
		for (int i = 1; i < s.length(); i++) {
			char curr  = s.charAt(i);
		   	if(prev == curr && --numOps<0) {
		   		res = false;
		   		break;
		   	}
		   	prev = curr;
		}
		
		return res;
	}





	private int maxWinSizeIfSubstringPossible(String s, int targetLen, int numOps) {
		// TODO Auto-generated method stub
		int currWinLen=1, winCnt=0;
		int maxWinSize = Integer.MIN_VALUE;
		boolean isTargetPossible = true;
		for (int i = 1; i < s.length() && isTargetPossible ; i++) {
		  
			if(s.charAt(i-1) == s.charAt(i)) {
				
				if(currWinLen > targetLen) {
					if(--numOps>=0) {
						winCnt++;
						currWinLen=0;
						//maxWinSize = targetLen-1;
					}else {
						isTargetPossible = false;
					}
					
				}
				
				maxWinSize = Math.max(maxWinSize, currWinLen);
				currWinLen++;
				
			}else {
				currWinLen = 0;
			}
		   
		}
		
		return isTargetPossible?maxWinSize:-1;
	}


	
	
	@Test
	public void testMinLength() {
		assertEquals(2,minLength( "000001", 1));
		assertEquals(1,minLength( "0000", 2));
		assertEquals(1,minLength("0101", 0));
		assertEquals(1,minLength("0", 0));
		assertEquals(2,minLength("00", 0));
		assertEquals(2,minLength("011", 0));
		
		assertEquals(2,minLength("0000", 1));
	}
	
	public static void main(String[] args) {
		System.out.println(6>>1);
		int[][] t = new int[2][2];
		//t[1][1] =0;
		System.out.println(t[1][0]);
		System.out.println(t[0].length);
		ArrayList<Integer>[] adjLst = new ArrayList[11];
		String[] ss = new String[2];
		BitSet n = new BitSet(5);
		n.set(1);
		System.out.println(n.nextSetBit(2));
		
		
 		
	}

}
