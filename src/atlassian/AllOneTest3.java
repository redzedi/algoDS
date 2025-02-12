package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

class AllOne1 {
	
	private HashMap<String,Integer> freqMap;
	
	//this should be a linked list - have to be hand rolled
	private TreeMap<Integer,ArrayList<String>> revFreqMap;

    public AllOne1() {
        freqMap = new HashMap<>();
        revFreqMap = new TreeMap<>();
    }
    
    public void inc(String key) {
        
    	Integer f = freqMap.compute(key, (k,v)->v==null?1:v+1);
    	List<String> updatedKs = revFreqMap.computeIfPresent(f-1, (f2,ks)->{
    		ks.remove(key);
    		return ks;
    	});
    	
    	if(updatedKs != null && updatedKs.isEmpty()) {
    		revFreqMap.remove(f-1);
    	}
    	
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
    
    public void dec(String key) {
    	Integer f = freqMap.computeIfPresent(key, (k,v)->v==null?1:v-1);
    	if(f == 0) {
    		freqMap.remove(key);
    		
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
    	return revFreqMap.isEmpty()?"": revFreqMap.lastEntry().getValue().get(0); 
        
    }
    
    public String getMinKey() {
    	return revFreqMap.isEmpty()?"": revFreqMap.firstEntry().getValue().get(0); 
        
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
public class AllOneTest3 {
	
	@Test
	public void testAllOne1() {
		AllOne1 allOne = new AllOne1();
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
		AllOne1 allOne = new AllOne1();
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
