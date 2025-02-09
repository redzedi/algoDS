package atlassian;

import java.util.HashMap;
import java.util.PriorityQueue;

public class MyLFUCache<K,V> {
	
	private HashMap<K, ValueHolder> data;
	
	
	private int capacity;
	
	PriorityQueue<LfuKeyNode> keyAcceessFreqHeap ;
	
	public MyLFUCache(int capacity) {
		 data = new HashMap<>();
		 this.capacity = capacity;
		 keyAcceessFreqHeap = new PriorityQueue<>(( kn1, kn2)->kn1.accessCount - kn2.accessCount);
	}
	
	
	public void put(K key, V val) {
		if(!data.containsKey(key)) {
			
			LfuKeyNode newKn = new LfuKeyNode(key,1);
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
			keyAcceessFreqHeap.remove(vl.indexKeyNode);
			vl.indexKeyNode.accessCount++;
			keyAcceessFreqHeap.add(vl.indexKeyNode);
		}
		
		
	}
	
	public V get(K key) {
		return data.get(key).value;
	}
	
	public int size() {
		return data.size();
	}
	
	
	private class ValueHolder{
		
		public  V value;
		public final LfuKeyNode indexKeyNode;
		public ValueHolder(V value, LfuKeyNode indexKeyNode) {
			super();
			this.value = value;
			this.indexKeyNode = indexKeyNode;
		}
		
		
	}
	private class LfuKeyNode{
		public final K key;
		public int accessCount;
		public LfuKeyNode(K key, int accessCount) {
			super();
			this.key = key;
			this.accessCount = accessCount;
		}
		
		
	}

}
