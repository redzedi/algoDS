package fb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import leetcode.binaryTree.TreeNode;

public class RightSideViewOfBinaryTree {
	
	
	private record NdPair(TreeNode nd , Integer lvl) {} 

	public List<Integer> rightSideView(TreeNode root) {
		
		LinkedList<NdPair> res = new LinkedList<>();
		
		if(root == null) {
			return Collections.emptyList();
		}
		
		ArrayDeque<NdPair> q = new ArrayDeque<>();
		q.add(new NdPair(root,0));
		
		while(!q.isEmpty()) {
			NdPair curr = q.removeFirst();
			if(curr.nd().left != null)
			q.add(new NdPair(curr.nd().left, curr.lvl()+1));
			
			if(curr.nd().right != null)
			q.add(new NdPair(curr.nd().right, curr.lvl()+1));
			
			if(res.isEmpty() || res.getLast().lvl() < curr.lvl() ) {
				res.addLast(curr);
			}else {
				res.removeLast();
				res.addLast(curr);
			}
		}
		
		
		return res.stream().map(ndPr->ndPr.nd().val).collect(Collectors.toList());
	}
	
	@Test
	public void testRightSideView1() {
		TreeNode n1 = new TreeNode(5);
		TreeNode n2 = new TreeNode(2,null,n1);
		
		
		TreeNode n4 = new TreeNode(4);
		TreeNode n5 = new TreeNode(3,null,n4);
		
		TreeNode rt = new TreeNode(1,n2,n5);
		assertEquals(Arrays.asList(1,3,4), rightSideView(rt));
	}
	
	@Test
	public void testRightSideView2() {
		TreeNode n1 = new TreeNode(5);
		TreeNode n2 = new TreeNode(4,n1,null);
		TreeNode n3 = new TreeNode(2,n2,null);
		
		
		TreeNode n4 = new TreeNode(3);
		
		TreeNode rt = new TreeNode(1,n3,n4);
		assertEquals(Arrays.asList(1,3,4,5), rightSideView(rt));
	}
	
	@Test
	public void testRightSideView3() {
		TreeNode n1 = new TreeNode(3);
		
		TreeNode rt = new TreeNode(1,null,n1);
		assertEquals(Arrays.asList(1,3), rightSideView(rt));
	}
	@Test
	public void testRightSideView4() {
		assertTrue( rightSideView(null).isEmpty());
	}

}
