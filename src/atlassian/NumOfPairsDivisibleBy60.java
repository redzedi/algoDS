package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.TreeSet;

import org.junit.Test;

public class NumOfPairsDivisibleBy60 {
	
	 public int numPairsDivisibleBy60(int[] time) {
		 long[] remainderToIdxArr = new long[60];
		 long res =0;
		 Arrays.fill(remainderToIdxArr, 0L);
		 
		 for (int i = 0; i < time.length; i++) {
			 int modCurrVal = time[i]%60; // 0-59
			 remainderToIdxArr[modCurrVal]++;
			
		}
		 
		 for (int i = 0; i < remainderToIdxArr.length; i++) {
			 int remValueRequired = (60-i)%60;
			 
			 if(i > remValueRequired) {
				 
				 res = res + (remainderToIdxArr[i] * remainderToIdxArr[remValueRequired]);
			 }else if(i == remValueRequired){
				 res = res + (remainderToIdxArr[i]* (remainderToIdxArr[i]-1))/2;
			 }
		}
		 
		 return (int)res;
		 
	 }

	 public int numPairsDivisibleBy601(int[] time) {
		 
		 TreeSet<Short>[] remainderToIdxArr = new TreeSet[60];
		 
		 for (int i = 0; i < remainderToIdxArr.length; i++) {
			 remainderToIdxArr[i] = new TreeSet<Short>();
		}
		 
		 int res=0;
		 
		 //Arrays.fill(remainderToIdxArr, (short)-1);
		 
		 for (int i = 0; i < time.length; i++) {
				int modCurrVal = time[i]%60; // 0-59
				int remValueRequired = (60-modCurrVal)%60;
				
				
				if(!remainderToIdxArr[remValueRequired].isEmpty()) {
						System.out.println("i= "+remainderToIdxArr[remValueRequired]+" j= "+i+" incr="+remainderToIdxArr[remValueRequired].headSet((short)i));
						res =res + remainderToIdxArr[remValueRequired].headSet((short)i).size();	
				}else {
					// the counterpart is not there nothing to to do 
				}
				 remainderToIdxArr[modCurrVal].add((short)i) ; 
				
			
		}
		 
	        return res;
	    }
	 
	 @Test
	 public void testNumPairsDivBy60() {
		 assertEquals( 3, numPairsDivisibleBy60(new int[] {30,20,150,100,40}) );
		 assertEquals( 3, numPairsDivisibleBy60(new int[] {60,60,60}) );
		 
		 
	 }
	 
	 
}
