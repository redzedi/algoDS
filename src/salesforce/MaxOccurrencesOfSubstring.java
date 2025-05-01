package salesforce;

import static org.junit.Assert.assertEquals;

import java.util.BitSet;
import java.util.HashMap;

import org.junit.Test;

public class MaxOccurrencesOfSubstring {
	
	//https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/description/?envType=company&envId=salesforce&favoriteSlug=salesforce-three-months
	
	
	
	
	
	 public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
		 
		 HashMap<String,Integer> candidateSubstrings = new HashMap<>(); 
		 int maxFreq = Integer.MIN_VALUE ;
		 for (int i = minSize; i <= maxSize; i++) {
			 
			 for (int j = 0; j <= s.length()-i; j++) {
				 String currS = s.substring(j,j+i);
				 if(getNumOfUnqs(currS)<=maxLetters) {
						candidateSubstrings.put(currS, candidateSubstrings.getOrDefault(currS, 0)+1);
					maxFreq = Math.max(maxFreq, candidateSubstrings.get(currS));
					
				 }
			}
			
		}
		 return maxFreq;
	 }
	 
	 private int getNumOfUnqs(String ss) {
		 
		 BitSet b = new BitSet(26);
		 int cnt =0;
		 for (int i = 0; i < ss.length(); i++) {
			 
			 if(!b.get(ss.charAt(i)-'a')) {
				 cnt++;
				 b.set(ss.charAt(i)-'a'); 
			 }
			
		}
		 return cnt;
	 }
	
	
	 public int maxFreq1(String s, int maxLetters, int minSize, int maxSize) {
	     
		 int l=0,r=0, cnt=0 , maxFreq = Integer.MIN_VALUE , currUnqCnt = 0;;
		 
		 int[] unq = new int[26];
		 
		 HashMap<String,Integer> candidateSubstrings = new HashMap<>(); 
		 
		 
		 
		 while(l<=r && r<s.length()) {
		    
			 char curr = s.charAt(r);
			 int currIdx = curr -'a';
			 
			 boolean isCurrUnq = unq[currIdx] == 0;
			 
			 
			 
			
			 
			 int currSubstringSize = r-l+1;
			
			 unq[currIdx]++;
			 
			 if(isCurrUnq) {
				 currUnqCnt++;
			 }
			
			
			
			 
			 
			 while( ( currSubstringSize > maxSize || currUnqCnt > maxLetters ) && l<=r ){
				 //move l
				 
				 char currR = s.charAt(l);
				 int currRIdx = currR - 'a';
				 
				 boolean isCurrRUnq = unq[currRIdx]==1;
				 
				 if(isCurrRUnq) {
					 currUnqCnt--;
				 }
				 unq[currRIdx]--;
				 
				 
				 l++;
				 currSubstringSize = r-l+1;
				 
//				 maxFreq = recognizeCandidateSubstrings(s, maxLetters, minSize, maxSize, l, r, maxFreq, currUnqCnt,
//						candidateSubstrings, currSubstringSize);
				 
			 }
			 
			 maxFreq = recognizeCandidateSubstrings(s, maxLetters, minSize, maxSize, l, r, maxFreq, currUnqCnt,
						candidateSubstrings, currSubstringSize);
		
			 
			 r++;
			 
		 }
		 
		 return maxFreq;
	    }


	private int recognizeCandidateSubstrings(String s, int maxLetters, int minSize, int maxSize, int l, int r,
			int maxFreq, int currUnqCnt, HashMap<String, Integer> candidateSubstrings, int currSubstringSize) {
		if( currSubstringSize >= minSize && currSubstringSize <= maxSize && currUnqCnt <= maxLetters ) {
			
				String currK = s.substring(l,r+1);
				candidateSubstrings.put(currK, candidateSubstrings.getOrDefault(currK, 0)+1);
			maxFreq = Math.max(maxFreq, candidateSubstrings.get(currK));
			
		 }
		return maxFreq;
	}
	 
	 
	 @Test
	 public void testMaxFreq() {
		 assertEquals(2, maxFreq("aababcaab", 2,3,4));
		 assertEquals(2, maxFreq("aaaa", 1,3,3));
		 
		 assertEquals(3, maxFreq("aabcabcab", 2,2,3));
	 }
	

}
