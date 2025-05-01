package fb;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

import leetcode.binaryTree.TreeNode;

public class LCAOfBinaryTree {

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		
		if(root == null) {
			return null;
		}
		boolean pLeft = false;
		boolean pRight = false;
		boolean qLeft = false;
		boolean qRight = false;
		
		if(root.val == p.val && ( (pLeft =containsNode(root.left , q)) || (pRight = containsNode(root.right , q) ))) {
			return p;
		}else if(root.val == q.val && ( (qLeft =containsNode(root.left , p)) || (qRight =containsNode(root.right , p) ))) {
			return q;
		}
		
		TreeNode lcaL = lowestCommonAncestor(root.left, p, q);
		TreeNode lcaR = lowestCommonAncestor(root.right, p, q);
		
		if( lcaL != null || lcaR != null) {
			return lcaL != null? lcaL : lcaR;
		}else if( (pLeft && qRight) || (qLeft && pRight)) {
		
			return root;
		}
		
		return null;

	}
	
	private HashMap<Integer, HashSet<Integer>> memoizeSet = new HashMap<>();

	private boolean containsNode(TreeNode right, TreeNode q) {
		
		
		
		if(right == null) {
			return false;
		}else if( right.val == q.val) {
			return true;
		}
		
		if(memoizeSet.containsKey(q.val) && memoizeSet.get(q.val).contains(right.val)) {
			return true;
		}
		
		boolean containsInSubtree = containsNode(right.right, q) || containsNode(right.left, q);
		
		if(containsInSubtree) {
			
			memoizeSet.putIfAbsent(q.val, new HashSet<>());
			memoizeSet.get(q.val).add(right.val);
		}
		
		return containsInSubtree;
	}
	
	
	@Test
	public void testLCA() {
		
		TreeNode n6 = new TreeNode(6);
		TreeNode n7 = new TreeNode(7);
		TreeNode n4 = new TreeNode(4);
		TreeNode n0 = new TreeNode(0);
		TreeNode n8 = new TreeNode(8);

		TreeNode n2 = new TreeNode(2 , n7,n4);
		
		TreeNode n5 = new TreeNode(5 , n6,n2);
		
		TreeNode n1 = new TreeNode(1 , n0,n8);

		TreeNode rt = new TreeNode(3 , n5,n1);
		
		
		assertEquals(3, rt.val);
		
	}
	

}
