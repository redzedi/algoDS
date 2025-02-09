package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.PriorityQueue;

import org.junit.Test;


 class LFUCache {
	
	private HashMap<Integer, ValueHolder> data;
	
	
	private int capacity;
	
	PriorityQueue<LfuKeyNode> keyAcceessFreqHeap ;
	
	private int internalClock;
	
	public LFUCache(int capacity) {
		 data = new HashMap<>();
		 this.capacity = capacity;
		 keyAcceessFreqHeap = new PriorityQueue<>(( kn1, kn2)->{
		   int freqDiff = kn1.accessCount - kn2.accessCount;
		   return freqDiff!=0?freqDiff:kn1.lastAccessTs-kn2.lastAccessTs;
		 });
	}
	
	
	public void put(int key, int val) {
		if(!data.containsKey(key)) {
			
			LfuKeyNode newKn = new LfuKeyNode(key,1, this.internalClock++);
			data.put(key, new ValueHolder(val,newKn));
			
			if(keyAcceessFreqHeap.size()<capacity) {
				
				keyAcceessFreqHeap.add(newKn);
			}else {
				
				// TODO: the new entry will have a freq 1 which is less than all other frequency in the heap already
				
				LfuKeyNode ndToBeRemoved =  keyAcceessFreqHeap.poll();
				keyAcceessFreqHeap.add(newKn);
				data.remove(ndToBeRemoved.key);
			}
			
		}else {
			//update value of old key
			ValueHolder vl = data.get(key);
			vl.value = val;
			updateAccessCount(vl);
		}
		
		
	}


	private void updateAccessCount(ValueHolder vl) {
		keyAcceessFreqHeap.remove(vl.indexKeyNode);
		vl.indexKeyNode.accessCount++;
		vl.indexKeyNode.lastAccessTs = this.internalClock++;
		keyAcceessFreqHeap.add(vl.indexKeyNode);
	}
	
	public int get(int key) {
		ValueHolder vl = data.get(key);
		if(vl!=null)
		updateAccessCount(vl);
		return vl!=null?vl.value:-1;
	}
	
	public int size() {
		return data.size();
	}
	
	
	private class ValueHolder{
		
		public  int value;
		public final LfuKeyNode indexKeyNode;
		public ValueHolder(int value, LfuKeyNode indexKeyNode) {
			super();
			this.value = value;
			this.indexKeyNode = indexKeyNode;
		}
		
		
	}
	private class LfuKeyNode{
		public final int key;
		public int accessCount;
		public int lastAccessTs;
		public LfuKeyNode(int key, int accessCount, int lastAccessTs) {
			super();
			this.key = key;
			this.accessCount = accessCount;
			this.lastAccessTs = lastAccessTs;
		}
		
		
	}

}


public class LFUCacheTest{
	
	
	@Test
	public void testLFUCache() {
		// cnt(x) = the use counter for key x
		// cache=[] will show the last used order for tiebreakers (leftmost element is  most recent)
		LFUCache lfu = new LFUCache(2);
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
		
		LFUCache lfu = new LFUCache(2);
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