package fb;

import leetcode.binaryTree.TreeNode;

public class DiameterOfBinaryTree {
	
	public int diameterOfBinaryTree(TreeNode root) {
		if(root == null) {
			return 0;
		}
		
		return Math.max(depthOfBinaryTree( root.left)+depthOfBinaryTree(root.right), Math.max(diameterOfBinaryTree(root.left), diameterOfBinaryTree(root.right)));
		
		
	}
	
	private int depthOfBinaryTree(TreeNode rt) {
		if(rt== null) {
			return 0;
		}else {
			return Math.max(depthOfBinaryTree(rt.left), depthOfBinaryTree(rt.right)) + 1;
		}
	}

}
