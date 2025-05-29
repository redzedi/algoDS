package fb;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidNumber {
	
	 public boolean isNumber(String s) {
	        String[] xs = s.split("");
	        boolean isValid = true;
         boolean isExponent = false;
         boolean isDecimal = false;
	        for(int i=0;i<xs.length;i++){
	            switch(xs[i]){
	                case "+":
	                    if(!(i==0 || "e".equals(xs[i-1]) || "E".equals(xs[i-1]))) {
	                    	return false;
	                    }
	                break;
	                case String s1 when s1.matches("\\d"):
	                	break;
	                case String s1 when s1.matches("[aA-zZ]"):
	                	if(!(("e".equals(s1) || "E".equals(s1)) && !( i==0 || i==xs.length)) ) {
	                		return false;
	                	}else{
                         isExponent = true;
                     }
	                	break;
	                case ".":
	                	if( (i==0 && xs.length ==1) || isExponent || isDecimal) {
	                		return false;
	                	}else{
                         isDecimal = true;
                     }
	                default:
	            }
	        }
	        return true;

	    }
	 
	 @Test
	 public void testIsNumber() {
		 assertTrue(isNumber("2e0"));
		 assertTrue(!isNumber("0e"));
		 assertTrue(!isNumber(".e1"));
	 }

}
