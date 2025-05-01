package fb;

import leetcode.binaryTree.TreeNode;

public class RangeSumBST {
	
	//https://leetcode.com/problems/range-sum-of-bst/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	public int rangeSumBST(TreeNode root, int low, int high) {
        if(root == null){
            return 0;
        }

        if( root.val >low && root.val < high){
            return root.val + rangeSumBST( root.left , low , high ) + rangeSumBST(root.right , low , high);
        }else if( root.val >= high){
            return (root.val==high?root.val:0) + rangeSumBST( root.left, low, high);
        }else{
            return (root.val==low?root.val:0) + rangeSumBST( root.right, low,high);
        }
    }

}
