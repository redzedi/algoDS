package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.PriorityQueue;

import org.junit.Test;


 class LFUCache1 {
	 
	 
	 //https://leetcode.com/problems/lfu-cache/submissions/
	
	private HashMap<Integer, ValueNode> data;
	
	
	private int capacity;
	
	
	private FreqNode hd;
	private FreqNode tl;
	
	public LFUCache1(int capacity) {
		 data = new HashMap<>();
		 this.capacity = capacity;
	}
	
	
	public void put(int key, int val) {
		if(!data.containsKey(key)) {
			//new key addition
			ValueNode vn = new ValueNode(key);
			vn.value = val;
			
			
			//add to index
			if(hd == null) {
				//first time entry
				hd= new FreqNode(1);
				hd.valHd = vn;
				hd.valTl = vn;
				vn.owner = hd;
				tl = hd;
			}else {
				//insertion in a occupied map
				
				if(data.size() == capacity) {
					//eviction
					ValueNode nodeToEvict = hd.valHd;
					hd.valHd = hd.valHd.next;
					
					if(hd.valHd != null) {
						
						hd.valHd.prev = null;
					}else {
						hd.valTl = null;
					}
					
					data.remove(nodeToEvict.key);
				}
				
				if(hd.freq == 1) {
					ValueNode currTl = hd.valTl;
					
					if(hd.valTl != null) {
						
						hd.valTl.next = vn;
						vn.prev = hd.valTl;
					}else {
						hd.valTl = vn;
						hd.valHd = vn;
					}
					vn.owner = hd;
					hd.valTl = vn;
				}else {
					FreqNode nFreq = new FreqNode(1);
					nFreq.next = hd;
					hd.prev = nFreq;
					hd = nFreq;
					
					hd.valHd = vn;
					hd.valTl = vn;
					vn.owner = hd;
				}
			}
			
			// add to data store
			data.put(key, vn);
			
		}else {
			// update value
			
			ValueNode currVn  = data.get(key);
			
			//2. update new Value
			currVn.value = val;
			
			recordAccess(currVn);
			
		}
		
		
	}


	private void recordAccess(ValueNode currVn) {
		// 1. detach currVn
		if(currVn.prev != null) {
			
			currVn.prev.next = currVn.next;
		}else {
			currVn.owner.valHd = currVn.next;
		}
		
		if(currVn.next != null) {
			
			currVn.next.prev = currVn.prev;
		}else {
			currVn.owner.valTl = currVn.prev;
		}
		
		 //1.1 if the freqNode has become empty remove it 
		
		FreqNode oldFreqNode = currVn.owner;
		
       
		
		
		
		//3. attach currVn to the next frequency
		
		if(currVn.owner.next != null) {
			
			if( currVn.owner.next.freq == currVn.owner.freq+1 ) {
				//insert currVn at the tail here
				currVn.owner = currVn.owner.next;
				
				currVn.owner.valTl.next = currVn;
				currVn.prev = currVn.owner.valTl; 
				currVn.owner.valTl = currVn;
				
			}else {
				// create a new freqNode and insert currVn
				FreqNode nF = new FreqNode(currVn.owner.freq+1);
				
				nF.next = currVn.owner.next;
				nF.prev = currVn.owner;
				
				if(tl == currVn.owner) {
					tl = nF;
				}
				
				currVn.owner = nF;
				
				nF.valHd = currVn;
				nF.valTl = currVn;
				
				 
			}
			
		}else {
			//insert at the tail
			if( currVn.owner == tl) {
				
		       FreqNode nF = new FreqNode(currVn.owner.freq+1);
				
				nF.next = currVn.owner.next;
				nF.prev = currVn.owner;
				
				if(tl == currVn.owner) {
					tl = nF;
				}
				
				currVn.owner = nF;
				
				nF.valHd = currVn;
				nF.valTl = currVn;
				
			}else {
				System.out.println("This should not happen");
			}
			
		}
		
		  if(oldFreqNode.valHd == null ) {
				
				if( hd == oldFreqNode ) {
					hd = oldFreqNode.next;
					oldFreqNode.next = null;
				}else {
					
					oldFreqNode.prev.next = oldFreqNode.next;  
					oldFreqNode.next.prev = oldFreqNode.prev;  
				}
				
				
			}
	}


	
	
	public int get(int key) {
		ValueNode vl = data.get(key);
		if(vl!=null)
		recordAccess(vl);
		return vl!=null?vl.value:-1;
	}
	
	public int size() {
		return data.size();
	}
	
	

	
	private class ValueNode{
		public FreqNode owner;
		public final int key;
		public int value;
		
		public ValueNode prev;
		public ValueNode next;
		public ValueNode(int key) {
			super();
			this.key = key;
		}
		
		
	}
	
	
	private class FreqNode{
		
		public final int freq;
		public FreqNode prev;
		public FreqNode next;
		
		public ValueNode valHd;
		public ValueNode valTl;
		
		public FreqNode(int freq) {
			super();
			this.freq = freq;
		}
		
		
	}

}


public class LFUCacheTest2{
	
	
	@Test
	public void testLFUCache() {
		// cnt(x) = the use counter for key x
		// cache=[] will show the last used order for tiebreakers (leftmost element is  most recent)
		LFUCache1 lfu = new LFUCache1(2);
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
		
		LFUCache1 lfu = new LFUCache1(2);
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