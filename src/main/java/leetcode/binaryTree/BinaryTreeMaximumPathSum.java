package leetcode.binaryTree;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BinaryTreeMaximumPathSum {
	
	//https://leetcode.com/problems/binary-tree-maximum-path-sum/description/
	
	private int maxSum = Integer.MIN_VALUE;
	
	public int maxPathSum(TreeNode root) {
		 
		 // either the max path passes through the root
		 // or it is rooted at left or right subtree nodes
	     maxPathRootedAndMaxSubTree(root);  
		 return maxSum;
	    }
	 
	 private int maxPathRootedAndMaxSubTree(TreeNode nd) {
		 if(nd==null) {
			 return Integer.MIN_VALUE;
		 }else {
			 int lt = maxPathRootedAndMaxSubTree( nd.left);
			 int rt = maxPathRootedAndMaxSubTree( nd.right);
			 int maxPathRootedAtSelf = Math.max( nd.val , nd.val+  (lt==Integer.MIN_VALUE&&rt==Integer.MIN_VALUE?0: Math.max(lt, rt)));
			 int pathLengthPassingThroughSelf = nd.val+(lt==Integer.MIN_VALUE?0:lt)+(rt==Integer.MIN_VALUE?0:rt);
			 maxSum =  Math.max(nd.val, Math.max(maxPathRootedAtSelf,  Math.max(maxSum, pathLengthPassingThroughSelf)));
			 
			 return maxPathRootedAtSelf;
		 }
	 }
	 
	 
	
	  
	  @Test
	  public void testMaxPathSum1() {
		  assertEquals(-3, maxPathSum(new TreeNode(-3)));
	  }
	 
	 public static void main(String[] args) {
		System.out.printf("%d", Integer.MIN_VALUE+1);
	}

}
