package fb;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

//Definition for a Node.



public class BstToCircularDoublyLinkedList {
	
	class Node {
		 public int val;
		 public Node left;
		 public Node right;

		 public Node() {}

		 public Node(int _val) {
		     val = _val;
		 }

		 public Node(int _val,Node _left,Node _right) {
		     val = _val;
		     left = _left;
		     right = _right;
		 }
		};
		
	   	public Node treeToDoublyList(Node root) {
				if(root == null) {
					return null;
				}
				
				Node leftLst = treeToDoublyList(root.left);
				Node rightLst = treeToDoublyList(root.right);


				root.left = root;
				root.right = root;

	              Node leftHead = root;
				
				if(leftLst != null) {
	                leftHead = leftLst;
					Node leftTail = leftLst.left;
					root.left =   leftTail; 
					leftTail.right = root;
					
					root.right = leftLst;
	                leftLst.left = root;
				}
				
				if(rightLst != null) {
					Node rightTail = rightLst.left;
	                // leftHead = root.right;
	                rightTail.right = leftHead;

	                
					root.right =   rightLst; 
					rightLst.left = root;
	                leftHead.left = rightTail;
					

					
				}
				
				
				
		        return leftHead;
		    }
	
		
	
		
	 @Test	
      public void testTreeToDoublyList() {
		 Node n1 = new Node(1);
		 Node n3 = new Node(3);
		 Node n2 = new Node(2,n1,n3);
		 
		 Node n5 = new Node(5);
		  
		 
		 Node rt = new Node(4,n2,n5);
		 
		 assertEquals(null,treeToDoublyList(rt));
    	  
      }
	 
	 public static void main(String[] args) {
		Random rnd  = new Random();
		
		
	}
}
