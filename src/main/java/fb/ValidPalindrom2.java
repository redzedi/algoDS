package fb;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidPalindrom2 {
	
	//https://leetcode.com/problems/valid-palindrome-ii/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	 public boolean validPalindrome(String s) {
		 int l=0,r=s.length()-1, al = -1 , ar=-1;
		 boolean isSkipPending = true;
		 
		 boolean isValid = true;
		 
		 do {
			 if(al != -1 && ar != -1) {
				 l = al;
				 r = ar;
				 al = -1;
				 ar = -1;
				 isValid = true;
			 }
			 
			 while(l<r) {
				 if(s.charAt(l) != s.charAt(r)) {
					 if(!isSkipPending) {
						 isValid = false;
						 break;
					 }
					 // either l can be deleted or r can be deleted
					 al = l;
					 ar = r-1;
					 
					 l++;
					 isSkipPending = false;
				 }else {
					 l++;
					 r--;
				 }
			 }
			 
			 
		 }while(al != -1 && ar != -1 && !isValid);
		
		
		 
	       return isValid; 
	    }
	 
	 @Test
	 public void testValidPalindrome() {
//		 assertTrue(  validPalindrome("aba"));
//		 assertTrue(  validPalindrome("abca"));
//		 assertTrue( ! validPalindrome("abc"));
//		 assertTrue(  validPalindrome("bddb"));
//		 assertTrue(  validPalindrome("deeee"));
		 
		 assertTrue(  validPalindrome("cbbcc"));
	 }

}
