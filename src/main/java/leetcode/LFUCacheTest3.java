package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.junit.Test;


 class LFUCache3 {
	 
	 
	 //https://leetcode.com/problems/lfu-cache/submissions/
	
	 // k -> [v,freq]
	private HashMap<Integer, Integer[]> data;
	
	
	private int capacity;
	
	// freq -> [k]
	private HashMap<Integer,LinkedHashSet<Integer>> freqs;
	
	private int minF;
	
	
	public LFUCache3(int capacity) {
		 data = new HashMap<>();
		 this.capacity = capacity;
		 freqs = new HashMap<>();
		 
		 
	}
	
	
	public void put(int key, int val) {
		if(!data.containsKey(key)) {
			//new key addition
			
			if(data.size() == capacity) {
				int minFKey = freqs.get(minF).removeFirst();
				data.remove(minFKey);
				
				if(freqs.get(minF).isEmpty()) {
					freqs.remove(minF);
				}

			}
			
			data.put(key, new Integer[] {val,1});
			
			freqs.putIfAbsent(1, new LinkedHashSet<>());
			
			freqs.get(1).add(key);
			
			minF = 1;
			
			
			}else {
			// update value
			
			Integer[] currVn  = data.get(key);
			
			//2. update new Value
			currVn[0] = val;
			//currVn[1]++;
			
			recordAccess4(key, currVn);
			
			
		}
		
		
	}


	private void recordAccess4(int key, Integer[] currVn) {
		
		currVn[1]++;
		
		if(freqs.containsKey(currVn[1]-1)) {
			freqs.get(currVn[1]-1).remove(key);
			
			if(freqs.get(currVn[1]-1).isEmpty()) {
				freqs.remove(currVn[1]-1);
				
				if(minF == currVn[1]-1) {
					minF = currVn[1];
				}
			}
		}
		
		
		freqs.putIfAbsent(currVn[1], new LinkedHashSet<>());
		
		freqs.get(currVn[1]).add(key);
	}


	


	


	
	
	public int get(int key) {
		Integer[] vl = data.get(key);
		if(vl!=null) {
			//vl[1]++;
			recordAccess4(key,vl);
		}
		return vl!=null?vl[0]:-1;
	}
	
	public int size() {
		return data.size();
	}
	
	

	
	

}


public class LFUCacheTest3{
	
	
	@Test
	public void testLFUCache() {
		// cnt(x) = the use counter for key x
		// cache=[] will show the last used order for tiebreakers (leftmost element is  most recent)
		LFUCache3 lfu = new LFUCache3(2);
		lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
		lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
		assertEquals(1,lfu.get(1));      // return 1
		                 // cache=[1,2], cnt(2)=1, cnt(1)=2
		lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
		                 // cache=[3,1], cnt(3)=1, cnt(1)=2
		assertEquals(-1,lfu.get(2));      // return -1 (not found)
		assertEquals(3,lfu.get(3));      // return 3
		                 // cache=[3,1], cnt(3)=2, cnt(1)=2
		lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
		                 // cache=[4,3], cnt(4)=1, cnt(3)=2
		assertEquals(-1,lfu.get(1));      // return -1 (not found)
		assertEquals(3,lfu.get(3));      // return 3
		                 // cache=[3,4], cnt(4)=1, cnt(3)=3
		assertEquals(4,lfu.get(4));      // return 4
		                 // cache=[4,3], cnt(4)=2, cnt(3)=3
	}
	
	
	@Test
	public void testLFUCache2(){
		
		LFUCache3 lfu = new LFUCache3(2);
		lfu.put(2,1);
		lfu.put(3,2);
		
		assertEquals(2,lfu.get(3));
		assertEquals(1,lfu.get(2));
		
		lfu.put(4,3);
		
		assertEquals(1,lfu.get(2));
		assertEquals(-1,lfu.get(3));
		assertEquals(3,lfu.get(4));
	}
	
}