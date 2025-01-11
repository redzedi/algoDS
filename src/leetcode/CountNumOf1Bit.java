package leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CountNumOf1Bit {
	
	
	//https://leetcode.com/problems/number-of-1-bits/
	
	 // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int cnt = 0;
        while(n != 0x00000000) {
        	n &= (n-1);
        	cnt++;
        }
        return cnt;
    }
    
    @Test
    public void testHammingWeight() {
    	assertEquals(31,hammingWeight(-3));
    }
    
    public static void main(String[] args) {
		System.out.println(String.format("%d", (0xffffffff)));
	}

}
