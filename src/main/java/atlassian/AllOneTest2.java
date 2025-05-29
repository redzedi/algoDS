package atlassian;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashSet;

import org.junit.Test;

class AllOne {
	
	private HashMap<String,Node> freqMap;
	
	//this should be a linked list - have to be hand rolled
	//private TreeMap<Integer,ArrayList<String>> revFreqMap;
	
	
	private class Node{
		public final Integer freq;
		public final LinkedHashSet<String> keys;
		
		private Node prev;
		private Node next;
		public Node(Integer freq) {
			super();
			this.freq = freq;
			this.keys = new LinkedHashSet<>();
		}
		
		
	}
	
	private Node head;
	private Node tail;

    public AllOne() {
        freqMap = new HashMap<>();
    }
    
    public void inc(String key) {
        
    	
    	if(freqMap.containsKey(key)) {
    		
    		Node currFreqNode = freqMap.get(key);
    		
    		Node newFNode = null;
    		
    		if( currFreqNode.next == null || currFreqNode.next.freq != currFreqNode.freq+1) {
    			//create new node
    			 newFNode = new Node(currFreqNode.freq+1);
    			 newFNode.keys.add(key);
    			if(currFreqNode.next == null) {
    				currFreqNode.next = newFNode;
    				newFNode.prev = currFreqNode;
    				tail = newFNode;
    			}else {
    				//insert
    				Node tmp = currFreqNode.next;
    				currFreqNode.next = newFNode;
    				newFNode.prev = currFreqNode;
    				newFNode.next = tmp;
    				tmp.prev = newFNode;
    			}
    			
    		}else {
    			// append to the new next node keys
    			newFNode = currFreqNode.next;
    			newFNode.keys.add(key);
    		}
    		
    		freqMap.put(key, newFNode);
    		//remove the key from the current freq node and delete the node when the keys list is empty
    		removeKeyFromNodeAndCleanup(key, currFreqNode);
    		
    	}else {
    		if(head == null || head.freq != 1) {
        		
        		Node newHd = new Node(1);
        		newHd.keys.add(key);
        		freqMap.put(key, newHd);
        		
        		if(head == null) {
        			head = newHd;
        			tail = newHd;
        			
        		}else {
        			newHd.next = head;
        			head.prev = newHd;
        			head = newHd;
        		}
        		
        		
        	}else {
        		// head!=null && head.freq==1
        		freqMap.put(key, head);
        		head.keys.add(key);
        		
        	}
    		
    	}
    	
    	
    	
   
    }
    
    public void dec(String key) {
    	
    	if(!freqMap.containsKey(key)) {
    		throw new RuntimeException("");
    	}
		
		Node currFreqNode = freqMap.get(key);
		
		Node newFNode = null;
		
		if(currFreqNode.freq ==1) {
			//do nothing 
		}else if( currFreqNode.prev == null || currFreqNode.prev.freq != currFreqNode.freq-1) {
			//create new node
			 newFNode = new Node(currFreqNode.freq-1);
			 newFNode.keys.add(key);
			if(currFreqNode.prev == null) {
				currFreqNode.prev = newFNode;
				newFNode.next = currFreqNode;
				head = newFNode;
			}else {
				//insert before
				Node tmp = currFreqNode.prev;
				currFreqNode.prev = newFNode;
				newFNode.next = currFreqNode;
				newFNode.prev = tmp;
				tmp.next = newFNode;
			}
			
		}else {
			// append to the new next node keys
			newFNode = currFreqNode.prev;
			currFreqNode.prev.keys.add(key);
		}
		
		if(newFNode != null) {
			
			freqMap.put(key, newFNode);
		}else {
			freqMap.remove(key);
		}
		//remove the key from the current freq node and delete the node when the keys list is empty
		removeKeyFromNodeAndCleanup(key, currFreqNode);
		
	}

	private void removeKeyFromNodeAndCleanup(String key, Node currFreqNode) {
		currFreqNode.keys.remove(key);
		if(currFreqNode.keys.isEmpty()) {
			
			if(currFreqNode != head && currFreqNode != tail) {
				currFreqNode.prev.next = currFreqNode.next;
				currFreqNode.next.prev = currFreqNode.prev;
			}else {
				
				if(currFreqNode == head) {
					head = currFreqNode.next;
					if(head != null)
					 head.prev = null;
				} 
				if(currFreqNode == tail){
					tail = currFreqNode.prev;
					
					if(tail != null)
					 tail.next = null;
				}
				
			}
			
			
		}
	}
    
    public String getMaxKey() {
    	return tail == null ?"": tail.keys.getLast(); 
        
    }
    
    public String getMinKey() {
    	return head == null ?"": head.keys.getLast(); 
        
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
	
	@Test
	public void testAllOne3() {
		AllOne allOne = new AllOne();
		
		allOne.inc("hello");
		allOne.inc("goodbye");
		allOne.inc("hello");
		allOne.inc("hello");
		
		assertEquals("hello", allOne.getMaxKey());
		allOne.inc("leet");
		allOne.inc("code");
		allOne.inc("leet");
		allOne.dec("hello");
		allOne.inc("leet");
		allOne.inc("code");
		allOne.inc("code");
		; // return "hello"
		assertEquals("code", allOne.getMaxKey());
		
	}
	
	@Test
	public void testAllOne4() {
		AllOne allOne = new AllOne();
		
		allOne.inc("a");
		allOne.inc("b");
		allOne.inc("b");
		allOne.inc("b");
		allOne.inc("b");
		allOne.dec("b");
		allOne.dec("b");
		
		assertEquals("b", allOne.getMaxKey());
		assertEquals("a", allOne.getMinKey());
		
		
	}

}
