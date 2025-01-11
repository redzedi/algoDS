package leetcode.slidingWindow;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MinWindowSubstring {
	
	/*
	 * https://leetcode.com/problems/minimum-window-substring/
	 */
	
	// 2- pointers method
	
	//TODO: what is the terminal condition ??
	
	public String minWindow1(String s, String t) {
		int st=0,en=0;
		String res= "";
		int resLength = Integer.MAX_VALUE;
		int remainingMatches=t.length();
		
		HashMap<Character,Integer> tRequiredFreqsMap = new HashMap<>();
		t.chars().forEach(x-> {
			tRequiredFreqsMap.putIfAbsent((char) x, 0);
			tRequiredFreqsMap.put((char)x, tRequiredFreqsMap.get((char)x)+1);
		} );

		HashMap<Character,Integer> tRunningFreqsMap = new HashMap<>();
		
		for(Character c:tRequiredFreqsMap.keySet()) {
			tRunningFreqsMap.put(c, 0);
		}
		
		while( en<s.length()) {
				
			//growth phase
				while (remainingMatches > 0  && en<s.length()) {
					if (tRequiredFreqsMap.containsKey(s.charAt(en)) && tRequiredFreqsMap.get(s.charAt(en)) > 0) {
						tRunningFreqsMap.computeIfPresent(s.charAt(en), (k, v) -> v + 1);
						if (tRunningFreqsMap.get(s.charAt(en)) <= tRequiredFreqsMap.get(s.charAt(en))) {
							remainingMatches--;
						}
					}
					en++;
				}
				boolean candidateFound = remainingMatches ==0;
				//now optimize the segment by growing the the st pointer,
				//until remainingMatches remain 0 or 
				//st pointer is at a position such that it is 1 less than the remainingMatches
				
				//System.out.println("unoptimized candidate --> "+s.substring(st,en));
				//optimize phase
				while (remainingMatches == 0 && st < s.length()) {
				//	System.out.println("In the optimize phase !! "+st+" -- "+s.charAt(st));
					if (tRequiredFreqsMap.containsKey(s.charAt(st)) && tRequiredFreqsMap.get(s.charAt(st)) > 0) {
						tRunningFreqsMap.computeIfPresent(s.charAt(st), (k, v) -> v - 1);
						if (tRunningFreqsMap.get(s.charAt(st)) < tRequiredFreqsMap.get(s.charAt(st))) {
							remainingMatches++;
						}
					}
					st++;
				}
				
				//candidate segment found - record the result
				if(candidateFound && ((en-st+1)<resLength)) {
					
					res = s.substring(st-1,en);
					resLength = en-st+1;
				}
				//System.out.println("candidate --> "+res);
			}
		
		return res;
	}
	
	
	
	public String minWindow2(String s, String t) {
		if (s.length() == 0 || t.length() == 0) {
			return "";
		}

		// Dictionary which keeps a count of all the unique characters in t.
		Map<Character, Integer> dictT = new HashMap<Character, Integer>();
		for (int i = 0; i < t.length(); i++) {
			int count = dictT.getOrDefault(t.charAt(i), 0);
			dictT.put(t.charAt(i), count + 1);
		}

		// Number of unique characters in t, which need to be present in the desired
		// window.
		int required = dictT.size();

		// Left and Right pointer
		int l = 0, r = 0;

		// formed is used to keep track of how many unique characters in t
		// are present in the current window in its desired frequency.
		// e.g. if t is "AABC" then the window must have two A's, one B and one C.
		// Thus formed would be = 3 when all these conditions are met.
		int formed = 0;

		// Dictionary which keeps a count of all the unique characters in the current
		// window.
		Map<Character, Integer> windowCounts = new HashMap<Character, Integer>();

		// ans list of the form (window length, left, right)
		int[] ans = { -1, 0, 0 };

		while (r < s.length()) {
			// Add one character from the right to the window
			char c = s.charAt(r);
			int count = windowCounts.getOrDefault(c, 0);
			windowCounts.put(c, count + 1);

			// If the frequency of the current character added equals to the
			// desired count in t then increment the formed count by 1.
			if (dictT.containsKey(c) && windowCounts.get(c).intValue() == dictT.get(c).intValue()) {
				formed++;
			}

			// Try and contract the window till the point where it ceases to be 'desirable'.
			while (l <= r && formed == required) {
				c = s.charAt(l);
				// Save the smallest window until now.
				if (ans[0] == -1 || r - l + 1 < ans[0]) {
					ans[0] = r - l + 1;
					ans[1] = l;
					ans[2] = r;
				}

				// The character at the position pointed by the
				// `Left` pointer is no longer a part of the window.
				windowCounts.put(c, windowCounts.get(c) - 1);
				if (dictT.containsKey(c) && windowCounts.get(c).intValue() < dictT.get(c).intValue()) {
					formed--;
				}

				// Move the left pointer ahead, this would help to look for a new window.
				l++;
			}

			// Keep expanding the window once we are done contracting.
			r++;
		}

		return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
	}
	
	
	
		  public String minWindow(String s, String t) {
	     
		  HashMap<Character,Integer> charFreqMap = new HashMap<>();
		  int st=0,en=0 , len=Integer.MAX_VALUE , remainingCharsToMatch = t.length();
		  String res = "";
		  
		  for(int i=0;i< t.length();i++) {
			  if(!charFreqMap.containsKey(t.charAt(i))) {
				  charFreqMap.put(t.charAt(i), 1);
			  }else {
				  charFreqMap.put(t.charAt(i), charFreqMap.get(t.charAt(i))+1);
			  }
		  }
		  
		  while( !(en==s.length() && st==s.length())) {
			  if(remainingCharsToMatch >0 && en<s.length()) {
				  Character enChar = s.charAt(en);
				  if(charFreqMap.containsKey(enChar) ) {
					  if(charFreqMap.get(enChar) > 0) {
						 charFreqMap.put(enChar, charFreqMap.get(enChar)-1); 
						 if(--remainingCharsToMatch ==0 ) {
							 String tmp = s.substring(st,en+1);
							 if(tmp.length()<len) {
								 len = tmp.length();
								 res = tmp;
							 }
						 }
						 
					  }else {
						  // what to do if it is already 0 or less - means there are more chars in s of the given type than in t
						  charFreqMap.put(enChar, charFreqMap.get(enChar)-1);
					  }
				  }else {
					  //current char not in t -- no impact
				  }
				  en++;
			  }else {
				  //slide the window start to find the minimal window
				  Character stChar = s.charAt(st);
				  
				  if(charFreqMap.containsKey(stChar)) {
					  
					  if(charFreqMap.get(stChar) == 0 ) {
						  
						  remainingCharsToMatch++;
						  charFreqMap.put(stChar, charFreqMap.get(stChar)+1); 
					  }else if(charFreqMap.get(stChar) < 0 ) {
						  charFreqMap.put(stChar, charFreqMap.get(stChar)+1); 
						  
					  }else {
						  System.out.println("** This should not happen remainingCharsToMatch= "+remainingCharsToMatch+"\t stChar= "+stChar+"\t count stChar="+ charFreqMap.get(stChar));
					  }
				 }
				  st++;
				  
				  if(remainingCharsToMatch ==0 ) {
						 String tmp = s.substring(st,en);
						 if(tmp.length()<len) {
							 len = tmp.length();
							 res = tmp;
						 }
					 }
			  }
		  }
		  
		  return res;
		  
	    }
	  
	  
	  @Test
	  public void testMinWindow() {
		  assertEquals("BANC",minWindow2( "ADOBECODEBANC",  "ABC"));
		  assertEquals("a",minWindow2( "a",  "a"));
		  assertEquals("",minWindow2( "a",  "aa"));
		  assertEquals("cwae",minWindow2( "cabwefgewcwaefgcf",  "cae"));
	  }
	  
	  public static void main(String[] args) {
		  "abcdef".chars().forEach(x->System.out.println(String.format("%c -- %d -- %s", x,x-'a', Character.valueOf( (char)x)) ));
		 System.out.println();
	}

}
