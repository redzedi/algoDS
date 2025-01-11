package leetcode;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Test;

public class TopKFrequentElements {
	
	// https://leetcode.com/problems/top-k-frequent-elements/
	
	  public int[] topKFrequent(int[] nums, int k) {
	        
		  PriorityQueue<Map.Entry<Integer, Integer>> hp = new PriorityQueue<>(k,(e1,e2)->e1.getValue()-e2.getValue());
		  HashMap<Integer,Integer> freqMap = new HashMap<>();
		  
		  for(int i=0;i<nums.length;i++) {
			  if(!freqMap.containsKey(nums[i])) {
				  freqMap.put(nums[i], 1);
			  }else {
				  freqMap.put(nums[i], freqMap.get(nums[i])+1);
			  }
		  }
		  
		  for(Map.Entry<Integer, Integer> e:freqMap.entrySet()) {
			  if(hp.size() <k) {
				  hp.add(e);
			  }else {
				  if(hp.peek().getValue() < e.getValue()) {
					  hp.poll();
					  hp.offer(e);
				  }
			  }
		  }
		  
		  int[] res = new int[k];
		  int j=0;
          for(Map.Entry<Integer, Integer>e:hp) {
        	  res[j++] = e.getKey();
          }
		  
		  return res;
		  
	    }
	  //O(n) time possible with quick select algo
	  
	  
	  @Test
	  public void testTopKFreqElements() {
		  assertThat( new Integer[] {1,2} , IsArrayContainingInAnyOrder.arrayContainingInAnyOrder ( topKFrequent(new int[] {1,1,1,2,2,3}, 2)));
		  //assertArrayEquals(new int[] {1,2} , topKFrequent(new int[] {1,1,1,2,2,3}, 2));
		  //assertArrayEquals(new int[] {1,} , topKFrequent(new int[] {1}, 1));
	  }
	  
	  
	  public static void main(String[] args) {
		  PriorityQueue<Integer> hp = new PriorityQueue<>();
		  hp.addAll(Arrays.asList(5,4,3,2,1));
		  
		  while(hp.peek() != null) {
			  System.out.println(hp.poll());
		  }
		  
		System.out.println();
	}

}
