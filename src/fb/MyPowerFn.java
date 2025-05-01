package fb;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MyPowerFn {
	
	 public double myPow(double x, int n1) {

	       long n = n1;
	       
	       if(n1 == 0) {
	    	   return 1;
	       }
	       
	       if(n < 0){
	          n *= -1;
	          x = 1.0/x;
	       }
	       double res =1.0;
	       
	       while(n != 0) {
	    	   
	    	   if(n%2 == 1) {
	    		   res *= x;
	    		   n--;
	    	   }
	    	   
	    	   x *= x;
	    	   n /=2;
	       }

	      
	        return res;
	    }
	 
	 
	 @Test
	 public void testMyPow() {
		 
		 //assertEquals(1023, myPow(0.00001, 2147483647));
		 double act1 = myPow(2.0000, -2147483648);
		 double act2 = myPow(2.0000, Integer.MAX_VALUE);
		 System.out.println(" actual result "+act1);
		 System.out.println(" actual result "+act2);
		 assertTrue(0.0 ==act1);
		 
	 }

}
