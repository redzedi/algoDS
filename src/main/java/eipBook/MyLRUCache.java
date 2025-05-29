package eipBook;

import java.util.Comparator;


public class MyLRUCache {
	
	private static class ValueHolder implements Comparable<ValueHolder>{
		
		volatile private static long CLOCK=0L;
		
		final public int key;
		public int value;
		private long ts;
		
		
		
		public ValueHolder(int key, int value) {
			super();
			this.key = key;
			this.value = value;
			touch();
		}
		
		public void touch(){
			this.ts = ++CLOCK;
			
		}



		@Override
		public int compareTo(ValueHolder o) {
			return Long.compare(this.ts,o.ts);
		}
		
		
	}
	final private int capacity;
	
	private java.util.PriorityQueue<ValueHolder> heap = new java.util.PriorityQueue<ValueHolder>( );
	private java.util.HashMap<Integer,ValueHolder> kvMap = new java.util.HashMap<Integer,ValueHolder>();
    
    public MyLRUCache(int capacity) {
        this.capacity = capacity;
    }
    
    public int get(int key) {
    	ValueHolder res = fetchAndTouchKeyInHeap( key);
    	if(res != null){
    		return res.value;
    	}else{
    		return -1;
    	}
    }
    
    public void set(int key, int value) {
    	ValueHolder res = fetchAndTouchKeyInHeap( key);
    	if(res != null){
            res.value = value;    		
    	}else{
    		ValueHolder holder = new ValueHolder(key,value);
    		kvMap.put(key,holder);
    		if(heap.size() < capacity){
    			heap.add(holder);
    			
    		}else{
                ValueHolder val = heap.poll();    			
    			heap.add(holder);
    			kvMap.remove(val.key);
    		}
    		
    	}
    	
    }
    
    private ValueHolder fetchAndTouchKeyInHeap(int key){
    	ValueHolder res = kvMap.get(key);
    	if(res != null){
    		res.touch();
    		//heapify
//    		ValueHolder hd = heap.poll();
//    		heap.add(hd);
    		heap.remove(res);
    		heap.add(res);
    	}
    		return res;
    }
}
