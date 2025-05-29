package eipBook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import org.junit.Test;


public class TreeTraversals {
	
	 static class TreeNode {
	      int val;
	      TreeNode left;
	      TreeNode right;
	      TreeNode(int x) {
	       val = x;
	       left=null;
	       right=null;
	      }}
	 
	 //19 min
	 public ArrayList<Integer> inorderTraversal(TreeNode A) {
		 Stack<TreeNode> as = new Stack<TreeNode>();
		 ArrayList<Integer> res  =new ArrayList<Integer>();
		 TreeNode curr = A;
		 while(!as.isEmpty() || curr != null){
			 while(curr != null){
				 as.push(curr);
				 curr = curr.left;
			 }
			 curr = as.pop();
			 res.add(curr.val);
			 curr = curr.right;
			 //as.push(curr);
			 
		 }
		 
		 
		 return res;
		 
		 
	    }
	 
	 
	 public ArrayList<Integer> postOrderTraversal(TreeNode A) {
		 Stack<TreeNode> as = new Stack<TreeNode>();
		 ArrayList<Integer> res  =new ArrayList<Integer>();
		 HashSet<TreeNode> touched = new HashSet<TreeNode>();
		 as.push(A);
		 TreeNode NULL = new TreeNode(-1);
		 while(!as.isEmpty()){
			 TreeNode curr = as.pop();
			 if(touched.contains(curr)){
				 res.add(curr.val);
			 }else if(curr==NULL || curr==null){
				 //touched.add(e)
				 //continue
			 }else{
				 touched.add(curr);
				 as.push(curr);
				 if(curr.right == null){
					 as.push(NULL);
				 }else{
					 as.push(curr.right);
				 }
				 if(curr.left == null){
					 as.push(NULL);
					 }else{
					 as.push(curr.left);
				 }
			 }
		 }
		 
		 
		 return res;
		 
		 
	    }
	 
	 
	 public ArrayList<Integer> postOrderTraversal_2Stacks(TreeNode A) {
		 Stack<TreeNode> s1 = new Stack<TreeNode>();
		 Stack<TreeNode> s2 = new Stack<TreeNode>();
		 ArrayList<Integer> res  =new ArrayList<Integer>();
         s1.push(A);
		 while(!s1.isEmpty()){
			 TreeNode curr = s1.pop();
			 if(curr.left != null)
				 s1.push(curr.left);
			 if(curr.right != null)
				 s1.push(curr.right);
			 System.out.println("pushing to s2 --> "+curr.val);
			 s2.push(curr);
		 }
		 while(!s2.isEmpty()){
			 res.add(s2.pop().val);
		 }
		 return res;
	    }
	 
	 public ArrayList<Integer> postOrderTraversal_1Stack(TreeNode node) {
	        Stack<TreeNode> stk = new Stack<TreeNode>();
	        ArrayList<Integer> list = new ArrayList<Integer>();
	          
	        // Check for empty tree
	        if (node == null)
	            return list;
	        stk.push(node);
	        TreeNode prev = null;
	        while (!stk.isEmpty()) 
	        {
	            TreeNode current = stk.peek();
	  
	            /* go down the tree in search of a leaf an if so process it 
	            and pop stack otherwise move down */
	            if (prev == null || prev.left == current || 
	                                        prev.right == current) 
	            {
	                if (current.left != null)
	                    stk.push(current.left);
	                else if (current.right != null)
	                    stk.push(current.right);
	                else
	                {
	                    stk.pop();
	                    list.add(current.val);
	                }
	  
	                /* go up the tree from left node, if the child is right 
	                   push it onto stack otherwise process parent and pop 
	                   stack */
	            } 
	            else if (current.left == prev) 
	            {
	                if (current.right != null)
	                    stk.push(current.right);
	                else
	                {
	                    stk.pop();
	                    list.add(current.val);
	                }
	                  
	                /* go up the tree from right node and after coming back
	                 from right node process parent and pop stack */
	            } 
	            else if (current.right == prev) 
	            {
	                stk.pop();
	                list.add(current.val);
	            }
	  
	            prev = current;
	        }
	  
	        return list;
	        }
	 

	 
	 //test
	 
	 @Test
	 public void testInorderTraversal(){
		 TreeNode c1 = new TreeNode(2);
		 c1.left = new TreeNode(3);
		 
		 TreeNode rt = new TreeNode(1);
		 rt.right = c1;
		 
		 assertEquals(new ArrayList<Integer>(Arrays.asList(1,3,2)), inorderTraversal(rt));
		 
	 }
	 
	 @Test
	 public void testPostorderTraversal(){
		 TreeNode c1 = new TreeNode(2);
		 c1.left = new TreeNode(3);
		 
		 TreeNode rt = new TreeNode(1);
		 rt.right = c1;
		 
		 assertEquals(new ArrayList<Integer>(Arrays.asList(3,2,1)), postOrderTraversal(rt));
		 
	 }
	 
	 @Test
	 public void testPostorderTraversal_2Stacks(){
		 TreeNode c1 = new TreeNode(2);
		 c1.left = new TreeNode(3);
		 
		 TreeNode rt = new TreeNode(1);
		 rt.right = c1;
		 
		 assertEquals(new ArrayList<Integer>(Arrays.asList(3,2,1)), postOrderTraversal_2Stacks(rt));
		 
	 }
	 
	 @Test
	 public void testPostorderTraversal_1Stack(){
		 TreeNode c1 = new TreeNode(2);
		 c1.left = new TreeNode(3);
		 
		 TreeNode rt = new TreeNode(1);
		 rt.right = c1;
		 
		 assertEquals(new ArrayList<Integer>(Arrays.asList(3,2,1)), postOrderTraversal_1Stack(rt));
		 
	 }
	 
}
