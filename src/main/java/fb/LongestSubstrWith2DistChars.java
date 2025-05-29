package fb;

import java.util.Arrays;

public class LongestSubstrWith2DistChars {
	 public int lengthOfLongestSubstringTwoDistinct(String sOrig) {
	        int l=0, r=0, maxLen= Integer.MIN_VALUE;
	        int[] fMap = new int[26];
	        Arrays.fill(fMap,0);
	        int currUnqCnt =0;
	        String s = sOrig.toLowerCase();
	        while(l<=r && r<s.length()){
	            //growth
	           while(r<s.length() && (fMap[s.charAt(r)-'a'] >0 || currUnqCnt<2)){
	               if(fMap[s.charAt(r)-'a'] == 0){
	                  currUnqCnt++;
	               }
	               fMap[s.charAt(r)-'a']++;
	               //System.out.println("fMap[s.charAt(r)-'a'] --> "+s.charAt(r)+" freq --> "+fMap[s.charAt(r)-'a']);
	               r++;
	           }
	          // System.out.println("l --> "+l+" r --> "+r);
	            maxLen = Math.max(r-l,maxLen);
	            if(!(r < s.length()))
	               continue;
	            //fMap[s.charAt(r)-'a']++;
	           // currUnqCnt++;
	            while(currUnqCnt ==2){
	               // System.out.println("*fMap[s.charAt(l)-'a'] --> "+s.charAt(l)+" freq "+fMap[s.charAt(l)-'a']);
	                 if(--fMap[s.charAt(l)-'a'] == 0){
	                    currUnqCnt--;
	                 }
	              //   System.out.println("fMap[s.charAt(l)-'a'] --> "+s.charAt(l)+" freq "+fMap[s.charAt(l)-'a']);
	                 l++;
	            }
	         //   System.out.println("*l --> "+l+" *r --> "+r);

	        }

	       return maxLen;

	    }

}
