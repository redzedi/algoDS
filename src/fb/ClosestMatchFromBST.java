package fb;

import leetcode.binaryTree.TreeNode;

public class ClosestMatchFromBST {
	
	//https://leetcode.com/problems/closest-binary-search-tree-value/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
	
	public int closestValue(TreeNode root, double target) {
        if(root == null){
            return -1;
        }    
        if(root.val == target){
            return root.val;
        }
        int chldRes = closestValue(target < root.val?root.left:root.right , target);
        chldRes = (chldRes == -1)?root.val:chldRes;
       
         if(chldRes != root.val){
            double rootDiff =  Math.abs(root.val-target);
            double chldResDiff = Math.abs(chldRes-target); 
            return chldResDiff < rootDiff? chldRes: chldResDiff>rootDiff?root.val: chldRes<root.val?chldRes:root.val;
         }
  
       return chldRes;


    }

}
