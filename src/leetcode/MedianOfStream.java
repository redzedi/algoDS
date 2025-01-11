package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.PriorityQueue;

import org.junit.Test;

class MedianFinder {
	
	private PriorityQueue<Integer> maxQ;
	private PriorityQueue<Integer> minQ;

    public MedianFinder() {
        maxQ = new PriorityQueue<>((x,y)->y-x);
        minQ = new PriorityQueue<>((x,y)->x-y);
    }
    
    public void addNum(int num) {
       if(maxQ.isEmpty() || num<maxQ.peek()) {
    	   maxQ.offer(num);
       }else {
    	   minQ.offer(num);
       }
       
    	   if(maxQ.size() - minQ.size() >1 ) {
    		   minQ.offer(maxQ.poll());
    	   }if(minQ.size() > maxQ.size()  ) {
    		   maxQ.offer(minQ.poll());
    		   
    	   }
    }
    
    public double findMedian() {
    	 if(maxQ.size() == minQ.size()) {
         	return (minQ.peek()+maxQ.peek())/2.0;
         }else {
         	return maxQ.peek();
         	
         }
    }
}

public class MedianOfStream{
	
	@Test
	public void testMedianFinder() {
		MedianFinder medianFinder = new MedianFinder();
		medianFinder.addNum(1);    // arr = [1]
		medianFinder.addNum(2);    // arr = [1, 2]
		 assertEquals( 1.5 , medianFinder.findMedian(),0.0); // return 1.5 (i.e., (1 + 2) / 2)
		medianFinder.addNum(3);    // arr[1, 2, 3]
		assertEquals(2.0 , medianFinder.findMedian(),0.0); // return 2.0
	}
	
}