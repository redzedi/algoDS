package fb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;

import org.junit.Test;

class Node {
	public int val;
	public Node left;
	public Node right;
	public Node parent;
	
	public Node(int val , Node p , boolean isLeft) {
		this.val = val;
		this.parent = p;
		
		if(p != null) {
			if(isLeft) {
				p.left = this;
			}else {
				p.right = this;
			}
		}
		
	}
};

public class LCAOfBinaryTreeWithParentPointer {

	   public Node lowestCommonAncestor(Node p, Node q) {
		   Node curr = p , prev = null;
		  
		   for(; curr != null ; prev=curr,curr=curr.parent) {
			   if(curr.val == q.val) {
				   break;
			   }
		   }
		   
		   if(curr == null) {
			   curr = q;
			   for(; curr != null ; prev = curr,curr=curr.parent) {
				   if(curr.val == p.val) {
					   break;
				   }
			   }
		   }
		   
		   return curr!=null?curr:prev;
		   
	       
	        
	    }

	    private  ArrayDeque<Node> getAncestors(Node p){
	    	ArrayDeque<Node> ancestors = new ArrayDeque<>();

	        for(Node curr = p ; curr != null;curr=curr.parent){
	            ancestors.push(curr);
	        } 
	        return ancestors;
	    }
	    
	    @Test
	    public void testLCA() {
	    	
	    	Node rt = new Node(3, null,false);
	    	Node n5 = new Node(5, rt,true);
	    	Node n6 = new Node(3, n5,true);
	    	Node n2 = new Node(2, n5,false);
	    	Node n7 = new Node(2, n2,true);
	    	Node n4 = new Node(4, n2,false);
	    	Node n1 = new Node(1, rt,false);
	    	Node n0 = new Node(0, n1,true);
	    	Node n8 = new Node(8, n1,false);
	    	
	    	
	    	
	    	
	    	assertEquals(3 , lowestCommonAncestor(n5, n1).val);
	    	
	    	assertEquals(5 , lowestCommonAncestor(n5, n4).val);
	    }
	    

}
