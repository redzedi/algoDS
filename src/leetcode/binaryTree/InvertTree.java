package leetcode.binaryTree;

public class InvertTree {
	
	/*
	 * https://leetcode.com/problems/invert-binary-tree/submissions/
	 */

	 public TreeNode invertTree(TreeNode root) {
	        if(root == null){
	            return null;
	        }else{
	            TreeNode lft = root.left;
	            root.left = invertTree(root.right);
	            root.right = invertTree(lft);
	            return root;

	        }
	        
	    }
}
