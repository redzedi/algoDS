package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class LongestStringChain {
	
	//https://leetcode.com/problems/longest-string-chain/description/?envType=company&envId=atlassian&favoriteSlug=atlassian-six-months
	
	//for memoization
	private HashMap<String,Integer> chLengthMap = null;
	
	 public int longestStrChain(String[] words) {
		 chLengthMap = new HashMap<>();
		 
		 Arrays.sort(words, (w1,w2)->w1.length()-w2.length());
		 
		 
		 int res = 0;
		 
		 for (int i = 0; i < words.length; i++) {
			
			 res = Math.max(res, strChainDfs(words, i));
		}
		 System.out.println(Arrays.toString(words));
		 System.out.println(chLengthMap.entrySet());
		 return res;
	        
	    }
	 
	 private int strChainDfs(String[] words, int frmIdx) {
		 if(chLengthMap.containsKey(words[frmIdx])) {
			 return chLengthMap.get(words[frmIdx]);
		 }
		 
		 chLengthMap.put(words[frmIdx],1);
		 
		 List<Integer> nxtNodeIdxs = getNextCandidates( words,  frmIdx);
		 
		 
		 
		 for(Integer nxtNodeIdx:nxtNodeIdxs) {
			 int  chainLength = strChainDfs( words,  nxtNodeIdx);
			 
			 System.out.println(" successor :: "+words[frmIdx]+" -- "+words[nxtNodeIdx]);
			 
			 chLengthMap.put(words[frmIdx],Math.max(chainLength+1, chLengthMap.get(words[frmIdx])));
		 }
		 
		return  chLengthMap.get(words[frmIdx]); 
	 }
	 
	 private List<Integer> getNextCandidates(String[] words, int frmIdx) {
		 
		 ArrayList<Integer> chldIdxs = new ArrayList<>();
		 
		 int currLen = words[frmIdx].length();
		 for(int i=frmIdx+1;i<words.length && words[i].length() < currLen+2 ;i++) {
			 
			 if(isChainSuccessor(words[frmIdx],words[i])){
				 chldIdxs.add(i);
			 }
			 
		 }
		 
		 return chldIdxs;
	 }
	 
	 private boolean isChainSuccessor(String parent , String chld) {
		 
		 if(chld.length() - parent.length() != 1) {
			 return false;
		 }
		 int prevMatch = 0;
		 for(int i=0;i<parent.length();i++) {
			int currMatch =  chld.indexOf(parent.charAt(i), prevMatch);
			 
			 if(currMatch == -1) {
				 return false;
			 }
			 
			 prevMatch  = currMatch+1;
			 
		 }
		 
		 return true;
	 }
	 
	 @Test
	 public void testLongestStrChain() {
		 
		 assertEquals(4, longestStrChain(new String[] {"a","b","ba","bca","bda","bdca"}));
		 assertEquals(5, longestStrChain(new String[] {"xbc","pcxbcf","xb","cxbc","pcxbc"}));
		 assertEquals(1, longestStrChain(new String[] {"abcd","dbqca"}));
		 
		 assertEquals(1, longestStrChain(new String[] {"bccbcbfea", "dccdcafaaeb", "eaef", "eec", "bbedafdeceb", "ffcabbbbced", "dcbcb", "ddb", "bddcbcfcd", "bfddddfbeac", "efafdeadee", "aae", "baacc", "f", "daddf", "bfbbed", "cdefbea", "cdeabcebfecf", "ecacdaa", "bfc", "bbbcc", "ebbeaefbedc", "ddd", "dedf", "baefd", "efccaab", "bdebadbefdb", "b", "bea"}));
	 }

}
