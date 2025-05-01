package fb;

import java.util.ArrayList;
import java.util.HashMap;



public class CopyListWithRandomPointer {
	
	class Node {
	    int val;
	    Node next;
	    Node random;

	    public Node(int val) {
	        this.val = val;
	        this.next = null;
	        this.random = null;
	    }
	}
	
	 public Node copyRandomList(Node head) {

	        Node curr = head;
	        int oi = 0;
	        ArrayList<Node> newNs = new ArrayList<>();
	        HashMap<Node,Integer> orgNode2Index = new HashMap<>();
	        while(curr != null){
	           Node newCurr = new Node(curr.val);
	           if(!newNs.isEmpty()){
	               newNs.get(newNs.size()-1).next = newCurr;
	           }
	           orgNode2Index.put(curr,oi++);
	           newNs.add(newCurr);
	           curr = curr.next;
	        }
	        curr = head;
	        for(int i=0;curr != null;curr=curr.next, i++){
	            if(curr.random != null){
	                newNs.get(i).random = newNs.get(orgNode2Index.get( curr.random));
	            }
	        }
	        
	        return !newNs.isEmpty()?newNs.get(0):null;

	        
	    }

}
