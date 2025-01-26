package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

class AllOne {
	
	private HashMap<String,Integer> keyToIndexMap;
	private TreeMap<Integer,ArrayList<String>> revFreqMap;
	
	private ArrayList<String> keys;
	
	private int maxKeyIdx=-1;
	private int minKeyIdx=-1;

    public AllOne() {
        keyToIndexMap = new HashMap<>();
        revFreqMap = new TreeMap<>();
        keys = new ArrayList<>(50000);
    }
    
    public void inc(String key) {
        
    	keys.add(key);
    	
    	if(maxKeyIdx == -1) {
    		maxKeyIdx=0;
    	}else {
    		
    		if(keys.get(maxKeyIdx) != key ) {
    			maxKeyIdx++;
    		}
    		
    	}
    	
    	if(minKeyIdx == -1) {
    		minKeyIdx=0;
    	}else {
    		
    		if(keys.get(minKeyIdx) == key ) {
    			minKeyIdx++;
    		}
    		
    	}
    	
    	
    	
    }
    
    public void dec(String key) {
    	Integer f = keyToIndexMap.computeIfPresent(key, (k,v)->v==null?1:v-1);
    	if(f == 0) {
    		keyToIndexMap.remove(key);
    		
    		revFreqMap.get(1).remove(key);
    		
    		if(revFreqMap.get(1).isEmpty()) {
    			revFreqMap.remove(1);
    		}
    		
    	}else {
    		revFreqMap.get(f+1).remove(key);
    		
    		if(revFreqMap.get(f+1).isEmpty()) {
    			revFreqMap.remove(f+1);
    		}
    		//revFreqMap.get(f).add(key);
    		revFreqMap.compute(f,(f1,ks)->{
        		if(ks == null) {
        			ArrayList<String> x = new ArrayList<>();
        			x.add(key);
        			return x;
        		}else {
        			ks.add(key);
        			return ks;
        		}
        	});
    	}
    	
    }
    
    public String getMaxKey() {
    	return maxKeyIdx==-1?"":keys.get(maxKeyIdx); 
        
    }
    
    public String getMinKey() {
    	return minKeyIdx==-1?"":keys.get(minKeyIdx); 
        
    }
    
    
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
public class AllOneTest2 {
	
	@Test
	public void testAllOne1() {
		AllOne allOne = new AllOne();
		allOne.inc("hello");
		allOne.inc("hello");
		
		assertEquals("hello", allOne.getMaxKey());
		; // return "hello"
		assertEquals("hello", allOne.getMinKey());
		allOne.inc("leet");
		assertEquals("hello", allOne.getMaxKey());
		assertEquals("leet", allOne.getMinKey());
	}
	
	@Test
	public void testAllOne2() {
		AllOne allOne = new AllOne();
		allOne.inc("hello");
		allOne.inc("hello");
		
		assertEquals("hello", allOne.getMaxKey());
		; // return "hello"
		assertEquals("hello", allOne.getMinKey());
		allOne.dec("hello");
		allOne.dec("hello");
		assertEquals("", allOne.getMaxKey());
		allOne.inc("hello");
		assertEquals("hello", allOne.getMinKey());
	}

}
