package fb;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidWordAbbreviation {
	
  public boolean validWordAbbreviation(String word, String abbr) {
        int wIdx =0 , i =0;
        boolean isValid = true;
        for (; i < abbr.length() && isValid && wIdx < word.length(); ) {
        	char currAbbr = abbr.charAt(i);
        	if(  Character.isLetter(currAbbr) ) {
        		if(word.charAt(wIdx) != currAbbr) {
        			isValid = false;
        		}else {
        			i++;
        			wIdx++;
        		}
        	}else {
        		// if it is num
        		int j = i;
        		for(; j < abbr.length() && Character.isDigit(abbr.charAt(j));j++ );
        		
        		 String currSubstr = abbr.substring(i, j);
        		 
        		 if(currSubstr.startsWith("0")) {
        			 isValid=false;
        			 break;
        		 }
        		
        		int substrLen = Integer.parseInt(currSubstr);
        		
        		if(substrLen > word.length()-wIdx ) {
        			isValid = false;
        		}else {
        			i =j;
        			wIdx += substrLen;
        		}
        		
        		
        	}
			
		}
        
        return isValid && wIdx==word.length() && i == abbr.length();
    }
  
  
  @Test
  public void testIsValidWordAbbr() {
	  assertTrue(validWordAbbreviation("internationalization", "i12iz4n"));
	  assertTrue(!validWordAbbreviation("apple", "a2e"));
	  assertTrue(validWordAbbreviation("internationalization", "i5a11o1"));
	  assertTrue(!validWordAbbreviation("abbde","a1b01e"));
	  
	  assertTrue(!validWordAbbreviation("hi","hi1"));
  }

}
