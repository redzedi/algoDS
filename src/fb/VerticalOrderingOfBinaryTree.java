package fb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.*;
import java.util.List;

import org.junit.Test;

import leetcode.binaryTree.TreeNode;

public class VerticalOrderingOfBinaryTree {
	
     private record NodeValCoords(int val , int x , int y) {}
	
	 public List<List<Integer>> verticalOrder(TreeNode root) {
		 ArrayList<NodeValCoords> nds = new ArrayList<>();
		 inOrderTraversal(root, nds, 0 ,0);
		 
		 Collections.sort(nds, (n1,n2)-> n1.x()!=n2.x()?n1.x()-n2.x():n1.y()-n2.y());
		 System.out.println(nds);
		 
		 ArrayList<List<Integer>> res = new ArrayList<>();
		 
		 if(nds.isEmpty()){
	            return res;
	          }
		 
		 res.add(new ArrayList<Integer>());
		 NodeValCoords curr = nds.get(0);
		 
		 res.get(0).add(curr.val());
		 
		 for (int i = 1,currIdx=0 ; i < nds.size(); i++) {
		     if(nds.get(i).x() != curr.x()) {
		    	 currIdx++;
		    	 curr = nds.get(i);
		    	 res.add(new ArrayList<Integer>());
		    	 
		     }
		     res.get(currIdx).add(nds.get(i).val());
			 
			
		}
		 
		 return res;
	        
	    }
	 
	 private void inOrderTraversal(TreeNode nd , List<NodeValCoords> xs , int x , int y) {
		 if(nd==null) {
			 return;
		 }else {
			 inOrderTraversal(nd.left , xs, x-1,y+1);
			 xs.add(new NodeValCoords( nd.val , x ,y));
			 inOrderTraversal(nd.right , xs, x+1, y+1);
		 }
	 }

	 @Test
	 public void testVerticalOrder1() {
		 
		 TreeNode n1 = new TreeNode(9);
		 TreeNode n2 = new TreeNode(15);
		 TreeNode n4 = new TreeNode(7);
		 TreeNode n3 = new TreeNode(20, n2,n4);
		 TreeNode rt = new TreeNode(3 , n1 , n3);
		 
		 List<List<Integer>> res = verticalOrder(rt);
		 
		 System.out.println(res);
		 
		 assertEquals(asList(asList(9), asList(3,15), asList(20), asList(7)),  res);
		 
	 }
	 
	 @Test
	 public void testVerticalOrder2() {
		 
		 TreeNode n1 = new TreeNode(4);
		 TreeNode n2 = new TreeNode(0);
		 TreeNode n3 = new TreeNode(1);
		 TreeNode n4 = new TreeNode(7);
		 TreeNode n5 = new TreeNode(9, n1,n2);
		 TreeNode n6 = new TreeNode(8, n3,n4);
		 TreeNode rt = new TreeNode(3 , n5 , n6);
		 
		 List<List<Integer>> res = verticalOrder(rt);
		 
		 System.out.println(res);
		 
		 assertEquals(asList(asList(4), asList(9), asList(3,0,1), asList(8) , asList(7)),  res);
		 
	 }
	 
	 @Test
	 public void testVerticalOrder3() {
		 
		 TreeNode n1 = new TreeNode(6);
		 TreeNode n2 = new TreeNode(10);
		 
		 TreeNode n5 = new TreeNode(5, null,n1);
		 TreeNode n6 = new TreeNode(4, null,n5);
		 TreeNode n7 = new TreeNode(2, n6,n2);
		 
		 
		 TreeNode n3 = new TreeNode(9);
		 TreeNode n4 = new TreeNode(11);
		 TreeNode n8 = new TreeNode(3, n3,n4);
		 
		 TreeNode rt = new TreeNode(1 , n7 , n8);
		 
		 List<List<Integer>> res = verticalOrder(rt);
		 
		 System.out.println(res);
		 
		 assertEquals(asList(asList(4), asList(2,5), asList(1,10,9,6), asList(3) , asList(11)),  res);
		 
	 }
	 
	 
	 public static void main(String[] args) {
		 List<List<Integer>> xs = asList(asList(1,1),asList(0,2),asList(1,2));
		 Collections.sort(xs, (x1,x2)->x1.get(0)!=x2.get(0)?x1.get(0)-x2.get(0):x1.get(1)-x2.get(1));
		 System.out.println(xs);
	}
}
